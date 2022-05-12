package com.example.databasesqlite.Activity;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.databasesqlite.Action.ActionDatabase;
import com.example.databasesqlite.Adapter.CreateDatabaseAdapter;
import com.example.databasesqlite.Constants.Tables;
import com.example.databasesqlite.Database.Database;
import com.example.databasesqlite.Model.DatabaseModel;
import com.example.databasesqlite.R;

import java.util.ArrayList;
import java.util.List;


public class CreateDatabaseActivity extends AppCompatActivity implements ActionDatabase {
    private ImageButton btnAddDatabase,btnBackCreateDatabase;
    private RecyclerView recylerViewCreateDatabase;
    private CreateDatabaseAdapter createDatabaseAdapter;
    private List<DatabaseModel> list = new ArrayList<>();
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_database);
        init();
        action();
        database = new Database(CreateDatabaseActivity.this);

        createDatabaseAdapter = new CreateDatabaseAdapter(list,this,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recylerViewCreateDatabase.setLayoutManager(linearLayoutManager);
        recylerViewCreateDatabase.setAdapter(createDatabaseAdapter);


        getData();

    }

    private void action() {
        btnAddDatabase.setOnClickListener(v -> {
            AlertDialog dialog = createDiaLogCreateDatabase();
            dialog.show();
        });
        btnBackCreateDatabase.setOnClickListener(v -> {
            Intent intent = new Intent(CreateDatabaseActivity.this,MainActivity.class);
            startActivity(intent);
        });
    }

    private void init() {
        recylerViewCreateDatabase = findViewById(R.id.recylerViewCreateDatabase);
        btnAddDatabase = findViewById(R.id.btnAddDatabase);
        btnBackCreateDatabase = findViewById(R.id.btnBackCreateDatabase);
    }
    private AlertDialog createDiaLogCreateDatabase() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_create_database,null);
        EditText editText = view.findViewById(R.id.username);
        builder.setView(view);

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                String nameDatabase = editText.getText().toString();
                if(nameDatabase.trim().length() > 0){
                   boolean isCreateDatabase =  database.createDatabase(nameDatabase);
                   if(isCreateDatabase){
                       Toast.makeText(CreateDatabaseActivity.this, "Tạo Database thành công!!", Toast.LENGTH_SHORT).show();
                        getData();
                   }else{
                       Toast.makeText(CreateDatabaseActivity.this, "Database "+nameDatabase+ " đã tồn tại", Toast.LENGTH_SHORT).show();
                   }

                }else{
                    Toast.makeText(CreateDatabaseActivity.this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        return builder.create();
    }
    private  void getData(){
        list.clear();
        Cursor cursor = database.getDataDatabaseDefaut(Tables.TABLE_DATABASES);
        while (cursor.moveToNext()) {
            int idDatabase = cursor.getInt(0);
            String name = cursor.getString(1);
            list.add(new DatabaseModel(idDatabase,name));
        }
        createDatabaseAdapter.notifyDataSetChanged();
    }

    @Override
    public void clickItem(int postion) {
        createDialog("Bạn có chắc chắn muốn xóa datbase này không",postion).show();
//        Database database1 = new Database(this);
//        database1.createDatabase(databaseModel.getName());

    }

    private AlertDialog createDialog(String mess,int postion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mess)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DatabaseModel databaseModel = list.get(postion);
                        database.deleteDataBase(databaseModel.getId());
                        getApplicationContext().deleteDatabase(databaseModel.getName());
                        getData();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}