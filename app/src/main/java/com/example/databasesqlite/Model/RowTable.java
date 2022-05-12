package com.example.databasesqlite.Model;

public class RowTable {
    private String name;
    private String type;
    private int indexType;
    private boolean primary;
    private boolean auto;

    public RowTable(String name, String type, boolean primary) {
        this.name = name;
        this.type = type;
        this.primary = primary;
        this.indexType = 0;
    }

    public RowTable(String name, String type, boolean primary, boolean auto) {
        this.name = name;
        this.type = type;
        this.primary = primary;
        this.auto = auto;
    }

    public boolean isAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIndexType() {
        return indexType;
    }

    public void setIndexType(int indexType) {
        this.indexType = indexType;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    @Override
    public String toString() {
        return "RowTable{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", primary=" + primary +
                ", auto=" + auto +
                '}';
    }
}
