package com.example.onlinestore.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityReceiver extends BroadcastReceiver {
    public static ConnectivityReceiverListener connectivityReceiverListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected = hasNetwork(context);

        if (connectivityReceiverListener != null) {
            connectivityReceiverListener.onNetworkConnectionChanged(isConnected);
        }
    }

    public static boolean isConnected(Context context) {
        return hasNetwork(context);
    }

    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }

    private static boolean hasNetwork(Context context) {
        boolean has_wifi = false;
        boolean has_mobile_data = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInformation = connectivityManager.getAllNetworkInfo();
        for (NetworkInfo info : networkInformation) {
            if (info.getTypeName().equalsIgnoreCase("Wifi")) {
                if (info.isConnected()) {
                    has_wifi = true;
                }
            }
            if (info.getTypeName().equalsIgnoreCase("Mobile")) {
                if (info.isConnected()) {
                    has_mobile_data = true;
                }
            }
        }
        return has_wifi || has_mobile_data;
    }
}
