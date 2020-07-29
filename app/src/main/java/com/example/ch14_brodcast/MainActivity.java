package com.example.ch14_brodcast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView ivBattery;
    EditText edtbatteya;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Check");
        ivBattery = findViewById(R.id.ivBattery);
        edtbatteya = findViewById(R.id.edtBattery);
    }
    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                int remain = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                edtbatteya.setText("현재충전량::" + remain + " %/n ");

                if (remain >= 90) ivBattery.setImageResource(R.drawable.battery_icon);
                else if (remain >= 70) ivBattery.setImageResource(R.drawable.battery_80);
                else if (remain >= 50) ivBattery.setImageResource(R.drawable.battery_60);
                else if (remain >= 10) ivBattery.setImageResource(R.drawable.battery_20);
                else ivBattery.setImageResource(R.drawable.battery_0);
                int plug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
                switch (plug) {
                    case 0:
                        edtbatteya.append("전원연결:안됨");
                        break;
                    case BatteryManager.BATTERY_PLUGGED_AC:
                        edtbatteya.append("전원연결:어댑터연결됨");
                        break;
                    case BatteryManager.BATTERY_PLUGGED_USB:
                        edtbatteya.append("전원연결 : USB 연결굄");
                        break;
                }
            }
        }
    };
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(br);
    }
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(br, iFilter);
    }
}