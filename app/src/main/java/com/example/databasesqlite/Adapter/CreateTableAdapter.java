package com.example.databasesqlite.Adapter;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.databasesqlite.Action.CreateTableAction;
import com.example.databasesqlite.Constants.Types;
import com.example.databasesqlite.Model.DatabaseModel;
import com.example.databasesqlite.Model.RowTable;
import com.example.databasesqlite.R;

import java.util.ArrayList;
import java.util.List;

public class CreateTableAdapter extends RecyclerView.Adapter<CreateTableAdapter.ViewHoder> {
    private List<RowTable> list;
    private CreateTableAction createTableAction;

    public CreateTableAdapter(List<RowTable> list, CreateTableAction createTableAction) {
        this.list = list;
        this.createTableAction = createTableAction;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_create_table,null);
        return new ViewHoder(view,new MyCustomEditTextListener());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, @SuppressLint("RecyclerView") int position) {
        RowTable rowTable = list.get(position);
        holder.myCustomEditTextListener.updatePosition(position);
        holder.edtName.setText(rowTable.getName());
        holder.spinnerStypes.setSelection(rowTable.getIndexType());
        holder.checkboxPrimaryCreateTable.setChecked(rowTable.isPrimary());
        holder.checkboxPrimaryCreateTable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             if(isChecked){
                createTableAction.changeChecked(position);
             }
         }
     });
     holder.btnDelete.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            createTableAction.deleteRowTable(position);
         }
     });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class  ViewHoder extends RecyclerView.ViewHolder {
        Spinner spinnerStypes;
        EditText edtName;
        ImageButton btnDelete;
        CheckBox checkboxPrimaryCreateTable;
        public MyCustomEditTextListener myCustomEditTextListener;

        public ViewHoder(@NonNull View itemView, MyCustomEditTextListener myCustomEditTextListener) {
            super(itemView);
            spinnerStypes = itemView.findViewById(R.id.spinerTypesItem);
            edtName = itemView.findViewById(R.id.editNameItemCreateTable);
            btnDelete = itemView.findViewById(R.id.btnDeleteItemCreateTable);
            checkboxPrimaryCreateTable = itemView.findViewById(R.id.checkboxPrimaryCreateTable);
            String[] items = {Types.INTEGER,Types.TEXT};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    itemView.getContext(), android.R.layout.simple_spinner_item, items);
            adapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item);
            // associate GUI spinner and adapter
            spinnerStypes.setAdapter(adapter);
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.edtName.addTextChangedListener(myCustomEditTextListener);
            this.spinnerStypes.setOnItemSelectedListener(myCustomEditTextListener);

        }
    }
    private class MyCustomEditTextListener implements TextWatcher, AdapterView.OnItemSelectedListener {
        private int position;
        private String[] items = {Types.INTEGER,Types.TEXT};

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
//            String regex = "\\#[0-9a-zA-Z]*\\-[0-9][0-9a-zA-Z]*\\#";

            list.get(position).setName(charSequence.toString().trim());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int p, long id) {
            list.get(this.position).setIndexType(p);
            list.get(this.position).setType(items[p]);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
