package com.example.johnnyscoffeeshop.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.johnnyscoffeeshop.Cart;

import java.util.List;

@Dao
public interface CartDAO {
    @Insert
    void insert(Cart... carts);

    @Update
    void update(Cart... carts);

    @Delete
    void delete(Cart...carts);

    @Query("SELECT * FROM " + AppDatabase.CART_TABLE + " WHERE mUserId = :userId")
    List<Cart> getItemsByUserId(int userId);

    @Query("SELECT * FROM " + AppDatabase.CART_TABLE + " WHERE mUserId = :userId AND mMenuIdl = :menuId")
    Cart getCartByUserMenu(int menuId, int userId);

}
