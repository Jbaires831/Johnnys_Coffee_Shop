package com.example.johnnyscoffeeshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.johnnyscoffeeshop.databinding.ActivityAdminBinding;

public class AdminActivity extends AppCompatActivity {

    Button addBtn;
    Button editBtn;
    Button deleteBtn;
    Button viewBtn;

    ActivityAdminBinding mActivityAdminBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mActivityAdminBinding = ActivityAdminBinding.inflate(getLayoutInflater());
        View view = mActivityAdminBinding.getRoot();

        setContentView(view);

        addBtn = mActivityAdminBinding.addButton;
        editBtn = mActivityAdminBinding.editButton;
        deleteBtn = mActivityAdminBinding.deleteButton;
        viewBtn = mActivityAdminBinding.viewUsersButton;

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);
                startActivity(intent);
            }
        });

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UsersActivity.class);
                startActivity(intent);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DeleteActivity.class);
                startActivity(intent);
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                startActivity(intent);
            }
        });

    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, AdminActivity.class);
        return intent;
    }
}