package local.skyworth.filterhelper;
import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
public class StartService extends Service {
 private final Handler handler=new Handler();private long started;private int stable;private boolean active;
 private Notification notification(String text){return new Notification.Builder(this,"startup").setSmallIcon(android.R.drawable.stat_notify_sync).setContentTitle("电视过滤启动助手").setContentText(text).build();}
 public int onStartCommand(Intent i,int flags,int id){
  if(active)return START_NOT_STICKY;
  NotificationManager nm=getSystemService(NotificationManager.class);nm.createNotificationChannel(new NotificationChannel("startup","过滤启动状态",NotificationManager.IMPORTANCE_LOW));
  startForeground(1,notification("正在等待 FlClash VPN，最多60秒"));active=true;started=SystemClock.elapsedRealtime();stable=0;
  try{Intent launch=new Intent("com.follow.clash.action.START");launch.setPackage("com.follow.clash");launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);startActivity(launch);}catch(RuntimeException e){finishWith("无法启动FlClash，请手动打开检查",true);return START_NOT_STICKY;}
  new Thread(() -> {
   applyPlayerSettings();
   handler.postDelayed(check,1500);
  }, "player-settings").start();
  return START_NOT_STICKY;
 }
 private void applyPlayerSettings(){
  String[][] settings={{"debug.ottsdk.ups_ad","false"},{"debug.disable.screen.info","true"},{"debug.ottad.mid.forbiddentime","86400"},{"debug.ottsdk.config","1"}};
  try{
   for(String[] setting:settings){
    java.lang.Process process=new ProcessBuilder("/system/bin/setprop",setting[0],setting[1]).start();
    if(!process.waitFor(2,java.util.concurrent.TimeUnit.SECONDS)){process.destroy();throw new java.io.IOException("property timeout");}
    if(process.exitValue()!=0)throw new java.io.IOException("property denied: "+setting[0]);
   }
   android.util.Log.i("TvFilterHelper","播放器快速启动参数已设置");
  }catch(Exception error){android.util.Log.w("TvFilterHelper","播放器快速启动参数未设置",error);}
 }
 private final Runnable check=new Runnable(){public void run(){
  ConnectivityManager cm=getSystemService(ConnectivityManager.class);boolean vpn=false;
  for(Network n:cm.getAllNetworks()){NetworkCapabilities caps=cm.getNetworkCapabilities(n);if(caps!=null&&caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN)&&caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)){vpn=true;break;}}
  stable=vpn?stable+1:0;
  if(stable>=3){try{Intent home=new Intent();home.setComponent(new ComponentName("com.tianci.movieplatform","com.coocaa.homepage.vast.HomePageActivity"));home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);startActivity(home);finishWith("VPN已连接，已返回原首页",false);}catch(RuntimeException e){finishWith("VPN已连接，请手动返回首页",true);}return;}
  if(SystemClock.elapsedRealtime()-started>=60000){finishWith("启动超时，请打开FlClash检查",true);return;}
  handler.postDelayed(this,1000);
 }};
 private void finishWith(String message,boolean failed){android.util.Log.i("TvFilterHelper",message);stopForeground(true);if(failed)getSystemService(NotificationManager.class).notify(2,notification(message));active=false;stopSelf();}
 public void onDestroy(){handler.removeCallbacks(check);super.onDestroy();}
 public IBinder onBind(Intent i){return null;}
}
