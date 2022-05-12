package com.example.databasesqlite.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.databasesqlite.Constants.Tables;
import com.example.databasesqlite.Constants.Types;
import com.example.databasesqlite.Database.Database;
import com.example.databasesqlite.Model.RowTable;
import com.example.databasesqlite.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
   private Button btnCreateDatabase,btnCreateTable
           ,btnInsertRow,btnDeleteFrom,btnUpdateRowTo,btnQueryingData,btnInsertData,btnQuerying;
//   private EditText edtDeleteTable,edtInsertData;
//           ,btnDeleteTable,btnDeleteDatabase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        action();

    }

    private void action() {
        btnCreateDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CreateDatabaseActivity.class);
                startActivity(intent);
            }
        });

        btnCreateTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CreateTableActivity.class);
                startActivity(intent);
            }
        });
        btnDeleteFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DeleteFromActivity.class);
                startActivity(intent);
            }
        });
    }


    private AlertDialog createDiaLogDeleteDatabase() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        List<Integer> selectedItems = new ArrayList<>();
        String[] item = {"1","2","3"};
        builder.setMultiChoiceItems(item, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            selectedItems.add(which);
                        } else if (selectedItems.contains(which)) {
                            // Else, if the item is already in the array, remove it
                            selectedItems.remove(which);
                        }
                    }
                });
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        return builder.create();
    }


    private  void init(){
        btnCreateDatabase = findViewById(R.id.btnCreateDatabse);
//        btnDeleteDatabase = findViewById(R.id.btnDeleteDatabase);
        btnCreateTable = findViewById(R.id.btnCreateTable);
//        btnDeleteTable = findViewById(R.id.btnDeleteTable);
//        edtDeleteTable = findViewById(R.id.edtDeleteTable);
//        btnInsertRow = findViewById(R.id.btnInsertRow);
        btnDeleteFrom = findViewById(R.id.btnDeleteFrom);
        btnUpdateRowTo = findViewById(R.id.btnUpdateRowTo);
        btnQueryingData = findViewById(R.id.btnQueryingData);
//        btnInsertData = findViewById(R.id.btnInsertData);
//        edtInsertData = findViewById(R.id.edtInsertData);
        btnQuerying = findViewById(R.id.btnQuerying);


    }




}