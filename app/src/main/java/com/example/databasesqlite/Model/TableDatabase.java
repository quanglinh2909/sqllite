package com.example.databasesqlite.Model;

import java.io.Serializable;

public class TableDatabase implements Serializable {
    private String nameTable;
    private String namDatabase;
    private int idDatabase;

    public TableDatabase(String nameTable, String namDatabase, int idDatabase) {
        this.nameTable = nameTable;
        this.namDatabase = namDatabase;
        this.idDatabase = idDatabase;
    }

    public String getNameTable() {
        return nameTable;
    }

    public void setNameTable(String nameTable) {
        this.nameTable = nameTable;
    }

    public String getNamDatabase() {
        return namDatabase;
    }

    public void setNamDatabase(String namDatabase) {
        this.namDatabase = namDatabase;
    }

    public int getIdDatabase() {
        return idDatabase;
    }

    public void setIdDatabase(int idDatabase) {
        this.idDatabase = idDatabase;
    }

    @Override
    public String toString() {
        return "TableDatabase{" +
                "nameTable='" + nameTable + '\'' +
                ", namDatabase='" + namDatabase + '\'' +
                ", idDatabase='" + idDatabase + '\'' +
                '}';
    }
}
