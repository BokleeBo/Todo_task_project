package com.ToDo.todoTasks.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ToDo.todoTasks.R;
import com.ToDo.todoTasks.activities.DetailActivity;
import com.ToDo.todoTasks.activities.ToDoListActivity;
import com.ToDo.todoTasks.model.ToDo;
import com.ToDo.todoTasks.sql.DatabaseHelper;

import java.util.ArrayList;


public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder> {

    private ArrayList<ToDo> ToDoTasks;
    private Context context;
    private DatabaseHelper db;

    public UsersRecyclerAdapter(Context context, ArrayList<ToDo> toDoTasks) {
        this.ToDoTasks = toDoTasks;
        this.context = context;

    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_recycler, parent, false);




        return new UserViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(UserViewHolder holder, final int position) {
        holder.textViewTitle.setText(ToDoTasks.get(position).getTitle());
        holder.textViewContent.setText(ToDoTasks.get(position).getContent());
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DatabaseHelper(context);
                db.deleteRow(ToDoTasks.get(position));
                ToDoListActivity.refresh();
                Toast.makeText(context, "Successfully deleted an item", Toast.LENGTH_SHORT).show();
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailActivity = new Intent(context, DetailActivity.class);
                detailActivity.putExtra("ID",ToDoTasks.get(position).getId());
                context.startActivity(detailActivity);
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.v(UsersRecyclerAdapter.class.getSimpleName(), " " + ToDoTasks.size());
        return ToDoTasks.size();
    }


    /**
     * ViewHolder class
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewTitle;
        public AppCompatTextView textViewContent;
        public Button del;
        public Button edit;

        public UserViewHolder(View view) {
            super(view);
            textViewTitle = (AppCompatTextView) view.findViewById(R.id.textViewTitle);
            textViewContent = (AppCompatTextView) view.findViewById(R.id.textViewContent);
            del = (Button) view.findViewById(R.id.btnDel);
            edit = (Button) view.findViewById(R.id.btnEdit);

        }


    }



}
