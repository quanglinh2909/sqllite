package com.example.databasesqlite.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databasesqlite.Adapter.InsertTableAdapter;
import com.example.databasesqlite.Constants.Types;
import com.example.databasesqlite.Database.Database;
import com.example.databasesqlite.Model.ColumnTable;
import com.example.databasesqlite.Model.RowTable;
import com.example.databasesqlite.Model.TableDatabase;
import com.example.databasesqlite.R;

import java.util.ArrayList;
import java.util.List;

public class InsertRowTableActivity extends AppCompatActivity {
     private TableDatabase tableDatabase;
     private Database database;
     private RecyclerView recylerViewInsertTable;
     private List<ColumnTable> list = new ArrayList<>();
     private InsertTableAdapter insertTableAdapter;
     private TextView title;
     private Button btnSaveData;
     private ImageButton btnBackCreateTable;
     private TableLayout tableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_row_table);
        init();
        action();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {

            tableDatabase = (TableDatabase) intent.getSerializableExtra("data");
            title.setText("Insert table: "+ tableDatabase.getNameTable());
            database = new Database(this);
            database.createDatabase(tableDatabase.getNamDatabase());
//            Log.d("AAA", database.getTitleTable(tableDatabase.getNameTable(),tableDatabase.getIdDatabase()));

            String nameTitle =  database.getTitleTable(tableDatabase.getNameTable(),tableDatabase.getIdDatabase());
            String[] arr = nameTitle.split(",");
             for (String s : arr){
                 list.add(new ColumnTable(s.trim(),""));
             }

            insertTableAdapter = new InsertTableAdapter(list);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recylerViewInsertTable.setLayoutManager(linearLayoutManager);
            recylerViewInsertTable.setAdapter(insertTableAdapter);

            getData();


        }


    }

    private void action() {
        btnBackCreateTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InsertRowTableActivity.this,CreateDatabaseActivity.class);
                startActivity(intent);


            }
        });

        btnSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    database.insertTable(tableDatabase.getNameTable(),list);
                    getData();
                    Toast.makeText(InsertRowTableActivity.this, " insert thành công!", Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Toast.makeText(InsertRowTableActivity.this, "Lỗi insert???", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        recylerViewInsertTable = findViewById(R.id.recylerViewInsertTable) ;
        title = findViewById(R.id.title);
        btnSaveData = findViewById(R.id.btnSaveData);
        btnBackCreateTable = findViewById(R.id.btnBackCreateTable);
        tableLayout = findViewById(R.id.tableLayout);
    }
    private void getData(){
//        TableRow tableRow = view.findViewById(R.id.tableRow);
        tableLayout.removeAllViews();
        Cursor cursor = database.selectTable(tableDatabase.getNameTable());
        TableRow tableRow1 = new TableRow(InsertRowTableActivity.this);
        tableRow1.setBackgroundColor(getResources().getColor(R.color.purple_500));
        for (int i = 0;i < list.size();i++){
            TextView textView = new TextView(InsertRowTableActivity.this);
            textView.setTextColor(getResources().getColor(R.color.white));
            textView.setTextSize(18);
            textView.setPadding(10,5,5,5);
            textView.setText(list.get(i).getName());
            tableRow1.addView(textView);
        }
        tableLayout.addView(tableRow1);

        while (cursor.moveToNext()){
            TableRow tableRow = new TableRow(InsertRowTableActivity.this);
            for (int i = 0;i < list.size();i++){
                String value = cursor.getString(i);
                TextView textView = new TextView(InsertRowTableActivity.this);
                textView.setTextSize(16);
                textView.setPadding(10,5,5,5);
                textView.setText(value);
                tableRow.addView(textView);
            }
            tableLayout.addView(tableRow);
        }
        cursor.close();
    }
}