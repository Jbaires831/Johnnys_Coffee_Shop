package com.example.johnnyscoffeeshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.johnnyscoffeeshop.DB.AppDatabase;
import com.example.johnnyscoffeeshop.DB.ItemDAO;
import com.example.johnnyscoffeeshop.databinding.ActivityBrowseMenuBinding;

import java.util.List;

public class BrowseMenuActivity extends AppCompatActivity {

    TextView mDisplayMenu;
    ItemDAO mItemDAO;
    List<Item> mItemList;

    ActivityBrowseMenuBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_menu);

        mBinding = ActivityBrowseMenuBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mDisplayMenu = mBinding.menuItemsTextview;

        mItemDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries().build().ItemDAO();

        updateDisplay();
    }

    private void updateDisplay(){
        mItemList = mItemDAO.getMenuItems();
        if(!mItemList.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for(Item item : mItemList){
                sb.append(item.toString());
            }
            mDisplayMenu.setText(sb.toString());
        }
        else{
            mDisplayMenu.setText("No items have been added");
        }
    }
    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, BrowseMenuActivity.class);
        return intent;
    }
}