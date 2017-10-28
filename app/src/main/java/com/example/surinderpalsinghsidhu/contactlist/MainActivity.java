package com.example.surinderpalsinghsidhu.contactlist;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements android.widget.ListAdapter  {

    java.util.ArrayList<Contacts> dataModel;

//android.widget.EditText txtMessage;

    DatabaseHelper dbHelper;

    //The Viewer
    android.widget.ListView lstContacts;

    int iPos = 0;

    android.widget.Button btnEdit;
    android.widget.Button btnDelete;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//txtMessage = (android.widget.EditText) findViewById(R.id.txtMessage);
        lstContacts = (android.widget.ListView) findViewById(R.id.lstContacts);

        btnEdit = (android.widget.Button) findViewById(R.id.btnEdit);
        btnDelete = (android.widget.Button) findViewById(R.id.btnDelete);

//dataModel = new java.util.ArrayList<String> ();

        dbHelper = new DatabaseHelper(getApplicationContext());
//Toast.makeText(getApplicationContext(), "Before: dataModel.size() " + dataModel.size(), Toast.LENGTH_SHORT).show();

        dataModel = dbHelper.pullContacts();   //dbHelper.getReadableDatabase());

        //Toast.makeText(getApplicationContext(), "After: dataModel.size() " + dataModel.size(), Toast.LENGTH_SHORT).show();

        lstContacts.setAdapter(this);

        lstContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Contacts cont =  (Contacts) getItem(position);

                Toast.makeText(getApplicationContext(), "Contact# " + position + ": Contact ID is: " + cont.getContactId() +
                        ": Contact Name is: " + cont.getContactName(), Toast.LENGTH_SHORT).show();

//txtMessage.setText(getItem(position).toString());
                iPos = position;
                btnEdit.setEnabled(true);
                btnDelete.setEnabled(true);
            }
        });

    }

    public void addContact(android.view.View view) {

        android.content.Intent intent = new android.content.Intent(this, Add_Edit_Contact.class);
        startActivity(intent);
    }

    public void editContact(android.view.View view) {

//dataModel.add(txtMessage.getText().toString());
        Contacts artG =  dataModel.get(iPos);

        android.content.Intent intent = new android.content.Intent(this, Add_Edit_Contact.class);
        intent.putExtra("artG", artG);

        startActivity(intent);

//dataModel = dbHelper.pullMessages(dbHelper.getReadableDatabase());

        //lstMessages.setAdapter(this);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    public void deleteContact(android.view.View view) {
        if (!isEmpty() &&  iPos <  getCount()) {
            Contacts cont = (Contacts) lstContacts.getItemAtPosition(iPos);
            if (cont != null) {
                Toast.makeText(this, dbHelper.deleteContact(cont.getContactId()) != -1 ? "Contact ID: " + cont.getContactId() + " - " +
                        cont.getContactName() + " is deleted" : "Failed to delete " + cont.getContactId() + " - " +
                        cont.getContactName(), Toast.LENGTH_SHORT).show();
                this.recreate();
            }
        }
//lstGalleries.setAdapter(this);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    public void close(android.view.View view) {
        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
        System.exit(0);
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return dataModel.size();
    }

    @Override
    public Object getItem(int i) {
        return dataModel.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

//LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(this.LAYOUT_INFLATER_SERVICE);
        //View item = inflater.inflate(R.layout.item, viewGroup, false);

        android.view.LayoutInflater inflater = (android.view.LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item = inflater.inflate(R.layout.item, viewGroup, false);


        Contacts cont = (Contacts) getItem(i);

        Toast.makeText(getApplicationContext(), "Contact# " + i + " - ID is: " + cont.getContactId() +
                ", Name is: " + cont.getContactName(), Toast.LENGTH_SHORT).show();

        ImageView imgView = (ImageView) item.findViewById(R.id.imgView);

        TextView txtID = (TextView) item.findViewById(R.id.txtID);
        txtID.setText(cont.getContactId());

        TextView txtDescription = (TextView) item.findViewById(R.id.txtDescription);
        txtDescription.setText(cont.getContactName());


        byte[] pic = cont.getContactPicture();

        if (pic != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(pic, 0, pic.length);
            imgView.setImageBitmap(bitmap);
        }
        return item;

    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return getCount() < 1? true : false;
    }
}