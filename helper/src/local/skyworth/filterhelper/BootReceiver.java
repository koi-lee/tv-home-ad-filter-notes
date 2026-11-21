package local.skyworth.filterhelper;
import android.content.*;
public class BootReceiver extends BroadcastReceiver {
 public void onReceive(Context c,Intent i){
  if(Intent.ACTION_BOOT_COMPLETED.equals(i.getAction()) && c.getSharedPreferences("config",0).getBoolean("enabled",false)) c.startForegroundService(new Intent(c,StartService.class));
 }
}
