package com.example.phonebook;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class PhoneCall extends AppCompatActivity {
EditText phonecall;
ImageView phonecallButton;
TextView PhoneCallontact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_call);
        phonecallButton=findViewById(R.id.phonecallButton);
        phonecall=findViewById(R.id.phonecall);
        PhoneCallontact=findViewById(R.id.PhoneCallontact);
        phonecallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_CALL);
                String number=phonecall.getText().toString();
                if(number.trim().isEmpty()){
                    Toast.makeText(PhoneCall.this, "Please enter number", Toast.LENGTH_SHORT).show();
                }else{
                    intent.setData(Uri.parse("tel:" +number));

                }
                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(PhoneCall.this, "Please grant your permission", Toast.LENGTH_SHORT).show();
                    requestpermission();
                }
                else{
                    startActivity(intent);
                }

            }

        });

        PhoneCallontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PhoneCall.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
    private  void requestpermission(){
        ActivityCompat.requestPermissions(PhoneCall.this,new String[]{Manifest.permission.CALL_PHONE},1);
    }
}
