package com.example.ihsan.authentication.client;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ihsan.authentication.adapter.CustomAdapter;
import com.example.ihsan.authentication.model.Hewan;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Blackswan on 7/19/2017.
 */

public class FirebaseClient {
    Firebase firebase;
    ArrayList<Hewan> hewanArrayList = new ArrayList<>();
    CustomAdapter customAdapter;
    Context c;
    String DB_URL;
    ListView listView;
     String userid;
     DatabaseReference ref;

     public FirebaseClient(Context c){
         this.c =c;
     }
    public FirebaseClient(Context c, String DB_URL, ListView listView, DatabaseReference ref) {
        this.c = c;
        this.ref=ref;
        this.DB_URL = DB_URL;
        this.listView = listView;
        Firebase.setAndroidContext(c);
        firebase = new Firebase(DB_URL);
    }

    public void savedata(String name,String info, String url, String ntah) {
        Hewan h = new  Hewan();
        String  userid = ref.push().getKey();
        h.setId_hewan(userid);
        h.setName(name);
        h.setInfo(info);
        h.setUrl(url);
        h.setNtah(ntah);
        ref.child(userid).setValue(h);
    }


    public void refreshdata() {
        firebase.addChildEventListener(new com.firebase.client.ChildEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                getUpdates(dataSnapshot);

            }

            @Override
            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                getUpdates(dataSnapshot);

            }

            @Override
            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        } );
    }

    private void getUpdates(final com.firebase.client.DataSnapshot dataSnapshot) {
        hewanArrayList.clear();
        for (com.firebase.client.DataSnapshot ds : dataSnapshot.getChildren()) {
            com.example.ihsan.authentication.model.Hewan h = new  com.example.ihsan.authentication.model.Hewan();

            h.setId_hewan(ds.getValue(Hewan.class).getId_hewan());
            h.setName(ds.getValue(Hewan.class).getName());
            h.setInfo(ds.getValue(Hewan.class).getInfo());
            h.setUrl(ds.getValue(Hewan.class).getUrl());
            hewanArrayList.add(h);

        }
        if (hewanArrayList.size() > 0) {
            customAdapter = new  CustomAdapter(c, hewanArrayList,ref,firebase);

            listView.setAdapter((ListAdapter) customAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Hewan artist = hewanArrayList.get(position);
//                    showUpdateDeleteDialog(artist.getName(), artist.getInfo());

                    //     dataSnapshot.getRef().child(String.valueOf(position)).removeValue();
                    //  dataSnapshot.getRef().setValue(null);
//                    dataSnapshot.getRef().removeValue();
//                    if(dataSnapshot.child("hewan").getValue() != null){
//                        String msg = dataSnapshot.child("hewan").getValue().toString();
//                        return;
//                    }
               //     deletedata(userid);
                    Toast.makeText(c, "test aja", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            Toast.makeText(c, "no data", Toast.LENGTH_SHORT).show();
        }

    }


}