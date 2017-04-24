package com.ToDo.todoTasks.sql;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ToDo.todoTasks.model.ToDo;
import com.ToDo.todoTasks.model.User;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {


    Context ctx;
    // Database Version
    private static final int DB_VER = 1;

    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    // User table name
    private static final String TABLE_USER = "user";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    //Table mToDo task name
    private final static String mTODO = "Todos";

    //Todos table columns names
    private final static String TASK_ID = "task_Id"; //autoincrement
    private final static String user_id = "user_id";
    private final static String TITLE = "title";
    private final static String CONTENT = "content";


    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    private String CREATE_mTODO_TABLE = "CREATE TABLE " + mTODO + "("
            + TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + user_id + " TEXT, "
            + TITLE + " TEXT," + CONTENT + " TEXT" + ")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VER);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_mTODO_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);


        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    public void add(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void add(ToDo todoTask) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TITLE, todoTask.getTitle());
        values.put(CONTENT, todoTask.getContent());
        values.put(user_id, todoTask.getUserID());
        // Inserting Row

        db.insert(mTODO, null, values);
        db.close();
    }
    public void update(ToDo todo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TITLE, todo.getTitle());
        values.put(CONTENT, todo.getContent());

        // Inserting Row
        db.update(mTODO,values,"task_Id=" + todo.getId(),null);
        db.close();
    }


    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public ArrayList<ToDo> getTaskByUser() {
        // array of columns to fetch
        String[] columns = {
                TASK_ID,
                user_id,
                TITLE,
                CONTENT
        };

        ArrayList<ToDo> taskList = new ArrayList<ToDo>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        SharedPreferences pref = ctx.getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        String userid = pref.getString("USER_NUM", null);
        Cursor cursor = db.query(
                mTODO,
                columns,
                "user_id = ?",
                new String[]{userid},
                null,
                null,
                null);


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ToDo todo = new ToDo();
                todo.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TASK_ID))));
                todo.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
                todo.setUserID(cursor.getString(cursor.getColumnIndex(user_id)));
                todo.setContent(cursor.getString(cursor.getColumnIndex(CONTENT)));
                // Adding user record to list
                taskList.add(todo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return taskList;
    }

    public void deleteRow(ToDo todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(mTODO, TASK_ID + " = ?", new String[]{String.valueOf(todo.getId())});
        db.close();
    }




    public ToDo getTaskById (int taskid){
        SQLiteDatabase d = this.getReadableDatabase();
        ToDo todo = new ToDo();
        String[] columns = {
                TASK_ID,
                TITLE,
                CONTENT,
                user_id
        };

        Cursor cursor = d.query(
                mTODO,
                columns,
                "task_Id = ?",//Where value
                new String[]{String.valueOf(taskid)},
                null,
                null,
                null
        );

        try {
            if (cursor.moveToFirst()) {
                todo.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TASK_ID))));
                todo.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
                todo.setUserID(cursor.getString(cursor.getColumnIndex(user_id)));
                todo.setContent(cursor.getString(cursor.getColumnIndex(CONTENT)));
                // Adding user record to list

            }
        } catch (Exception e){
            e.printStackTrace();
        }


        return todo;
    }







    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition

        Cursor cursor = db.query(
                TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     */
    public long checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ? " + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions

        long userid = 0;
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        cursor.moveToFirst();

        int cursorCount = cursor.getCount();

        if (cursorCount > 0) {
            userid = cursor.getLong(0);
            cursor.close();
            db.close();
            return userid;
        }

        return 0;
    }
}
