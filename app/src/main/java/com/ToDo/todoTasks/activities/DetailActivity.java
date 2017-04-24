package com.ToDo.todoTasks.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.ToDo.todoTasks.R;
import com.ToDo.todoTasks.model.ToDo;
import com.ToDo.todoTasks.sql.DatabaseHelper;

public class DetailActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private EditText titleEdit;
    private EditText contentEdit;
    private Button confrm;
    private ToDo todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titleEdit = (EditText) findViewById(R.id.editTitle);
        confrm = (Button) findViewById(R.id.confirmID);
        contentEdit = (EditText) findViewById(R.id.editContent);
        titleEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);
            }
        });
        contentEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);
            }
        });
        db = new DatabaseHelper(getApplicationContext());

        Intent i = getIntent();
        final int TaskId =  i.getIntExtra("ID",0);
        todo = db.getTaskById(TaskId);

        titleEdit.setText(todo.getTitle());
        contentEdit.setText(todo.getContent());
        confrm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDo updateTask = new ToDo();
                updateTask.setId(TaskId);
                updateTask.setTitle(titleEdit.getText().toString());
                updateTask.setContent(contentEdit.getText().toString());
                db.update(updateTask);
                finish();

            }
        });


    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }




}
