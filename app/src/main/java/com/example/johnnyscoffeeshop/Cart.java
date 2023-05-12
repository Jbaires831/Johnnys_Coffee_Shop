package com.example.johnnyscoffeeshop;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.johnnyscoffeeshop.DB.AppDatabase;

@Entity(tableName = AppDatabase.CART_TABLE)
public class Cart {
    @PrimaryKey(autoGenerate = true)
    private int mCartId;

    private int mMenuIdl;
    private int mUserId;

    public Cart(int menuIdl, int userId) {
        mMenuIdl = menuIdl;
        mUserId = userId;
    }

    public int getCartId() {
        return mCartId;
    }

    public void setCartId(int cartId) {
        mCartId = cartId;
    }

    public int getMenuIdl() {
        return mMenuIdl;
    }

    public void setMenuIdl(int menuIdl) {
        mMenuIdl = menuIdl;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "mCartId=" + mCartId +
                ", mMenuIdl=" + mMenuIdl +
                ", mUserId=" + mUserId +
                '}';
    }
}
