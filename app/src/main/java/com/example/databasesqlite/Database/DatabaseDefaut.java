package com.example.databasesqlite.Database;

import android.content.Context;
import android.database.Cursor;

import com.example.databasesqlite.Constants.Tables;

public class DatabaseDefaut {
    private Context context;

    public DatabaseDefaut(Context context) {
        this.context = context;
    }

    public Cursor getDataTableDefaut(String namTable){
        ConfigDatabase config = new ConfigDatabase(context, Tables.Databases_LOCAL, null, 1);
        return config.getData("SELECT * FROM "+ namTable);
    }
}
