package com.example.databasesqlite.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.databasesqlite.Action.ActionDatabase;
import com.example.databasesqlite.Action.ActionItemTable;
import com.example.databasesqlite.Action.CreateTableAction;
import com.example.databasesqlite.Activity.InsertRowTableActivity;
import com.example.databasesqlite.Database.Database;
import com.example.databasesqlite.Model.DatabaseModel;
import com.example.databasesqlite.Model.TableDatabase;
import com.example.databasesqlite.R;

import java.util.ArrayList;
import java.util.List;

public class CreateDatabaseAdapter extends RecyclerView.Adapter<CreateDatabaseAdapter.ViewHonder>implements ActionItemTable {
    private List<DatabaseModel> databaseModelList;
    private ActionDatabase actionDatabase;
    private Context context;

    public CreateDatabaseAdapter(List<DatabaseModel> databaseModelList, ActionDatabase actionDatabase, Context context) {
        this.databaseModelList = databaseModelList;
        this.actionDatabase = actionDatabase;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHonder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_create_database,null);
        return new ViewHonder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHonder holder, @SuppressLint("RecyclerView") int position) {
        DatabaseModel databaseModel = databaseModelList.get(position);
        holder.txtNameDatabase.setText(databaseModel.getName());
//        holder.txtNameDatabase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                actionDatabase.clickItem(position);
//            }
//        });
        ItemTableAdapter itemTableAdapter;
        Database database1 = new Database(holder.itemView.getContext());
        database1.createDatabase(databaseModel.getName());
        Cursor c =  database1.getAllTable(databaseModel.getName());
        List<TableDatabase> tableDatabaseList = new ArrayList<>();
        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                String name = c.getString(0);
                if(!name.equals("android_metadata")){
                    databaseModel.getTable().add(name);
                    tableDatabaseList.add(new TableDatabase(name,databaseModel.getName(),databaseModel.getId()));
                }
                c.moveToNext();
            }
        }
        itemTableAdapter = new ItemTableAdapter(tableDatabaseList,context,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.itemView.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        holder.recyclerView.setAdapter(itemTableAdapter);
        holder.recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

        holder.btnDeleteItemDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionDatabase.clickItem(position);
            }
        });
//        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(holder.itemView.getContext(),InsertRowTableActivity.class);
//                holder.itemView.getContext().startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return databaseModelList.size();
    }

    @Override
    public void clickDelete(int position, TableDatabase tableDatabase) {
        notifyDataSetChanged();
    }


    public class ViewHonder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView txtNameDatabase;
        ImageButton btnDeleteItemDatabase;

        public ViewHonder(@NonNull View itemView) {
            super(itemView);
            txtNameDatabase = itemView.findViewById(R.id.txtNameDatabase);
            recyclerView = itemView.findViewById(R.id.recylerviewItemTabale);
            btnDeleteItemDatabase = itemView.findViewById(R.id.btnDeleteItemDatabase);
//            btnAdd = itemView.findViewById(R.id.btnAdd);

        }

    }
}
