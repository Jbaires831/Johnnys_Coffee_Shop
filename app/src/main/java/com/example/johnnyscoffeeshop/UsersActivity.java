package com.example.johnnyscoffeeshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.johnnyscoffeeshop.DB.AppDatabase;
import com.example.johnnyscoffeeshop.DB.UserDAO;
import com.example.johnnyscoffeeshop.databinding.ActivityUsersBinding;

import java.util.List;

public class UsersActivity extends AppCompatActivity {

    TextView mDisplayUsers;

    UserDAO mUserDAO;
    List<User> mUserList;
    ActivityUsersBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        mBinding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mDisplayUsers = mBinding.usersTextview;

        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries().build().UserDAO();

        updateDisplay();
    }

    private void updateDisplay(){
        mUserList = mUserDAO.getAllUsers();
        if(!mUserList.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for(User user : mUserList){
                sb.append(user.toString());
            }
            mDisplayUsers.setText(sb.toString());
        }
        else{
            mDisplayUsers.setText("List is empty. Add some users buddy");
        }
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, UsersActivity.class);
        return intent;
    }
}