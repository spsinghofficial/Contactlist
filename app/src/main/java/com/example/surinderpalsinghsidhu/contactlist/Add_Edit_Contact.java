package com.example.surinderpalsinghsidhu.contactlist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import android.app.SearchManager;//search
import android.app.SearchableInfo;//search
import android.provider.ContactsContract;//search
import android.content.Context;
import android.content.Intent;//search



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.ByteArrayOutputStream;

import android.widget.Toast;

public class Add_Edit_Contact extends AppCompatActivity {

    DatabaseHelper dbHelper;
    android.widget.EditText txtID;
    android.widget.EditText txtName;
    android.widget.EditText txtPhone;
    android.widget.EditText txtEmail;
    android.widget.EditText txtAddress;
    android.widget.ImageView imgPicture;
    android.widget.SearchView search;

    private static final int PICK_IMAGE_ID = 234; // the number doesn't matter

    Contacts cont;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__edit__contact);

        dbHelper = new DatabaseHelper(getApplicationContext());

        txtID = (android.widget.EditText) findViewById(R.id.txtId);
        txtName = (android.widget.EditText) findViewById(R.id.txtName);
        txtPhone = (android.widget.EditText) findViewById(R.id.txtPhone);
        txtEmail = (android.widget.EditText) findViewById(R.id.txtEmail);
        txtAddress = (android.widget.EditText) findViewById(R.id.txtAddress);
        imgPicture = (android.widget.ImageView) findViewById(R.id.imgPicture);
       // search = (android.widget.SearchView) findViewById(R.id.search);
        //setupSearchView();//search

        if (getIntent().getSerializableExtra("cont") != null) {
            cont = (Contacts) getIntent().getSerializableExtra("cont");

            txtID.setText(cont.getContactId());
            txtName.setText(cont.getContactName());
            txtPhone.setText(cont.getContactPhone());
            txtEmail.setText(cont.getContactEmail());
            txtAddress.setText(cont.getContactAddress());

            imgPicture.setImageBitmap(BitmapFactory.decodeByteArray(cont.getContactPicture(), 0, cont.getContactPicture().length));
            txtID.setEnabled(false);
        }
    }

    public void saveContact(android.view.View view) {

        Bitmap bitmap = ((BitmapDrawable) imgPicture.getDrawable()).getBitmap();
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, boas);

        byte[] pic = boas.toByteArray();

        if (cont == null) {
            cont = new Contacts(txtID.getText().toString(), txtName.getText().toString(),
                    txtPhone.getText().toString(),txtEmail.getText().toString(), txtAddress.getText().toString(), pic);

            Toast.makeText(this, dbHelper.addContact(cont) != -1 ? "Contact ID: " + cont.getContactId() + " - " +
                    cont.getContactName() + " is successfully added" : "Failed to add Contact " + cont.getContactId() + " - " +
                    cont.getContactName(), Toast.LENGTH_SHORT).show();
        }
        else {
            cont = new Contacts(txtID.getText().toString(), txtName.getText().toString(),
                    txtPhone.getText().toString(),txtEmail.getText().toString(), txtAddress.getText().toString(), pic);

            Toast.makeText(this, dbHelper.updateContact(cont) != -1 ? "Gallery ID: " + cont.getContactId() + " - " +
                    cont.getContactName() + " is successfully updated" : "Failed to update Gallery " + cont.getContactId() + " - " +
                    cont.getContactName(), Toast.LENGTH_SHORT).show();
        }


        android.content.Intent intent = new android.content.Intent(this, MainActivity.class);
        startActivity(intent);

    }
   /* private void setupSearchView() {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
         search = (android.widget.SearchView) findViewById(R.id.search);//final starting
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        search.setSearchableInfo(searchableInfo);
    }
    protected void onNewIntent(Intent intent) {
        if (ContactsContract.Intents.SEARCH_SUGGESTION_CLICKED.equals(intent.getAction())) {
            //handles suggestion clicked query
            //String resultText;

            String displayName = getDisplayNameForContact(intent);
             txtName.setText(displayName);
        } else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // handles a search query
            String query = intent.getStringExtra(SearchManager.QUERY);
            txtName.setText("should search for query: '" + query + "'...");
        }
    }

    private String getDisplayNameForContact(Intent intent) {
        return null;
    }*/


    protected void cancel(View view) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_IMAGE_ID:
                Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
// Scale the image to fill the 75x75 image size
                bitmap = Bitmap.createScaledBitmap(bitmap, 75, 75, true);
                imgPicture.setImageBitmap(bitmap);
// TODO use bitmap
                break;
            default:

                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imgPicture.setImageBitmap(photo);
                break;
        }
    }
    protected void pickupPicture(View view) {
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }
}