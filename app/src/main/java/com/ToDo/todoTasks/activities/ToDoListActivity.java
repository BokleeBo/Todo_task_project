package com.ToDo.todoTasks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ToDo.todoTasks.R;
import com.ToDo.todoTasks.adapters.UsersRecyclerAdapter;
import com.ToDo.todoTasks.model.ToDo;
import com.ToDo.todoTasks.sql.DatabaseHelper;

import java.util.ArrayList;


public class ToDoListActivity extends AppCompatActivity {

    private Button addItem;
    private TextView emptyText;

    private AppCompatActivity activity = ToDoListActivity.this;
    private RecyclerView recyclerViewTodos;
    private static ArrayList<ToDo> listTodo;
    private static UsersRecyclerAdapter todosAdapter;
    private static DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        emptyText = (TextView) findViewById(R.id.empty_view);
        addItem = (Button) findViewById(R.id.addId);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newAddItem = new Intent(getApplicationContext(), addTask.class);
                startActivity(newAddItem);
            }
        });
        initV();
        initObj();



    }

    private void initObj() {

        listTodo = new ArrayList<>();
        todosAdapter = new UsersRecyclerAdapter(this,listTodo);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewTodos.setLayoutManager(layoutManager);
        recyclerViewTodos.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTodos.setHasFixedSize(true);
        recyclerViewTodos.setAdapter(todosAdapter);
        databaseHelper = new DatabaseHelper(activity);
        refresh();



    }

//    void getDataSQLite() {
//
//        //AsyncTask for not blocking UI thread
//        new AsyncTask<Void, Void, Void>() {
//
//            @Override
//            protected Void doInBackground(Void... params) {
//                listTodo.addAll(databaseHelper.getTaskByUser());
//                todosAdapter.notifyDataSetChanged();
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//            }
//        }.execute();
//
//    }

    private void initV() {
        recyclerViewTodos = (RecyclerView) findViewById(R.id.recyclerViewTodos);
    }


    @Override
    protected void onResume() {
        refresh();
        super.onResume();
    }
    public static void refresh(){
        listTodo.clear();
        listTodo.addAll(databaseHelper.getTaskByUser());
        todosAdapter.notifyDataSetChanged();
    }
}
