package com.example.socialpostsapp.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.example.socialpostsapp.pojo.PostModel;
import java.util.HashMap;
import java.util.Map;

public class SQLHelper extends SQLiteOpenHelper {

    public static final String dbName = "Tasks";
    public static final int dbVersion = 1;
    public static SQLiteDatabase db;
    public static Map<Integer, PostModel> myFav = new HashMap<>();

    public SQLHelper(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
        db = this.getWritableDatabase();
        getFav();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Fav (" +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " Title TEXT," +
                "Body TEXT," +
                "UserId INTEGER," +
                "PostId INTEGER); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static void removeFav(Integer postId){
        for (Map.Entry<Integer, PostModel> entry : myFav.entrySet()) {
            if (entry.getValue().getId() == postId) {
                db.delete("Fav","PostId = " + postId, null);
                myFav.remove(entry.getKey());
                break;
            }
        }
    }

    public static void addFav(PostModel postModel){
        String sql = "insert into Fav(Title, Body, UserId, PostId) values (?, ?, ?, ?)";
        String[] values = {postModel.getTitle(), postModel.getBody(), String.valueOf(postModel.getUserId()), String.valueOf(postModel.getId())};
        db.execSQL(sql, values);
    }

    public static boolean isFav(Integer postId){
        for (Map.Entry<Integer, PostModel> entry : myFav.entrySet()) {
            if (entry.getValue().getId() == postId) {
                return true;
            }
        }
        return false;
    }

    public static void getFav(){
        Cursor cursor = db.query("Fav", null,null,null,null,null, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String title = cursor.getString(cursor.getColumnIndex("Title"));
                String body = cursor.getString(cursor.getColumnIndex("Body"));
                int postId = cursor.getInt(cursor.getColumnIndex("PostId"));
                int userId = cursor.getInt(cursor.getColumnIndex("UserId"));
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                myFav.put(id, new PostModel(postId, userId, title, body));
                cursor.moveToNext();
            }
        }

    }
}

//package com.example.todoapp;
//
//        import android.content.Context;
//        import android.database.Cursor;
//        import android.database.sqlite.SQLiteDatabase;
//        import android.database.sqlite.SQLiteOpenHelper;
//
//        import androidx.annotation.Nullable;
//
//        import java.util.HashMap;
//        import java.util.Map;
//
//public class AppSQLiteHelper extends SQLiteOpenHelper {
//
//    public static final String dbName = "Tasks";
//    public static final int dbVersion = 1;
//
//    public AppSQLiteHelper(@Nullable Context context) {
//        super(context, dbName, null, dbVersion); // If not Exist with same Name and Version >> Create Empty DB File
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        // Creat tables SQL
//        // insert   update  delete   select
//        db.execSQL("CREATE TABLE TASKS (" +
//                " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                " NAME TEXT); ");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//    }
//
//    public void insertTask(SQLiteDatabase db, String name){
//        String sql = "insert into TASKS(NAME) values (?)";
//        String[] values = {name};
//        db.execSQL(sql, values);
//    }
//
//    public void deleteTask(SQLiteDatabase db, int id){
//        db.delete("TASKS","_id = " + id, null);
////        String sql = "insert into TASKS(NAME) values (?)";
////        db.execSQL(sql);
//    }
//
//    public Map<Integer, String> getTasks(SQLiteDatabase db){
//        Cursor cursor = db.rawQuery("select * from TASKS",null);
//        Map<Integer, String> tasks = new HashMap<>();
//        if (cursor.moveToFirst()) {
//            while (!cursor.isAfterLast()) {
//                String name = cursor.getString(cursor.getColumnIndex("NAME"));
//                int id = cursor.getInt(cursor.getColumnIndex("_id"));
//                tasks.put(id ,name);
//                cursor.moveToNext();
//            }
//        }
//        return tasks;
//    }
//
//}