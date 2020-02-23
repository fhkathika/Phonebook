package com.example.phonebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Phonebook_DBHelper extends SQLiteOpenHelper {

    private String TAG = this.getClass().getSimpleName();

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACT_TABLE_NAME = "contactInfo";
    public static final String NAME_COLUMN = "name";




    public static final String SELECT_ALL = "SELECT*FROM " + CONTACT_TABLE_NAME;
    private HashMap hp;

    public Phonebook_DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 11);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ CONTACT_TABLE_NAME +" " + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,name text,mobile_number text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newveersion) {
        db.execSQL("DROP TABLE IF EXISTS "+CONTACT_TABLE_NAME+"");
        onCreate(db);
    }

    public boolean insertContacts( String name, String number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put("name", name);
        contentValues.put("mobile_number", number);


        db.insert(CONTACT_TABLE_NAME, null, contentValues);

        return true;
    }
//    public ArrayList<ContactInfo> getById( ) {
//        String Name, Number;
//        SQLiteDatabase mybd = getReadableDatabase();
//       // Cursor cursor = mybd.rawQuery("SELECT * FROM " + CONTACT_TABLE_NAME + " WHERE  id ", null);
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                do {
//                    Name = cursor.getString(1);
//                    Number = cursor.getString(2);
//
//                } while (cursor.moveToNext());
//            }
//        }
//
//    }


    public ArrayList<ContactInfo> search(String keyword) {
        ArrayList<ContactInfo> contactlist = null;
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + CONTACT_TABLE_NAME + " where " + NAME_COLUMN + " like ?", new String[] { "%" + keyword + "%" });
//            if (cursor.moveToFirst()) {
//                contactlist = new ArrayList<ContactInfo>();
//                do {
//                    ContactInfo contactInfo = new ContactInfo(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
//                    //contactInfo.setId(cursor.getInt(0));
//                    contactInfo.setContactlistName(cursor.getString(1));
//                    contactInfo.setContactlistNumber(cursor.getString(2));
//
//                    contactlist.add(contactInfo);
//                } while (cursor.moveToNext());
//            }
        } catch (Exception e) {
            contactlist = null;
        }
        return contactlist;
    }

    public boolean updateContact(Integer ID,String name, String number) {

        Log.i(TAG, "mobile_number : "+number);
        Log.i(TAG, "name "+name);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

//        int res = db.update("contactInfo", contentValues, ID + "= ? ID", new String[]{("ID")});

//        Log.i(TAG, "res : "+res);
     db.execSQL("UPDATE "+CONTACT_TABLE_NAME+" SET  name = "+"'"+name+"'"+ " ,mobile_number = "+"'"+number+"'" +  " Where id= "+"'"+ID+"'" );

        return true;
    }

//    public ArrayList<ContactInfo> findall()  {
//        ArrayList<ContactInfo> contactlist=null;
//        try {
//            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + CONTACT_TABLE_NAME, null);
//            if (cursor.moveToFirst()) {
//                contactlist = new ArrayList<ContactInfo>();
//                do {
//                    ContactInfo contactInfo = new ContactInfo(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
//                   // contactInfo.setId(cursor.getInt(0));
//                    contactInfo.setContactlistName(cursor.getString(1));
//                    contactInfo.setContactlistNumber(cursor.getString(2));
//
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            contactlist = null;
//        }
//        return contactlist;
//    }

    public Cursor getOldData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ALL, null);


        return cursor;
    }
    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contactInfo",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
//    public String deleteContact (String id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String res = db.delete("studentInfo",id + "= ? ", new String[]{id});
//        return  res;
//    }
//    public void deleteItem(ClipData.Item item) {
//        SQLiteDatabase db = getWritableDatabase();
//        String whereClause = "id=?";
//        String whereArgs[] = {item.getText().toString()};
//        db.delete("studentInfo", whereClause, whereArgs);
//    }
//    public int numberOfRows(){
//        SQLiteDatabase db = this.getReadableDatabase();
//        int numRows = (int) DatabaseUtils.queryNumEntries(db, STUDENTS_TABLE_NAME);
//        return numRows;
//
//    }
//
//    public ArrayList<String> getAllCotacts() {
//        ArrayList<String> studentlist = new ArrayList<String>();
//
//        //hp = new HashMap();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res =  db.rawQuery( "select * from studentInfo", null );
//        res.moveToFirst();
//
//        while(res.isAfterLast() == false){
//            studentlist.add(res.getString(res.getColumnIndex(STUDENTS_COLUMN_NAME)));
//            res.moveToNext();
//        }
//        return studentlist;
//    }


}
