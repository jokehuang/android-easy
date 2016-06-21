package com.easy.example.activity.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.TextView;

import com.easy.example.R;
import com.easy.example.activity.BaseActivity;
import com.easy.util.NetworkUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_network_util)
public class NetworkUtilActivity extends BaseActivity {
    @ViewInject(R.id.tv_mobile_connected)
    private TextView tv_mobile_connected;
    @ViewInject(R.id.tv_wifi_connected)
    private TextView tv_wifi_connected;
    @ViewInject(R.id.tv_wifi_ssid)
    private TextView tv_wifi_ssid;
    @ViewInject(R.id.tv_ip)
    private TextView tv_ip;
    @ViewInject(R.id.tv_mac)
    private TextView tv_mac;

    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isMobileConnected = NetworkUtil.isMobileConnected(self);
                boolean isWifiConnected = NetworkUtil.isWifiConnected(self);
                String wifiSSID = NetworkUtil.getWifiSSID(self);
                String ip = NetworkUtil.getIpAddress(self);
                String mac = NetworkUtil.getMacAddress(self);
                tv_mobile_connected.setText(isMobileConnected + "");
                tv_wifi_connected.setText(isWifiConnected + "");
                tv_wifi_ssid.setText(wifiSSID);
                tv_ip.setText(ip);
                tv_mac.setText(mac);
            }
        };
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
