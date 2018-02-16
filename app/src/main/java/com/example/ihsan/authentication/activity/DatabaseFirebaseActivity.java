package com.example.ihsan.authentication.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.ihsan.authentication.R;
import com.example.ihsan.authentication.client.FirebaseClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseFirebaseActivity extends AppCompatActivity {

    final static String DB_URL ="https://bebas-121f0.firebaseio.com/";
    EditText nameEditext,urlEditext,infoEditext,ntahEditText;
    Button btnsave,btncancel;
    ListView listView;
    FirebaseClient firebaseClient;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_firebase);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        ref = FirebaseDatabase.getInstance().getReference("hewan");


        listView = (ListView)findViewById(R.id.listview);
        firebaseClient = new FirebaseClient(this,DB_URL,listView,ref);
        firebaseClient.refreshdata();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        displayDialog();
            }
        });
    }

    private void displayDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("save data");
        dialog.setContentView(R.layout.customdialog_layout);
        nameEditext = (EditText)dialog.findViewById(R.id.nameEditText);
        urlEditext = (EditText)dialog.findViewById(R.id.urlEditText);
        ntahEditText = (EditText)dialog.findViewById(R.id.ntahEditText);
        infoEditext = (EditText)dialog.findViewById(R.id.infoEditText);
        btnsave = (Button)dialog.findViewById(R.id.saveBtn);
        btncancel= (Button)dialog.findViewById(R.id.cancelBtn);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseClient.savedata(nameEditext.getText().toString(),infoEditext.getText().toString(),urlEditext.getText().toString(),ntahEditText.getText().toString());
            nameEditext.setText("");
            infoEditext.setText("");
                urlEditext.setText("");
        dialog.dismiss();
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
