package local.skyworth.filterhelper;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.*;
public class MainActivity extends Activity {
 public void onCreate(Bundle b) {
  super.onCreate(b);
  LinearLayout layout=new LinearLayout(this); layout.setOrientation(1);layout.setPadding(60,40,60,40);
  TextView info=new TextView(this);info.setText("电视过滤启动助手\n开机启动 FlClash，确认系统 VPN 已连接后回到创维原首页。\n最多等待60秒。若失败会显示通知，保留原首页入口。\n不采集流量、不联网、不修改会员或播放器数据。");info.setTextSize(22);layout.addView(info);
  Switch enabled=new Switch(this);enabled.setText("开机自动恢复过滤并返回首页");enabled.setChecked(getSharedPreferences("config",0).getBoolean("enabled",false));enabled.setOnCheckedChangeListener((v,on)->getSharedPreferences("config",0).edit().putBoolean("enabled",on).apply());layout.addView(enabled);
  Button test=new Button(this);test.setText("测试启动并返回首页");test.setOnClickListener(v->startForegroundService(new Intent(this,StartService.class)));layout.addView(test);
  setContentView(layout);
 }
}
