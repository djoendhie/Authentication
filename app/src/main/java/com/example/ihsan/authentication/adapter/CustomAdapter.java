package com.example.ihsan.authentication.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ihsan.authentication.R;
import com.example.ihsan.authentication.client.MyHolder;
import com.example.ihsan.authentication.client.PicassoClient;
import com.example.ihsan.authentication.model.Hewan;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Blackswan on 7/19/2017.
 */

public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<com.example.ihsan.authentication.model.Hewan> hewanArrayList;
    LayoutInflater inflater;
    DatabaseReference ref;
    com.example.ihsan.authentication.client.FirebaseClient firebaseClient;
    Firebase firebase;

    public CustomAdapter(Context c, ArrayList<Hewan> hewanArrayList, DatabaseReference ref, Firebase firebase) {
        this.ref = ref;
        this.c = c;
        this.hewanArrayList = hewanArrayList;
        this.firebase = firebase;
    }


    @Override
    public int getCount() {
        return hewanArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return hewanArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_layout, parent, false);
        }
        MyHolder holder = new MyHolder(convertView);
        holder.nameText.setText(hewanArrayList.get(position).getName());
        holder.infoText.setText(hewanArrayList.get(position).getInfo());
        PicassoClient.downloading(c, hewanArrayList.get(position).getUrl(), holder.img);
        holder.infoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(hewanArrayList.get(position).getInfo())));
                updatedelete(position);
            }
        });
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedelete(position);
            }
        });

        return convertView;
    }

    private void updatedelete(int position) {
        final Dialog dialog = new Dialog(c);
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.updatedelete_layout, null);
        dialog.setContentView(view);
        final EditText nameEditext = (EditText) dialog.findViewById(R.id.nameEditText);
        final EditText urlEditext = (EditText) dialog.findViewById(R.id.urlEditText);
        final EditText infoEditext = (EditText) dialog.findViewById(R.id.infoEditText);
        final EditText ntahEditext = (EditText) dialog.findViewById(R.id.ntahEditText);
        Button btnupdate = (Button) dialog.findViewById(R.id.updateBtn);
        Button btndelete = (Button) dialog.findViewById(R.id.deleteBtn);
        final String name = hewanArrayList.get(position).getName();
        final String info = hewanArrayList.get(position).getInfo();
        final String url = hewanArrayList.get(position).getUrl();
        final String ntah = hewanArrayList.get(position).getNtah();

        final String id = hewanArrayList.get(position).getId_hewan();
        nameEditext.setText(name);
        urlEditext.setText(url);
        infoEditext.setText(info);
        ntahEditext.setText(ntah);

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hewan hewan = new  Hewan(id, nameEditext.getText().toString(), urlEditext.getText().toString(), infoEditext.getText().toString(), ntahEditext.getText().toString());
                Toast.makeText(c, "idhewan" + id, Toast.LENGTH_SHORT).show();
                //firebaseClient.updatedata(nameEditext.getText().toString(),infoEditext.getText().toString(),urlEditext.getText().toString());
                ref.child(id).setValue(hewan);
                Toast.makeText(c, "Updated Successfully", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference idd = FirebaseDatabase.getInstance().getReference("hewan");
                idd.child(id).removeValue();
                Toast.makeText(c, "deleted successfully", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
