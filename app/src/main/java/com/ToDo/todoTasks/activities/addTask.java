package com.ToDo.todoTasks.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ToDo.todoTasks.R;
import com.ToDo.todoTasks.helpers.InputValidation;
import com.ToDo.todoTasks.model.ToDo;
import com.ToDo.todoTasks.sql.DatabaseHelper;

import static com.ToDo.todoTasks.R.id.textInputEditTextEmail;
import static com.ToDo.todoTasks.R.id.textInputLayoutEmail;

public class addTask extends AppCompatActivity {


    private TextInputLayout textLayoutTitle;
    private TextInputLayout textLayoutContent;

    private TextInputEditText textInputTitle;
    private TextInputEditText textInputContent;
    private InputValidation inputValidation;
    private DatabaseHelper db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Button btnAdd = (Button) findViewById(R.id.addToDo);


        textLayoutTitle = (TextInputLayout) findViewById(R.id.textLayoutContent);
        textLayoutContent = (TextInputLayout) findViewById(R.id.textLayoutTitle);

        textInputContent = (TextInputEditText) findViewById(R.id.textInputContent);
        textInputTitle = (TextInputEditText) findViewById(R.id.textInputTitle);
        inputValidation = new InputValidation(this);
        db = new DatabaseHelper(this);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputValidation.isInputEditTextFilled(textInputTitle, textLayoutContent,
                        getString(R.string.error_field))) {

                    return;
                } else{
                    AddItem();
                }




            }

        });

    }




    private void AddItem() {
        ToDo todo = new ToDo();
        todo.setTitle(textInputTitle.getText().toString().trim());
        todo.setContent(textInputContent.getText().toString().trim());
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
