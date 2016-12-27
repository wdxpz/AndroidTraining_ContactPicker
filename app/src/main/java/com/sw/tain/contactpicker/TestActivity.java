package com.sw.tain.contactpicker;

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
import android.widget.Button;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {

    private Button mButton;
    private static int REQUEST_CONTACT = 0;
    private TextView mContactName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mContactName = (TextView)findViewById(R.id.text_view_contact_name);

        mButton = (Button)findViewById(R.id.button_pick_contact);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(Uri.parse("content://contacts/"));
                startActivityForResult(intent, REQUEST_CONTACT);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri dataUri = data.getData();


        Cursor cursor = getContentResolver().query(dataUri, null, null, null, null);

        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
        mContactName.setText(name);
    }
}
