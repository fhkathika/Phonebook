package com.example.phonebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class Update extends AppCompatActivity {
    private Toolbar toolbar;
    Phonebook_DBHelper mydb;
    Context mContext;
    ContactInfo contactInfo;
    ListView listView;
    private EditText name,number;
    private ImageView done;
    Boolean isUpdated;
    String contactName;
    public String Tag="Update";
  ArrayList<ContactInfo> contactlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        mContext = Update.this;
        mydb = new Phonebook_DBHelper(mContext);
        SQLiteDatabase db = mydb.getWritableDatabase();



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        toolbar = (Toolbar) findViewById(R.id.my_update_toolbar);
        name =  findViewById(R.id.name);
        number =  findViewById(R.id.number);


        done =  findViewById(R.id.done);



        setActionBar(toolbar);

        //add icon in toolbar
//        getActionBar().setIcon(R.drawable.ic_update_black_24dp);
        ////////
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Main Page");
        }
//        toolbar.setSubtitle("");
        toolbar.inflateMenu(R.menu.menu_item_for_update_activity);

if(getIntent().getExtras()!=null){
    contactInfo=(ContactInfo)getIntent().getSerializableExtra("updateData");
    if(contactInfo!=null){
        name.setText(contactInfo.getContactlistName());
        number.setText(contactInfo.getContactlistNumber());
    }
}
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               String contactName = name.getText().toString();
                Log.i(Tag,"name "+ contactName);
                String contactNumber = number.getText().toString();

                Log.i(Tag,"number " + contactNumber);

                int updateId=contactInfo.contactlistId;

                isUpdated = mydb.updateContact(updateId,contactName,contactNumber);

                if (isUpdated) {
                    Toast.makeText(Update.this, "data updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Update.this, "Not updated", Toast.LENGTH_SHORT).show();
                }
                ContactInfo contactInfo=new ContactInfo(updateId,contactName,contactNumber);


                Intent intent = new Intent(Update.this, Profile.class);
                intent.putExtra("string",contactInfo);

                startActivity(intent);
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item_for_update_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        if (item.getItemId() == R.id.deleteContactUpdateActivity) {
            AlertDialog.Builder AlertDialogBuilder = new AlertDialog.Builder(mContext);
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

                    int conttactId = contactInfo.getContactlistId();


                    Integer result = mydb.deleteContact(conttactId);


                    Intent intent = new Intent(Update.this, MainActivity.class);

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

        if (item.getItemId() == R.id.discardChanges) {
            Intent intent = new Intent(Update.this, Profile.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}
