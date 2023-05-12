package com.example.johnnyscoffeeshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
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
import com.example.johnnyscoffeeshop.databinding.ActivityMainBinding;
import com.example.johnnyscoffeeshop.databinding.ActivitySignUpBinding;

import org.w3c.dom.Text;

public class SignUpActivity extends AppCompatActivity {

    TextView mSignTitle;
    EditText mUsername;
    EditText mPassword;
    EditText mReEnter;
    Button mSignUp;
    TextView mNoSignUp;

    TextView mUserVerify;
    TextView mPasswordVerify;
    TextView mReEnterVerify;

    UserDAO mUserDAO;
    User mSpecificUser;
    User deleteUser;

    ActivitySignUpBinding mActivitySignUpBinding;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mActivitySignUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(mActivitySignUpBinding.getRoot());

        mSignTitle = mActivitySignUpBinding.titleSignUp;
        mUsername = mActivitySignUpBinding.signUpUsername;
        mPassword = mActivitySignUpBinding.signUpPassword;
        mReEnter = mActivitySignUpBinding.reEnterPassword;


        mSignUp = mActivitySignUpBinding.signUpBtn;
        mNoSignUp = mActivitySignUpBinding.refuseSignup;

        mUserVerify = mActivitySignUpBinding.verifyUsername;
        mPasswordVerify = mActivitySignUpBinding.verifyPassword;
        mReEnterVerify = mActivitySignUpBinding.verifyReEnterpassword;

        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries().build().UserDAO();

        mSharedPreferences = getSharedPreferences("LoginName", Context.MODE_PRIVATE);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                //user info
                String username = mUsername.getText().toString();
                //validate
                if(specficUsername(username)){
                    if (username.length() < 4){
                        mUserVerify.setText("Invalid Username. Must be longer than 4 characters");
                    }
                    else{
                        mUserVerify.setText("");
                    }
                }else{
                    mUserVerify.setText("User is already taken choose another on.");
                }

                //password
                if(mPassword.getText().toString().length() > 4){
                    mPasswordVerify.setText("");
                    if(mPassword.getText().toString().equals(mReEnter.getText().toString())){
                        mReEnterVerify.setText("");
                    }else{
                        mReEnterVerify.setText("Passwords don't match");
                    }
                }else{
                    mPasswordVerify.setText("password mst be longer than 4 characters");
                }
                if(specficUsername(username)){
                    if(username.length() > 4){
                        if(mPassword.getText().toString().length() > 4){
                            if(mPassword.getText().toString().equals(mReEnter.getText().toString())){
                                submitUserInfo();

                                SharedPreferences.Editor editor = mSharedPreferences.edit();
                                editor.putString("LoginName", username);
                                editor.apply();

                                Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
                                intent.putExtra("name", username);
                                startActivity(intent);
                            }
                        }
                    }
                }
            }
        });

        mSignUp.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(specficUsername(mUsername.getText().toString())){
                    if(mUsername.getText().toString().length() < 4){
                        mUserVerify.setText("Username must be longer than 4 characters");
                    }
                    else{
                        mUsername.setText("");
                    }
                }else{
                    mUserVerify.setText("Username is already taken");
                }

                if(mPassword.getText().toString().length()>4){
                    mPasswordVerify.setText("");
                    if(mPassword.getText().toString().equals(mReEnter.getText().toString())){
                        mReEnterVerify.setText("");
                    }
                    else{
                        mReEnterVerify.setText("Passwords don't match");
                    }
                }else{
                    mPasswordVerify.setText("Password must be longer than 4 characters");
                }
                //passes all cases
                if(specficUsername(mUsername.getText().toString())){
                    if(mUsername.getText().toString().length() > 4){
                        if(mPassword.getText().toString().length() > 4){
                            if(mPassword.getText().toString().equals(mReEnter.getText().toString())){
                                submitAdminInfo();

                                String username = mUsername.getText().toString();
                                Intent intent = new Intent(getApplicationContext(),LandingPageActivity.class);
                                intent.putExtra("name", username);
                                startActivity(intent);
                            }
                        }
                    }
                }
//                if(mUsername.getText().toString().equals("DeleteMyAccount")){
//                    String deleteString = "Yes";
//                    deleter = mUserDAO.getUserbyUserName(deleteString);
//                }

                return false;
            }
        });

        mNoSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LoginPage.getIntent(getApplicationContext());
                startActivity(intent);
                //work on loginActivity
            }
        });
    }
    private void submitUserInfo(){
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        boolean isAdmin = false;

        User user = new User(username, password, isAdmin);

        mUserDAO.insert(user);
    }

    private void submitAdminInfo(){
        String username = mUsername.getText().toString();
        String passsword = mPassword.getText().toString();
        boolean isAdmin = true;

        User admin = new User(username, passsword,isAdmin);

        mUserDAO.insert(admin);
    }

    private boolean specficUsername(String userName){
        mSpecificUser = mUserDAO.getUserbyUserName(userName);

        return mSpecificUser == null;
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, SignUpActivity.class);
        return intent;
    }
}