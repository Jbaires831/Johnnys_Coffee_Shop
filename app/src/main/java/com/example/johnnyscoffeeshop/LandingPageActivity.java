package com.example.johnnyscoffeeshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.johnnyscoffeeshop.DB.AppDatabase;
import com.example.johnnyscoffeeshop.DB.UserDAO;
import com.example.johnnyscoffeeshop.databinding.ActivityLandingPageBinding;

import org.w3c.dom.Text;

public class LandingPageActivity extends AppCompatActivity {

    TextView mGreeting;
    Button mBrowseBtn;
    Button mSearchBtn;
    Button mHistoryBtn;
    Button mCancelBtn;
    Button mAdminBtn;
    TextView mProfile;

    UserDAO mUserDAO;
    User mUserFound;

    ActivityLandingPageBinding mActivityLandingPageBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        mActivityLandingPageBinding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        View view = mActivityLandingPageBinding.getRoot();

        setContentView(view);

        mGreeting = mActivityLandingPageBinding.startLoginPage;
        mBrowseBtn = mActivityLandingPageBinding.landingBtn;
        mSearchBtn = mActivityLandingPageBinding.searchBtn;
        mHistoryBtn = mActivityLandingPageBinding.historyBtn;
        mCancelBtn = mActivityLandingPageBinding.cancelBtn;
        mAdminBtn = mActivityLandingPageBinding.isAdminBtn;
        mProfile = mActivityLandingPageBinding.profileTextview;

        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries().build().UserDAO();

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        mGreeting.setText("Welcome " + name);

        User mExistingUser = userFound(name);

        if(mExistingUser.isMisAdmin()){
            mAdminBtn.setVisibility(View.VISIBLE);
        }else{
            mAdminBtn.setVisibility(View.INVISIBLE);
        }

        mBrowseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = BrowseMenuActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = getIntent();
                String name = intent1.getStringExtra("name");

                Intent intent = new Intent(getApplicationContext(), SearchItemActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });

        mHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = getIntent();
                String name = intent1.getStringExtra("name");

                Intent intent = new Intent(getApplicationContext(), OrderHistoryActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = getIntent();
                String name = intent1.getStringExtra("name");

                Intent intent = new Intent(getApplicationContext(), CanelOrderActivity.class);
                startActivity(intent);
            }
        });

        mAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = AdminActivity.getIntent(getApplicationContext());
                startActivity(intent2);
            }
        });

        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String name = intent.getStringExtra("name");
                Log.d("profile", "reroute");

                Intent intent4 = new Intent(getApplicationContext(), ProfileActivity.class);
                intent4.putExtra("name", name);
                startActivity(intent4);
            }
        });
    }

    private User userFound(String userName){
        mUserFound = mUserDAO.getUserbyUserName(userName);
        return mUserFound;
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, LandingPageActivity.class);
        return intent;
    }
}