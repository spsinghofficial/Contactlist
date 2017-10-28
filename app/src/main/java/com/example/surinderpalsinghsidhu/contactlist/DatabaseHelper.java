package com.example.surinderpalsinghsidhu.contactlist;

/**
 * Created by surinderpalsinghsidhu on 2017-10-27.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.provider.ContactsContract;//search
import android.net.Uri;//search
import android.content.ContentUris;//search
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;




/**
 * Created by s1138648 on 25/10/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase sqLiteDatabase;

    static final String DB_Name = "MyContactsList";
    static final String Table_Name = "Contact";
    static final int DB_Version = 1;

    public DatabaseHelper (Context context) {
        super(context, DB_Name, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE " + Table_Name + " (" + Contacts.DbColumns.COLUMN_1 + " int PRIMARY KEY, ";
        sql += Contacts.DbColumns.COLUMN_2 + " TEXT NULL, " + Contacts.DbColumns.COLUMN_3 + " TEXT NULL,"+ Contacts.DbColumns.COLUMN_4 + " TEXT NULL,"
                +Contacts.DbColumns.COLUMN_5 + " TEXT NULL,"+  Contacts.DbColumns.COLUMN_6 + " BLOB NULL)";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE " + Table_Name;
        sqLiteDatabase.execSQL(sql);

        onCreate(sqLiteDatabase);
    }

    public long addContact(Contacts cnt) {    //SQLiteDatabase sqLiteDatabase, ArtGallery art) {

        sqLiteDatabase = this.getWritableDatabase();

        android.content.ContentValues contentValues = new android.content.ContentValues();

        contentValues.put(Contacts.DbColumns.COLUMN_1, cnt.getContactId());
        contentValues.put(Contacts.DbColumns.COLUMN_2, cnt.getContactName());
        contentValues.put(Contacts.DbColumns.COLUMN_3, cnt.getContactPhone());
        contentValues.put(Contacts.DbColumns.COLUMN_4, cnt.getContactEmail());
        contentValues.put(Contacts.DbColumns.COLUMN_5, cnt.getContactAddress());
        contentValues.put(Contacts.DbColumns.COLUMN_6, cnt.getContactPicture());

        return sqLiteDatabase.insert(Table_Name, null, contentValues);

/*
        String sql = "INSERT INTO " + Table_Name + " VALUES ('" + msg + "')";
        sqLiteDatabase.execSQL(sql);
        return 0;*/
    }

    public long updateContact(Contacts cnt) { //SQLiteDatabase sqLiteDatabase, ArtGallery art) {

        sqLiteDatabase = this.getWritableDatabase();
        android.content.ContentValues contentValues = new android.content.ContentValues();

        contentValues.put(Contacts.DbColumns.COLUMN_2, cnt.getContactName());
        contentValues.put(Contacts.DbColumns.COLUMN_3, cnt.getContactPhone());
        contentValues.put(Contacts.DbColumns.COLUMN_4, cnt.getContactEmail());
        contentValues.put(Contacts.DbColumns.COLUMN_5, cnt.getContactAddress());
        contentValues.put(Contacts.DbColumns.COLUMN_6, cnt.getContactPicture());

        return sqLiteDatabase.update(Table_Name, contentValues, Contacts.DbColumns.COLUMN_1 + " = '" + cnt.getContactId() + "'", null);
    }

    public long deleteContact(String cID) { //SQLiteDatabase sqLiteDatabase, String gID) { //ArtGallery art) {

        sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(Table_Name, Contacts.DbColumns.COLUMN_1 + " = '" + cID + "'", null);   //art.getArtGalID() + "'", null);

    }
   /* private String getDisplayNameForContact(Intent intent) {
        Activity a=new Activity();
        Cursor phoneCursor = a.getContentResolver().query(intent.getData(), null, null,
                null, null);
        phoneCursor.moveToFirst();
        int idDisplayName = phoneCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        String name = phoneCursor.getString(idDisplayName);
        phoneCursor.close();
        return name;
    }*/

    public java.util.ArrayList<Contacts> pullContacts() {    //SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase = this.getReadableDatabase();
        java.util.ArrayList<Contacts> retList = new java.util.ArrayList<Contacts> ();

        String[] projection = { Contacts.DbColumns.COLUMN_1, Contacts.DbColumns.COLUMN_2, Contacts.DbColumns.COLUMN_3,
                Contacts.DbColumns.COLUMN_4, Contacts.DbColumns.COLUMN_5, Contacts.DbColumns.COLUMN_6 };
        String sort =  Contacts.DbColumns.COLUMN_2;

        Cursor cursor = sqLiteDatabase.query(
                Table_Name,                     // The table to query
                projection,                     // The columns to return
                null,                           // The columns for the WHERE clause
                null,                           // The values for the WHERE clause
                null,                           // don't group the rows
                null,                           // don't filter by row groups
                sort                            // The sort order
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
//Toast.makeText(getApplicationContext(), "Adding new Gallery - ID is: " + cursor.getString(0) + ", Gallery Description is: " + cursor.getString(1), Toast.LENGTH_SHORT).show();
                retList.add(new Contacts(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getBlob(5)));
/*
                if (cursor.getBlob(0) != null & cursor.getBlob(1) != null & cursor.getBlob(2) != null)
                    retList.add(new ArtGallery(cursor.getString(0), cursor.getString(1), cursor.getBlob(2)));
                else if (cursor.getBlob(0) != null & cursor.getBlob(1) != null)
                    retList.add(new ArtGallery(cursor.getString(0), cursor.getString(1)));
                else
                    retList.add(new ArtGallery());
                */
            }

            cursor.close();
        }
        return retList;
    }
}