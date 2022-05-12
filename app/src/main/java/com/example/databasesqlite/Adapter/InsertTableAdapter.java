package com.example.databasesqlite.Adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.databasesqlite.Constants.Types;
import com.example.databasesqlite.Model.ColumnTable;
import com.example.databasesqlite.Model.RowTable;
import com.example.databasesqlite.Model.TableDatabase;
import com.example.databasesqlite.R;

import java.util.List;

public class InsertTableAdapter extends RecyclerView.Adapter<InsertTableAdapter.ViewHoder> {
    private List<ColumnTable> list;

    public InsertTableAdapter(List<ColumnTable> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public InsertTableAdapter.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_insert_table,null);
        return new ViewHoder(view,new MyCustomEditTextListener());
    }

    @Override
    public void onBindViewHolder(@NonNull InsertTableAdapter.ViewHoder holder, int position) {
        ColumnTable columnTable = list.get(position);
        holder.myCustomEditTextListener.updatePosition(position);
        holder.txtName.setText(columnTable.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView txtName;
        EditText edtValue;
        MyCustomEditTextListener myCustomEditTextListener;

        public ViewHoder(@NonNull View itemView,MyCustomEditTextListener myCustomEditTextListener) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtNameColumn);
            edtValue = itemView.findViewById(R.id.edtDataRow);
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.edtValue.addTextChangedListener(myCustomEditTextListener);
        }
    }
    private class MyCustomEditTextListener implements TextWatcher {
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
            list.get(position).setValue( charSequence.toString().trim());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }


    }
}
