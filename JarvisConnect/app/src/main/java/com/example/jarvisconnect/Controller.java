package com.example.jarvisconnect;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.EditText;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Controller extends Activity {

    public static final String IP_ADDRESS="ip address";
    public static final String ERROR_MESSAGE="my error message";
    private final String LIGHT_ON="light on";
    private final String LIGHT_OFF="light off";
    private final String FLASHLIGHT_ON="flashlight on";
    private final String FLASHLIGHT_OFF="flashlight off";
    private final String FAN_ON="fan on";
    private final String FAN_OFF="fan off";
    private final String SOS_ON="sos on";
    private final String SOS_OFF="sos off";
    public static DataOutputStream out=null;
    public static Socket socket=null;
    private  Switch lightSwitch;
    private  Switch fanSwitch;
    private  Switch flashlightSwitch;
    private  Switch sosSwitch;
    private EditText commandBox;
    private MySocketController mySocketControllerObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        Intent intent=getIntent();
        final String ipAddress=intent.getStringExtra(IP_ADDRESS);

        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s=null;
                    s=new Socket(ipAddress,6666);
                    if(s!=null)
                    {
                        Controller.socket=s;
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                    Intent intent1=new Intent(Controller.this,MainActivity.class);
                    intent1.putExtra(ERROR_MESSAGE,"error while connecting to server");
                    startActivity(intent1);
                }
            }
        });
        t.start();
        try
        {
            t.join();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        if(socket!=null)
        {
            mySocketControllerObject=new MySocketController(Controller.socket,Controller.this);
            out=mySocketControllerObject.out;
            commandBox=(EditText)findViewById(R.id.text_entry);
            lightSwitch=(Switch)findViewById(R.id.light_switch);
            fanSwitch=(Switch)findViewById(R.id.fan_switch);
            flashlightSwitch=(Switch)findViewById(R.id.flashlight_switch);
            sosSwitch=(Switch)findViewById(R.id.sos_switch);
            commandBox.setText("connected");
            PhoneStateReceiver.on=true;
            mySocketControllerObject.execute();
        }

    }


    public void onClickLightSwitch(View view) {
        boolean lightStatus=((Switch)view).isChecked();
        if(lightStatus==true)
        {
            mySocketControllerObject.lightStatus=true;
            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            out.writeUTF(LIGHT_ON);
                            out.flush();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            catch(Exception e)
            {
                showMyToast("i/o exception");
            }
        }
        else if(lightStatus==false)
        {
            mySocketControllerObject.lightStatus=false;
            try {
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       try
                       {
                           out.writeUTF(LIGHT_OFF);
                           out.flush();
                       }
                       catch (IOException e)
                       {
                           e.printStackTrace();
                       }
                   }
               }).start();
            }
            catch(Exception e)
            {
                showMyToast("i/o exception");
            }
        }
    }

    public void onClickFanSwitch(View view) {
        boolean fanStatus=((Switch)view).isChecked();
        if(fanStatus==true)
        {
            mySocketControllerObject.fanStatus=true;
            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            out.writeUTF(FAN_ON);
                            out.flush();
                        }
                        catch(IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            catch(Exception e)
            {
                showMyToast("i/o exception");
            }
        }
        else if(fanStatus==false)
        {
            mySocketControllerObject.fanStatus=false;
            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            out.writeUTF(FAN_OFF);
                            out.flush();
                        }
                        catch(IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            catch(Exception e)
            {
                showMyToast("i/o exception");
            }
        }
    }

    public void onClickFlashlightSwitch(View view) {
        boolean flashlightStatus=((Switch)view).isChecked();
        if(flashlightStatus==true)
        {
            mySocketControllerObject.flashlightStatus=true;
            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            out.writeUTF(FLASHLIGHT_ON);
                            out.flush();
                        }
                        catch(IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            catch(Exception e)
            {
                showMyToast("i/o exception");
            }
        }
        else if(flashlightStatus==false)
        {
            mySocketControllerObject.flashlightStatus=false;
            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            out.writeUTF(FLASHLIGHT_OFF);
                            out.flush();
                        }
                        catch(IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            catch(Exception e)
            {
                showMyToast("i/o exception");
            }
        }
    }

    public void onClickSosSwitch(View view) {
        boolean sosStatus=((Switch)view).isChecked();
        if(sosStatus==true)
        {
            mySocketControllerObject.sosStatus=true;
            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            out.writeUTF(SOS_ON);
                            out.flush();
                        }
                        catch(IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            catch(Exception e)
            {
                showMyToast("i/o exception");
            }
        }
        else if(sosStatus==false)
        {
            mySocketControllerObject.sosStatus=false;
            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                       try
                       {
                           out.writeUTF(SOS_OFF);
                           out.flush();
                       }
                       catch(IOException e)
                       {
                           e.printStackTrace();
                       }
                    }
                }).start();
            }
            catch(Exception e)
            {
                showMyToast("i/o exception");
            }
        }
    }

    public void onClickSendButton(View view) {
        final String command=String.valueOf(commandBox.getText());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    out.writeUTF(command);
                    out.flush();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void showMyToast(String toastMessage)
    {
        Toast.makeText(Controller.this,toastMessage,Toast.LENGTH_SHORT).show();
    }

    public void setLightState(boolean light)
    {
        lightSwitch.setChecked(light);
    }

    public void setFanState(boolean fan)
    {
        fanSwitch.setChecked(fan);
    }

    public void setFlashlightState(boolean flashlight)
    {
        flashlightSwitch.setChecked(flashlight);
    }

    public void setSosState(boolean sos)
    {
        sosSwitch.setChecked(sos);
    }


}