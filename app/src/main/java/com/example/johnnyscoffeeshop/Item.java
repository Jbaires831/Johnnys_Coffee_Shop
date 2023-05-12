package com.example.johnnyscoffeeshop;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.johnnyscoffeeshop.DB.AppDatabase;

@Entity(tableName = AppDatabase.ITEM_TABLE)
public class Item {
    @PrimaryKey(autoGenerate = true)
    private int mItemId;

    private String mItemName;
    private String mItemDescription;
    private Double mItemPrice;

    public Item(String itemName, String itemDescription, Double itemPrice) {
        mItemName = itemName;
        mItemDescription = itemDescription;
        mItemPrice = itemPrice;
    }

    public int getItemId() {
        return mItemId;
    }

    public void setItemId(int itemId) {
        mItemId = itemId;
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String itemName) {
        mItemName = itemName;
    }

    public String getItemDescription() {
        return mItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        mItemDescription = itemDescription;
    }

    public Double getItemPrice() {
        return mItemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        mItemPrice = itemPrice;
    }

    @Override
    public String toString() {
        return "ItemID: " + mItemId + "\n" +
                "ItemName: " + mItemName + "\n" +
                "Description: " + mItemDescription + "\n" +
                "Price: $" + mItemPrice + "\n" +
                "=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=" + "\n";
    }
}
