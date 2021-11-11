package com.example.jarvisconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    private EditText myIpAddress;
    public static MainActivity myObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent1=getIntent();
        String myErrorMessage=intent1.getStringExtra(Controller.ERROR_MESSAGE);
        if(myErrorMessage!=null)
        {
            Toast.makeText(MainActivity.this,myErrorMessage,Toast.LENGTH_SHORT).show();
        }

        myIpAddress=(EditText)findViewById(R.id.ip_address);
        myObject=MainActivity.this;
    }

    public void onClickConnectButton(View view) {
        String ipAddress=String.valueOf(myIpAddress.getText());
        Intent intent=new Intent(this,Controller.class);
        intent.putExtra(Controller.IP_ADDRESS,ipAddress);
        startActivity(intent);
    }
}