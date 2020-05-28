package com.app.gorent.data.storage.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLite extends SQLiteOpenHelper {
    private static final String database = "GORENT";
    private static final int VERSION = 2;

    private String databaseStatement = StatementsSQL.tableCategories+StatementsSQL.tableUsers+
            StatementsSQL.tableItems+StatementsSQL.tableItemLending;

    //Constructor
    SQLite(Context context){
        super(context, database, null, VERSION);
    }

    //metodos heredados
    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    public void createTables(SQLiteDatabase db){
        db.execSQL(StatementsSQL.tableCategories);
        db.execSQL(StatementsSQL.tableUsers);
        db.execSQL(StatementsSQL.tableItems);
        db.execSQL(StatementsSQL.tableItemLending);
    }

    public void removeTables(SQLiteDatabase db){
        db.execSQL(StatementsSQL.dropItemLending);
        db.execSQL(StatementsSQL.dropItems);
        db.execSQL(StatementsSQL.dropCategories);
        db.execSQL(StatementsSQL.dropUsers);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        if (newVersion > oldVersion){
            removeTables(db);
            createTables(db);
        }
    }
}
