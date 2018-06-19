package com.example.mars0925.myapplication;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;

import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.WRITE_CONTACTS;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView recyclerView;
    private Adapter adapter;

    private static final int request_contacts = 1;

    /*查詢的欄位名稱*/
    private static final String[] CONTACTS_SUMMARY_PROJECTION = new String[]{
            Contacts._ID,
            Contacts.DISPLAY_NAME,
            Phone.NUMBER};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        int permission = ActivityCompat.checkSelfPermission(this, READ_CONTACTS);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            //沒有權限的時候 向使用者請求權限
            ActivityCompat.requestPermissions(this, new String[]{READ_CONTACTS, WRITE_CONTACTS}, request_contacts);

        } else {
            //已經有權限
            readContact();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case request_contacts:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { //同意取得權限要做的事
                    readContact();
                } else//沒有同意取得權限要做的事{
                    new AlertDialog.Builder(this)
                            .setMessage("必須取得權限能")
                            .setPositiveButton("OK", null)
                            .show();
        }
    }

    private void readContact() {
        getLoaderManager().initLoader(0, null, this);

        adapter = new Adapter();
        recyclerView.setAdapter(adapter);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Phone.CONTENT_URI;

        return new android.content.CursorLoader(this, baseUri,
                CONTACTS_SUMMARY_PROJECTION, null, null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
