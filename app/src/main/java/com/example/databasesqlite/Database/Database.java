package com.example.databasesqlite.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.databasesqlite.Constants.Tables;
import com.example.databasesqlite.Constants.Types;
import com.example.databasesqlite.Model.ColumnTable;
import com.example.databasesqlite.Model.RowTable;

import java.util.List;

public class Database {
    private Context context;
    private ConfigDatabase configDatabase;

    public Database(Context context) {
        this.context = context;
        createTableDefaut(context);
    }
     public Cursor getAllTable(String nameDatabase){
         Cursor c = configDatabase.getWritableDatabase().rawQuery("SELECT name FROM sqlite_master  WHERE type='table'", null);
         return c;
    }
    public boolean createDatabase(String name) {
        configDatabase = new ConfigDatabase(context, name, null, 1);
        ConfigDatabase config = new ConfigDatabase(context, Tables.Databases_LOCAL, null, 1);
        Cursor cursor = getDataDatabaseDefaut(Tables.TABLE_DATABASES);
        boolean check = true;
        while (cursor.moveToNext()) {
            String nameDatabase = cursor.getString(1);
            if(nameDatabase.equals(name)){
                check =false;
                break;
            }
        }
        if(check){
            config.queryData("INSERT INTO "+Tables.TABLE_DATABASES+" VALUES(null,'"+name+"')");
        }
        config.close();
        return check;
    }
    public Cursor getDataDatabaseDefaut(String namTable){
        ConfigDatabase config = new ConfigDatabase(context,Tables.Databases_LOCAL, null, 1);
       return config.getData("SELECT * FROM "+ namTable);
    }
    public void createTableDefaut(Context context){
        configDatabase = new ConfigDatabase(context, Tables.Databases_LOCAL, null, 1);
        //save database
        String queryTable1 = "CREATE TABLE  IF NOT EXISTS "
                + Tables.TABLE_DATABASES+"(Id INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT)";
        //save table
        String queryTable2 = "CREATE TABLE  IF NOT EXISTS "
                + Tables.TABLE_DATABASES_TABLE
                +"(Id INTEGER PRIMARY KEY AUTOINCREMENT,NameTable TEXT,Columns TEXT,IdDatabase INTEGER)";
        configDatabase.queryData(queryTable1);
        configDatabase.queryData(queryTable2);
    }
    public boolean createtable(String tableName,int idDatabase, List<RowTable> rowTableList) {
        String sql = "";
        String columnTitle = "";
        for (int i = 0; i < rowTableList.size();i++) {
            RowTable row = rowTableList.get(i);

            if(i == 0){
                columnTitle += row.getName();
                if (row.isPrimary()) {
                    if(row.isAuto() && row.getType().equals(Types.INTEGER)){
                        sql += " " + row.getName() + " " + row.getType() + " " + Types.PRIMARY_KEY + " " + Types.AUTOINCREMENT+" ";
                    }else{
                        sql += " " + row.getName() + " " + row.getType() + " " + Types.PRIMARY_KEY + " ";
                    }
                } else {
                    sql += " " + row.getName() + " " + row.getType() + " ";
                }
            }else{
                columnTitle +=" ,"+ row.getName();
                if (row.isPrimary()) {
                    if(row.isAuto()){
                        sql += " ," + row.getName() + " " + row.getType() + " " + Types.PRIMARY_KEY + " " + Types.AUTOINCREMENT+" ";
                    }else{
                        sql += " ," + row.getName() + " " + row.getType() + " " + Types.PRIMARY_KEY + " ";
                    }
                } else {
                    sql += " ," + row.getName() + " " + row.getType() + " ";
                }
            }

        }
        String query = "CREATE TABLE  IF NOT EXISTS " + tableName + "( " + sql + " )";
        configDatabase.queryData(query);

        ConfigDatabase config = new ConfigDatabase(context, Tables.Databases_LOCAL, null, 1);
        Cursor cursor = getDataDatabaseDefaut(Tables.TABLE_DATABASES_TABLE);
        boolean check = true;
        while (cursor.moveToNext()) {
            String nameTable = cursor.getString(1);
            int idDatabaseTable = cursor.getInt(3);
            if(nameTable.equalsIgnoreCase(tableName) && idDatabaseTable == idDatabase ){
                check =false;
                break;
            }
        }
        if(check){
            config.queryData("INSERT INTO "+Tables.TABLE_DATABASES_TABLE
                    +" VALUES(null,'"+tableName+"','"+columnTitle+"',"+idDatabase+")");
        }
        config.close();
        return check;
    }
    public void dropTable(String tableName,int idDatabase){
        ConfigDatabase config = new ConfigDatabase(context, Tables.Databases_LOCAL, null, 1);
        config.queryData("DELETE FROM "+Tables.TABLE_DATABASES_TABLE+" WHERE IdDatabase ="+idDatabase+" AND NameTable ='"+tableName+"'");
        configDatabase.queryData("DROP TABLE IF EXISTS " + tableName);
    }
    public void deleteItem(String tableName){
        String delete = "DELETE FROM "+ tableName;
        configDatabase.queryData(delete);
    }
    public void deleteDataBase(int idDatabase){
        ConfigDatabase config = new ConfigDatabase(context, Tables.Databases_LOCAL, null, 1);
        config.queryData("DELETE FROM "+Tables.TABLE_DATABASES_TABLE+" WHERE IdDatabase ="+idDatabase);
        config.queryData("DELETE FROM "+Tables.TABLE_DATABASES+" WHERE Id ="+idDatabase);

    }

    public void insertTable(String nameTable,List<ColumnTable> listValue){
        String sql = "";
        String sql2 ="";

        for (int i = 0; i < listValue.size();i++){
            ColumnTable columnTable = listValue.get(i);
            if(i == 0){
                sql += columnTable.getName();
                sql2 += "'"+columnTable.getValue()+"'";
            }else{
                sql += ","+columnTable.getName();
                sql2 += ",'"+columnTable.getValue()+"'";
            }
        }
        String query = "INSERT INTO "+nameTable+"("+sql+") VALUES( "+sql2+")";
        configDatabase.queryData(query );

    }
    public Cursor selectTable(String namTable){
       return configDatabase.getData("SELECT * FROM "+ namTable);
    }
    public String getTitleTable(String tableName,int  idDatabase){
        Cursor cursor = getDataDatabaseDefaut(Tables.TABLE_DATABASES_TABLE);
        String titleTable = "";
        while (cursor.moveToNext()) {
            String nameTable = cursor.getString(1);
            int idDatabaseTable = cursor.getInt(3);
            if(nameTable.equalsIgnoreCase(tableName) && idDatabaseTable == idDatabase ){
                titleTable = cursor.getString(2);
                break;
            }
        }
        return titleTable;
    }
}
