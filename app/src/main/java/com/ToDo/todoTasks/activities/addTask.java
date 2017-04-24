package com.ToDo.todoTasks.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.ToDo.todoTasks.R;
import com.ToDo.todoTasks.model.ToDo;
import com.ToDo.todoTasks.sql.DatabaseHelper;

public class addTask extends AppCompatActivity {


    private EditText titleUser;
    private EditText contentDesc;
    private DatabaseHelper db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Button btnAdd = (Button) findViewById(R.id.addToDo);
        titleUser = (EditText) findViewById(R.id.titleID);
        contentDesc = (EditText) findViewById(R.id.contentDescId);
        db = new DatabaseHelper(this);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddItem();
            }


        });
        titleUser.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);
            }
        });
        contentDesc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);
            }
        });

    }


    private void AddItem() {
        ToDo todo = new ToDo();
        todo.setTitle(titleUser.getText().toString().trim());
        todo.setContent(contentDesc.getText().toString().trim());
        SharedPreferences pref = getApplicationContext().getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        String userid = pref.getString("USER_NUM",null);
        todo.setUserID(userid);
        db.add(todo);
        finish();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



}
