package com.example.johnnyscoffeeshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.johnnyscoffeeshop.DB.AppDatabase;
import com.example.johnnyscoffeeshop.DB.UserDAO;
import com.example.johnnyscoffeeshop.databinding.ActivityLoginPageBinding;
import com.example.johnnyscoffeeshop.databinding.ActivityMainBinding;
public class LoginPage extends AppCompatActivity {

    EditText mUsername;
    EditText mPassword;
    Button mLoginBtn;
    TextView mVerifyLogin;
    TextView mNoL0gin;

    UserDAO mUserDAO;
    User mSpecificUser;
    User mDefinedUser;

    SharedPreferences mSharedPreferences;

    ActivityLoginPageBinding mActivityLoginPageBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        mActivityLoginPageBinding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        setContentView(mActivityLoginPageBinding.getRoot());

        mUsername = mActivityLoginPageBinding.usernameEdit;
        mPassword = mActivityLoginPageBinding.passwordEdit;
        mLoginBtn = mActivityLoginPageBinding.LoginPageBtn;
        mVerifyLogin = mActivityLoginPageBinding.textLoginVerify;
        mNoL0gin = mActivityLoginPageBinding.noLoginText;

        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries().build().UserDAO();

        mSharedPreferences = getSharedPreferences("LoginName", Context.MODE_PRIVATE);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();

                if(findUser(username)){
                    mDefinedUser = mUserDAO.getUserbyUserName(username);
                    String p = mDefinedUser.getPassword();

                    if(p.equals(password)){
                        SharedPreferences.Editor editor = mSharedPreferences.edit();
                        editor.putString("LoginName", username);
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
                        intent.putExtra("name", username);
                        startActivity(intent);
                    }
                    else{
                        mVerifyLogin.setText("Username or Password are incorrect");
                    }
                }
                else{
                    mVerifyLogin.setText("Username or Password are incorrect");
                }
            }
        });

        mNoL0gin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = SignUpActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });
    }
    private boolean findUser(String userName){
        mSpecificUser = mUserDAO.getUserbyUserName(userName);
        return mSpecificUser != null;
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, LoginPage.class);
        return intent;
    }
}