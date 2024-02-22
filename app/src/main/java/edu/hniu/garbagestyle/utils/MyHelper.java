package edu.hniu.garbagestyle.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyHelper extends SQLiteOpenHelper {

    public MyHelper(@Nullable Context context) {
        //(上下文，数据库名字，游标，数据库版本）
        super(context, "UseInfo.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Person(person_id Integer primary key autoincrement," +
                "user_name varchar(20)," +
                "user_pwd varchar(20)," +
                "user_pwd_again varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
