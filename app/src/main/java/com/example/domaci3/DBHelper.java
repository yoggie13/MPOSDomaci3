package com.example.domaci3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper
{
    public DBHelper(@Nullable Context context) {
        super(context, "myDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, username TEXT, password TEXT)");
        }catch(Exception e){
            Log.d("Greska", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public User select(String username){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor  = db.rawQuery("SELECT * FROM users", null);
        while(cursor.moveToNext()){
            if(cursor.getString(2).equals(username)) return new User(cursor.getString(1), cursor.getString(2), cursor.getString(3));
        }
        return null;
    }
    public void addRow(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", user.name);
        values.put("username",user.username);
        values.put("password",user.password);

        db.insert("users", null, values);

    }

    public void changePass(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put("password", newPassword);

        db.update("users", value, "username = ?", new String[]{username} );
    }

    public void deleteAcc(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("users", "username = ?", new String[]{username});
    }
}
