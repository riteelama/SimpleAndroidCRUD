package com.example.recyclerviewdb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import Adapter.RecyclerViewAdapter;
import data.MyData;
import helper.MyDbHelper;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    RecyclerView.Adapter adapter;

    RecyclerView.LayoutManager layoutManager;

    MaterialButton addBtn, addItemBtn;

    ArrayList<MyData> data;

    MyDbHelper myDbHelper;

    TextView noDataTv;

    private ArrayAdapter<String> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addItemBtn = findViewById(R.id.addItemBtn);
        recyclerView = findViewById(R.id.listRv);

        myDbHelper = new MyDbHelper(this);

        ArrayList<MyData> data = new ArrayList<>();

//        data.add(new MyData(1,"Ritee","USA"));
        Cursor cursor = myDbHelper.selectData();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String address = cursor.getString(2);

            data.add(new MyData(id, name, address));

        }

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerViewAdapter(this,data,myDbHelper);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(data);
            }
        });
    }

    public void showAlertDialog(ArrayList<MyData> data){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("User Information");
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);
        builder.setView(view);

        EditText idEt = view.findViewById(R.id.idEt);
        EditText nameEt = view.findViewById(R.id.nameEt);
        EditText addressEt = view.findViewById(R.id.addressEt);
        MaterialButton addBtn = view.findViewById(R.id.addBtn);

        AlertDialog alert = builder.create();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("Save data", "Data save button clicked");

                String name = nameEt.getText().toString();
                String address = addressEt.getText().toString();
                Integer id = Integer.parseInt(idEt.getText().toString());

                if(!name.isEmpty() && !address.isEmpty() && !(idEt.getText().toString()).isEmpty()){

                    data.add(new MyData(id,name, address));
                    alert.dismiss();
                    myDbHelper.insertData(new MyData(id, name, address));
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(getApplicationContext(), "please enter every value", Toast.LENGTH_LONG).show();
                }
            }
        });

        alert.show();
    }

    private void checkData(ArrayList<MyData> data) {
        if (data.isEmpty()) {
            noDataTv.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noDataTv.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}