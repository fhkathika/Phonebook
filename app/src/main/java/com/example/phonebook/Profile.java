package com.example.phonebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {
private TextView textid,numberid;
    Phonebook_DBHelper mydb;
    Context mContext;
    ContactInfo contactInfo;
    private  Toolbar toolbar;
    private ImageView update;
    public String Tag="Profile";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);

        setActionBar(toolbar);

        //add icon in toolbar
        getActionBar().setIcon(R.drawable.ic_update_black_24dp);
        ////////
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Main Page");
        }
//        toolbar.setSubtitle("");
        toolbar.inflateMenu(R.menu.menu_item);
        textid=findViewById(R.id.textid);

        numberid=findViewById(R.id.numberid);

        mydb = new Phonebook_DBHelper(this);
        mContext = Profile.this;
        update=findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Profile.this,Update.class);
                intent.putExtra("updateData",contactInfo);
                startActivity(intent);
            }
        });
///to get profile data
if(getIntent().getExtras()!=null)
{
            contactInfo=(ContactInfo)
            getIntent().getSerializableExtra("string");
    if(contactInfo!=null){
        textid.setText(contactInfo.getContactlistName());
        numberid.setText(contactInfo.getContactlistNumber());
    }
}
     }

    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this,"back key is pressed", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(Profile.this,MainActivity.class);
        startActivity(i);
        finish();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        if(item.getItemId()==R.id.deleteContact){
            AlertDialog.Builder    AlertDialogBuilder = new AlertDialog.Builder(mContext);
            AlertDialogBuilder.setCancelable(true);
            View myView = LayoutInflater.from(mContext).inflate(R.layout.delete_alert_custom_dialog_box, null);

//
//                   CustomAdapter studentAdapter = new CustomAdapter(studentlist, mContext);
//                   listView.setAdapter(studentAdapter);
            Button ok = myView.findViewById(R.id.ok);
            Button cancel = myView.findViewById(R.id.cancel);
            final AlertDialog deleteAlert = AlertDialogBuilder.create();

            deleteAlert.dismiss();
            deleteAlert.setView(myView);
            deleteAlert.show();


            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                  int conttactId=  contactInfo.getContactlistId();




                    Integer result= mydb.deleteContact(conttactId);



                 Intent intent=new Intent(Profile.this,MainActivity.class);

                    startActivity(intent);


                    deleteAlert.dismiss();
                    finish();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteAlert.dismiss();
                }
            });
        }


        return super.onOptionsItemSelected(item);


    }


}

