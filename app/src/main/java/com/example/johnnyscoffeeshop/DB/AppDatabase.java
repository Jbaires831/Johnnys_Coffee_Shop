package com.example.johnnyscoffeeshop.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.johnnyscoffeeshop.Cart;
import com.example.johnnyscoffeeshop.User;
import com.example.johnnyscoffeeshop.Item;

@Database(entities = {User.class, Item.class, Cart.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "User.db";
    public static final String USER_TABLE = "user_table";
    public static final String ITEM_TABLE = "item_table";
    public static final String CART_TABLE = "cart_table";

    private static volatile AppDatabase instance;
    private static final Object LOCK = new Object();

    public abstract UserDAO UserDAO();
    public abstract ItemDAO ItemDAO();
    public abstract CartDAO CartDAO();

    public static AppDatabase getInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                if (instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}