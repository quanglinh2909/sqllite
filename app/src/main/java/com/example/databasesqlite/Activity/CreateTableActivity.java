package com.example.databasesqlite.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.databasesqlite.Action.CreateTableAction;
import com.example.databasesqlite.Adapter.CreateTableAdapter;
import com.example.databasesqlite.Constants.Tables;
import com.example.databasesqlite.Database.Database;
import com.example.databasesqlite.Model.DatabaseModel;
import com.example.databasesqlite.Model.RowTable;
import com.example.databasesqlite.R;

import java.util.ArrayList;
import java.util.List;

public class CreateTableActivity extends AppCompatActivity implements CreateTableAction, AdapterView.OnItemSelectedListener {
    private RecyclerView recyclerViewRow;
    private ImageButton btnAddRow,btnBackCreateTable;
    private Spinner spinnerDatabase;
    private CreateTableAdapter createTableAdapter;
    private List<RowTable> listRow = new ArrayList<>();
    private Database database;
    private List<DatabaseModel> listDatabase = new ArrayList<>();
    private DatabaseModel databaseModel;
    private Button btnSaveTable;
    private EditText edtNameTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_table);
        init();
        database = new Database(this);
        action();


        createTableAdapter = new CreateTableAdapter(listRow, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewRow.setLayoutManager(linearLayoutManager);
        recyclerViewRow.setAdapter(createTableAdapter);
        recyclerViewRow.addItemDecoration(new DividerItemDecoration(recyclerViewRow.getContext(), DividerItemDecoration.VERTICAL));


        Cursor cursor = database.getDataDatabaseDefaut(Tables.TABLE_DATABASES);
        List<String> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            listDatabase.add(new DatabaseModel(id, name));
            list.add(name);
        }
        if(list.size() == 0){
            createDialog().show();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinnerDatabase.setAdapter(adapter);
        spinnerDatabase.setOnItemSelectedListener(this);

    }

    private void action() {
        btnAddRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listRow.add(new RowTable("", "", false));
                recyclerViewRow.setAdapter(createTableAdapter);

            }
        });
        btnBackCreateTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateTableActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btnSaveTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idDataBase = databaseModel.getId();
                String nameTable = edtNameTable.getText().toString();

                try {
                    if(!nameTable.trim().isEmpty()){
                        if(listRow.size() > 0){
                            boolean check = true;
                            for (RowTable rowTable: listRow){
                                if(rowTable.getName().trim().isEmpty()){
                                    check = false;
                                    break;
                                }
                            }
                            if(check){
                                Database databaseTable = new Database(CreateTableActivity.this);
                                databaseTable.createDatabase(databaseModel.getName());
                                boolean is =  databaseTable.createtable(nameTable,idDataBase,listRow);
                                if(is){
                                    Intent intent = new Intent(CreateTableActivity.this,CreateDatabaseActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(CreateTableActivity.this, "Tạo bảng thành công", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(CreateTableActivity.this, "Tên bảng đã tồn tại", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(CreateTableActivity.this, "Vui lòng không để trống name row", Toast.LENGTH_SHORT).show();

                            }
                        }else{
                            Toast.makeText(CreateTableActivity.this, "Không có row nào", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(CreateTableActivity.this, "Vui lòng nhập tên table", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(CreateTableActivity.this, "Vui lòng nhập đúng dịnh dạng", Toast.LENGTH_SHORT).show();
                }




            }
        });
    }
    private AlertDialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Không có database nào Vui lòng Tạo database")
                .setPositiveButton("Create Database", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(CreateTableActivity.this,CreateDatabaseActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(CreateTableActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
    private void init() {
        recyclerViewRow = findViewById(R.id.recyclerViewCreateTable);
        btnAddRow = findViewById(R.id.btnAddRow);
        spinnerDatabase = findViewById(R.id.spinnerCreateTable);
        btnBackCreateTable = findViewById(R.id.btnBackCreateTable);
        btnSaveTable = findViewById(R.id.btnSaveTable);
        edtNameTable = findViewById(R.id.edtNameTable);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        databaseModel = listDatabase.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void deleteRowTable(int position) {
        listRow.remove(position);
        recyclerViewRow.setAdapter(createTableAdapter);

    }

    @Override
    public void changeChecked(int position) {
        for (RowTable rowTable: listRow){
             rowTable.setPrimary(false);
        }
        listRow.get(position).setPrimary(true);
        recyclerViewRow.setAdapter(createTableAdapter);
    }
}