package com.example.jarvisconnect;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import android.os.AsyncTask;
import android.widget.Toast;

public class MySocketController extends AsyncTask<Void,String,Boolean> {

    private Socket socket;
    private Controller myControllerObject;
    public DataInputStream in=null;
    public DataOutputStream out=null;
    public boolean lightStatus=false;
    public boolean fanStatus=false;
    public boolean flashlightStatus=false;
    public boolean sosStatus=false;
    private String inputMessage=null;

    MySocketController(Socket mySocket,Controller myControllerObject)
    {
        this.socket=mySocket;
        this.myControllerObject=myControllerObject;
        try
        {
            in=new DataInputStream(socket.getInputStream());
            out=new DataOutputStream(socket.getOutputStream());
        }
        catch(IOException e)
        {
            e.printStackTrace();
            myControllerObject.showMyToast("error i/o");
        }
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        boolean currentState=true;
        while(currentState)
        {
            try
            {
                inputMessage=in.readUTF();
                if(inputMessage!=null)
                {
                    publishProgress(inputMessage);
                }
                inputMessage=null;
            }
            catch(Exception e)
            {
                myControllerObject.showMyToast("unable to read message");
                currentState=false;
            }
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        String commandString=values[0];

        if(commandString.equalsIgnoreCase("light on")||commandString.equalsIgnoreCase("light off"))
        {
            if(commandString.equalsIgnoreCase("light on"))
            {
                lightStatus=true;
                myControllerObject.setLightState(true);
            }
            else if(commandString.equalsIgnoreCase("light off"))
            {
                lightStatus=false;
                myControllerObject.setLightState(false);
            }
        }

        else if(commandString.equalsIgnoreCase("fan on")||commandString.equalsIgnoreCase("fan off"))
        {
            if(commandString.equalsIgnoreCase("fan on"))
            {
                fanStatus=true;
                myControllerObject.setFanState(true);
            }
            else if(commandString.equalsIgnoreCase("fan off"))
            {
                fanStatus=false;
                myControllerObject.setFanState(false);
            }
        }

        else if(commandString.equalsIgnoreCase("flashlight on")||commandString.equalsIgnoreCase("flashlight off"))
        {
            if(commandString.equalsIgnoreCase("flashlight on"))
            {
                flashlightStatus=true;
                myControllerObject.setFlashlightState(true);
            }
            else if(commandString.equalsIgnoreCase("flashlight off"))
            {
                flashlightStatus=false;
                myControllerObject.setFlashlightState(false);
            }
        }

        else if(commandString.equalsIgnoreCase("sos on")||commandString.equalsIgnoreCase("sos off"))
        {
            if(commandString.equalsIgnoreCase("sos on"))
            {
                sosStatus=true;
                myControllerObject.setSosState(true);
            }
            else if(commandString.equalsIgnoreCase("sos off"))
            {
                sosStatus=false;
                myControllerObject.setSosState(false);
            }
        }

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        myControllerObject.showMyToast("thread stopped");
    }
}
