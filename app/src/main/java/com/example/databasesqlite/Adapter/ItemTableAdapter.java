package com.example.databasesqlite.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.databasesqlite.Action.ActionItemTable;
import com.example.databasesqlite.Activity.InsertRowTableActivity;
import com.example.databasesqlite.Database.Database;
import com.example.databasesqlite.Model.DatabaseModel;
import com.example.databasesqlite.Model.TableDatabase;
import com.example.databasesqlite.R;

import java.util.List;

public class ItemTableAdapter extends RecyclerView.Adapter<ItemTableAdapter.ViewHoder> {
    private List<TableDatabase> list;
    private Context context;
    private ActionItemTable actionItemTable;

    public ItemTableAdapter(List<TableDatabase> list, Context context, ActionItemTable actionItemTable) {
        this.list = list;
        this.context = context;
        this.actionItemTable = actionItemTable;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_table_create_table,null);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, @SuppressLint("RecyclerView") int position) {
        TableDatabase tableDatabase = list.get(position);
       holder.txtNameTable.setText(tableDatabase.getNameTable());
       holder.btnWatch.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
//               actionItemTable.clickInsert(position,tableDatabase);
//               Toast.makeText(holder.itemView.getContext(), position+"", Toast.LENGTH_SHORT).show();

               Intent intent = new Intent(context, InsertRowTableActivity.class);
               Bundle bundle = new Bundle();
               bundle.putSerializable("data",tableDatabase);
               intent.putExtras(bundle);
               context.startActivity(intent);
           }
       });
       holder.btnDeleteItemTable.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               createDialog("Bạn có muốn xóa bản này không",position,tableDatabase).show();
           }
       });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    private AlertDialog createDialog(String mess, int position,TableDatabase tableDatabase) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(mess)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Database database = new Database(context);
                        database.createDatabase(tableDatabase.getNamDatabase());
                        database.dropTable(tableDatabase.getNameTable(),tableDatabase.getIdDatabase());
                        actionItemTable.clickDelete(position,tableDatabase);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView txtNameTable;
        ImageButton btnDeleteItemTable,btnWatch;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            txtNameTable = itemView.findViewById(R.id.txtNameTable);
            btnDeleteItemTable = itemView.findViewById(R.id.btnDeleteItemTable);
            btnWatch = itemView.findViewById(R.id.btnWatch);
        }

    }
}
