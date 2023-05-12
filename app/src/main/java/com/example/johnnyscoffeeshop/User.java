package com.example.johnnyscoffeeshop;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.johnnyscoffeeshop.DB.AppDatabase;

@Entity(tableName = AppDatabase.USER_TABLE)
public class User {
    @PrimaryKey(autoGenerate = true)
    private int mUserId;

    private String mUsername;
    private String mPassword;

    private boolean misAdmin;

    public User(String username, String password, boolean misAdmin) {
        mUsername = username;
        mPassword = password;
        this.misAdmin = misAdmin;
    }

    @Override
    public String toString() {
        return
                "Username: " + mUsername + "\n" +
                        "Admin: " + misAdmin + "\n" +
                        "=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=" + "\n";
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public boolean isMisAdmin() {
        return misAdmin;
    }

    public void setMisAdmin(boolean misAdmin) {
        this.misAdmin = misAdmin;
    }
}
