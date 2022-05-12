package com.example.databasesqlite.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.databasesqlite.Constants.Tables;
import com.example.databasesqlite.Database.Database;
import com.example.databasesqlite.Model.DatabaseModel;
import com.example.databasesqlite.R;

import java.util.ArrayList;
import java.util.List;

public class DeleteFromActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinerDatabase, spinerTable;
    private Database database;
    private List<DatabaseModel> listDatabase = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_from);
        anhxa();
        database = new Database(this);
        Cursor cursor = database.getDataDatabaseDefaut(Tables.TABLE_DATABASES);
        List<String> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            listDatabase.add(new DatabaseModel(id, name));
            list.add(name);
        }
        if (list.size() == 0) {
            createDialog().show();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinerDatabase.setAdapter(adapter);
        spinerDatabase.setOnItemSelectedListener(this);

    }

    private void anhxa() {
        spinerDatabase = findViewById(R.id.spinerDatabase);
        spinerTable = findViewById(R.id.spinerTable);
    }

    private AlertDialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Không có database nào Vui lòng Tạo database")
                .setPositiveButton("Create Database", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(DeleteFromActivity.this, CreateDatabaseActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(DeleteFromActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}