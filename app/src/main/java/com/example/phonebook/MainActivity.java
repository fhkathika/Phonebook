package com.example.phonebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonebook.ContactInfo;
import com.example.phonebook.Phonebook_CustomAdapter;
import com.example.phonebook.Phonebook_DBHelper;
import com.example.phonebook.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static String Extra_message = "message";
    private Button retrieve;
    private ImageView add;

  ListView listView;

    Phonebook_DBHelper mydb;
    private AlertDialog.Builder AlertDialogBuilder;
    private EditText entryName,entryNumber;
    public EditText searchView;
    private static String TAG = "MainActivity";
    Context context;

 ArrayList<ContactInfo> contactlist = new ArrayList<>();

    final String Tag = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new Phonebook_DBHelper(this);
        SQLiteDatabase db = mydb.getWritableDatabase();


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        add = findViewById(R.id.add);
        context = MainActivity.this;
        listView = findViewById(R.id.list_itemid);

      final   Phonebook_CustomAdapter contactInfoadapter = new Phonebook_CustomAdapter(contactlist, context);
        listView.setAdapter(contactInfoadapter);


        //seach
        searchView=findViewById(R.id.searchView);
        listView.setTextFilterEnabled(true);

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                if(TextUtils.isEmpty(s.toString())){
                    reinitialize(contactlist);
                }

                ArrayList<ContactInfo> filterList = new ArrayList<>();

                for (ContactInfo obj : contactlist){
                    Log.i(Tag,""+ obj.getContactlistName() +" check with "+s.toString());
                    if(obj.getContactlistName().toLowerCase().contains(s.toString().toLowerCase())){
                        filterList.add(obj);
                    }
                }

                reinitialize(filterList);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newtext) {
//
////
////                Phonebook_CustomAdapter contactInfoadapter = new Phonebook_CustomAdapter(contactlist, context);
//             contactInfoadapter.getFilter().filter(newtext);
////                reinitialize( contactlist);
////                listView.setAdapter(contactInfoadapter);
////                contactInfoadapter.notifyDataSetChanged();
////                if (TextUtils.isEmpty(newtext)) {
////                    listView.clearTextFilter();
////                } else {
////                    listView.setFilterText(newtext);
////                }
//                    Log.i(Tag,"" + newtext );
//
//
//
//                return true;
//            }
//        });


//        setupSearchView();

        //add conatct
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                AlertDialogBuilder.setCancelable(true);

                View mView = getLayoutInflater().inflate(R.layout.contact_entry_customdialog_layout, null);
                TextView save = mView.findViewById(R.id.save);
                 entryName = mView.findViewById(R.id.entryName);
             entryNumber = mView.findViewById(R.id.entryNumber);
                final AlertDialog alert = AlertDialogBuilder.create();



                alert.setView(mView);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String name = entryName.getText().toString();
                        String number = entryNumber.getText().toString();
                        if (TextUtils.isEmpty(name)) {

                            entryName.setError("error");
                        }
                        if (TextUtils.isEmpty(number)) {

                            entryNumber.setError("error");
                        }
                        mydb.insertContacts(name, number);


                        entryName.setText("");
                        entryNumber.setText("");
                        alert.dismiss();
                        initiallizeListview();

                    }
                });
                alert.show();
            }

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {


              final ContactInfo  value = contactlist.get(position);
//                Log.i(TAG,"String" + position);
                Toast.makeText(MainActivity.this, "" +value.getContactlistId(), Toast.LENGTH_SHORT).show();
//
//


//           ContactInfo contactInfo=new ContactInfo(value.contactlistId,value.contactlistName,value.contactlistNumber);


                Intent intent = new Intent(MainActivity.this, Profile.class);
                intent.putExtra("string",value);

                startActivity(intent);


            }


        });
        initiallizeListview();
    }
//private void setupSearchView(){
//        searchView.setIconifiedByDefault(false);
//
//        searchView.setSubmitButtonEnabled(true);
//        searchView.setQueryHint("search here");
//}
    public void reinitialize( ArrayList<ContactInfo> contactlist){

        Phonebook_CustomAdapter contactInfoadapter = new Phonebook_CustomAdapter(contactlist, context);
        listView.setAdapter(contactInfoadapter);

       contactInfoadapter.notifyDataSetChanged();

    }

    final public void initiallizeListview() {
                contactlist = new ArrayList<>();
                Cursor cursor = mydb.getOldData();
                if (cursor.getCount() == 0) {


                } else {


                    while (cursor.moveToNext()) {

                        ContactInfo contactInfo = new ContactInfo(cursor.getInt(0),cursor.getString(1), cursor.getString(2));

                        contactlist.add(contactInfo);

                    }


                }
                Phonebook_CustomAdapter contactInfoadapter = new Phonebook_CustomAdapter(contactlist, context);
                listView.setAdapter(contactInfoadapter);
                contactInfoadapter.notifyDataSetChanged();

            }
        }



