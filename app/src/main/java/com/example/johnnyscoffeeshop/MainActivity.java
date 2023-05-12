package com.example.johnnyscoffeeshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.johnnyscoffeeshop.DB.AppDatabase;
import com.example.johnnyscoffeeshop.DB.UserDAO;
import com.example.johnnyscoffeeshop.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    int count = 0;
    TextView mTitle;
    Button mLoginBtn;
    Button mSignUpBtn;
    UserDAO mUserDAO;
    User mDefinedUser;

    ActivityMainBinding mActivityMainBinding;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mActivityMainBinding.getRoot();

        setContentView(view);

        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries().build().UserDAO();

        if(!findUserInfo("testuser1")){
            User preUser = new User("testuser1", "testuser1", false);
            mUserDAO.insert(preUser);
        }
        if(!findUserInfo("admin2")){
            User preAdmin = new User("admin2", "admin2", true);
            mUserDAO.insert(preAdmin);
        }

        Log.d("tag", count + " ");

        mTitle = mActivityMainBinding.titleId;
        mLoginBtn = mActivityMainBinding.loginBtn;
        mSignUpBtn = mActivityMainBinding.signBtn;

        mSharedPreferences = getApplicationContext().getSharedPreferences("LoginName", Context.MODE_PRIVATE);

        if(mSharedPreferences.contains("LoginName")){
            Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
            String username = mSharedPreferences.getString("LoginName", "");
            intent.putExtra("name", username);
            startActivity(intent);
        }

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count +=1;
                Intent intent = LoginPage.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count +=1;
                Intent intent = SignUpActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private boolean findUserInfo(String userName){
        mDefinedUser = mUserDAO.getUserbyUserName(userName);
        return mDefinedUser != null;
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }
}