package com.sw.tain.contactpicker;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ContactPickerActivity extends AppCompatActivity {

    private ListView mContactListView;
    private Cursor mCursor;
    private SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_picker);

        mContactListView = (ListView)findViewById(R.id.list_view_contact_list);

        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_CONTACTS},
                    1);
        }
        mCursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        String[] from = new String[]{ContactsContract.Contacts.DISPLAY_NAME};
        int[] to = new int[]{R.id.list_item_contact};
        mAdapter = new SimpleCursorAdapter(this, R.layout.list_item_contact_picker, mCursor, from, to);

        mContactListView.setAdapter(mAdapter);


        mContactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCursor.moveToPosition(position);
                int contactId = mCursor.getInt(mCursor.getColumnIndexOrThrow("_id"));

                Uri outUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);

                Intent intent = new Intent();
                intent.setData(outUri);
                setResult(Activity.RESULT_OK, intent);

                finish();

            }
        });

    }
}
