package com.example.johnnyscoffeeshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.johnnyscoffeeshop.DB.AppDatabase;
import com.example.johnnyscoffeeshop.DB.UserDAO;
import com.example.johnnyscoffeeshop.databinding.ActivityProfileBinding;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    TextView mUsernameProfile;
    Button deleteButton;
    Button logoutButton;

    UserDAO mUserDAO;
    List<User> mUserList;
    User deleteUser;
    User mSpecificUser;

    ActivityProfileBinding mBinding;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mBinding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mUsernameProfile = mBinding.profileTextview;
        deleteButton = mBinding.deleteAccountButton;
        logoutButton = mBinding.logoutAccountButton;

        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries().build().UserDAO();

        mSharedPreferences = getSharedPreferences("LoginName", Context.MODE_PRIVATE);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        mUsernameProfile.setText(name);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog newAlert = new AlertDialog.Builder(ProfileActivity.this).create();
                newAlert.setTitle("You want to delete your account?");
                newAlert.setButton(newAlert.BUTTON_POSITIVE, "NO",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                newAlert.setButton(newAlert.BUTTON_NEGATIVE, "YES",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteUser = mUserDAO.getUserbyUserName(name);

                                if(!definedUser(name)){
                                    Log.d("DELETE ???", deleteUser.toString());
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);

                                    mSharedPreferences = getApplicationContext().getSharedPreferences("LoginName", Context.MODE_PRIVATE);
                                    mSharedPreferences.edit().clear().apply();
                                    mUserDAO.delete(deleteUser);
                                }
                                else{
                                    Log.d("OK", "Ok");
                                }
                                dialogInterface.dismiss();
                            }
                        });
                newAlert.show();
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSharedPreferences = getApplicationContext().getSharedPreferences("LoginName", Context.MODE_PRIVATE);
                mSharedPreferences.edit().clear().apply();

                Intent intent = MainActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private boolean definedUser(String userName){
        mSpecificUser = mUserDAO.getUserbyUserName(userName);
        return mSpecificUser == null;
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, ProfileActivity.class);
        return intent;
    }
}