#!/bin/bash
set -euo pipefail
cd -- "$(dirname -- "$0")"
: "${TV_JAVA_DIR:?Set TV_JAVA_DIR to a JDK 17 directory}"
: "${TV_ANDROID_JAR:?Set TV_ANDROID_JAR to Android API 28 android.jar}"
: "${TV_BUILD_TOOLS:?Set TV_BUILD_TOOLS to an Android build-tools directory}"
for tool in javac java keytool; do
  test -x "$TV_JAVA_DIR/bin/$tool" || { echo "Missing JDK tool: $tool" >&2; exit 1; }
done
test -f "$TV_ANDROID_JAR" || { echo "Missing android.jar" >&2; exit 1; }
for item in aapt2 zipalign lib/d8.jar lib/apksigner.jar; do
  test -f "$TV_BUILD_TOOLS/$item" || { echo "Missing build tool: $item" >&2; exit 1; }
done
command -v zip >/dev/null
task_build=$(mktemp -d "$PWD/build-tmp.XXXXXX")
trap 'rm -rf "$task_build"' EXIT
mkdir -p "$task_build/classes" "$task_build/dex" build local-signing
"$TV_JAVA_DIR/bin/javac" -source 8 -target 8 -classpath "$TV_ANDROID_JAR" -d "$task_build/classes" src/local/skyworth/filterhelper/*.java
"$TV_JAVA_DIR/bin/java" -cp "$TV_BUILD_TOOLS/lib/d8.jar" com.android.tools.r8.D8 --lib "$TV_ANDROID_JAR" --min-api 26 --output "$task_build/dex" "$task_build"/classes/local/skyworth/filterhelper/*.class
"$TV_BUILD_TOOLS/aapt2" link -o "$task_build/unsigned.apk" --manifest AndroidManifest.xml -I "$TV_ANDROID_JAR"
(cd "$task_build/dex" && zip -q "$task_build/unsigned.apk" classes.dex)
# This key is local test material. Preserve it locally for compatible updates.
# The well-known password protects no shared secret and is for local tests only.
task_signing_key="${TV_SIGNING_KEY:-$PWD/local-signing/test.keystore}"
if [ -n "${TV_SIGNING_KEY:-}" ] && [ ! -f "$task_signing_key" ]; then
  echo "Configured signing key does not exist" >&2
  exit 1
fi
if [ ! -f "$task_signing_key" ]; then
  "$TV_JAVA_DIR/bin/keytool" -genkeypair -keystore "$task_signing_key" -storepass android -keypass android -alias localhelper -keyalg RSA -keysize 2048 -validity 3650 -dname 'CN=TV Helper Local Test'
  chmod 600 "$task_signing_key"
fi
"$TV_BUILD_TOOLS/zipalign" -f 4 "$task_build/unsigned.apk" "$task_build/aligned.apk"
"$TV_JAVA_DIR/bin/java" -jar "$TV_BUILD_TOOLS/lib/apksigner.jar" sign --ks "$task_signing_key" --ks-pass pass:android --key-pass pass:android --out "$task_build/TvFilterHelper.apk" "$task_build/aligned.apk"
"$TV_JAVA_DIR/bin/java" -jar "$TV_BUILD_TOOLS/lib/apksigner.jar" verify --verbose "$task_build/TvFilterHelper.apk"
cp "$task_build/TvFilterHelper.apk" build/TvFilterHelper.apk
echo "Built helper/build/TvFilterHelper.apk"
