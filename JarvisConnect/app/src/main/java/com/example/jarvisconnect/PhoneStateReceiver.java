package com.example.jarvisconnect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import java.io.IOException;

public class PhoneStateReceiver extends BroadcastReceiver {

    public static boolean on=false;

    @Override
    public void onReceive(Context context, Intent intent) {
        String state=intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if(on)
        {
            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING))
            {
                String phoneNumber=intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                final String message="incoming call from "+phoneNumber;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            Controller.out.writeUTF(message);
                            Controller.out.flush();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            else if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))
            {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            Controller.out.writeUTF("call connected");
                            Controller.out.flush();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            else if(state.equals(TelephonyManager.EXTRA_STATE_IDLE))
            {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            Controller.out.writeUTF("call disconnected");
                            Controller.out.flush();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }
}
