package com.example.databasesqlite.Model;

import java.util.ArrayList;
import java.util.List;

public class DatabaseModel {
    private int id;
    private  String name;
    private List<String> table;

    public DatabaseModel(int id, String name) {
        this.id = id;
        this.name = name;
        this.table = new ArrayList<>();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTable() {
        return table;
    }

    public void setTable(List<String> table) {
        this.table = table;
    }

    @Override
    public String toString() {
        return "DatabaseModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", table=" + table +
                '}';
    }
}
