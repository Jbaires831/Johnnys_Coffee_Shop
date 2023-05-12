package com.example.johnnyscoffeeshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.johnnyscoffeeshop.DB.AppDatabase;
import com.example.johnnyscoffeeshop.DB.ItemDAO;
import com.example.johnnyscoffeeshop.databinding.ActivityEditBinding;

public class EditActivity extends AppCompatActivity {

    EditText searchMenuItem;
    EditText changeDescription;
    EditText changeItemprice;
    Button editBtn;

    ItemDAO mItemDAO;
    Item mItemSearch;

    ActivityEditBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mBinding = ActivityEditBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        searchMenuItem = mBinding.editNameEdittext;
        changeDescription = mBinding.editDescripEdittext;
        changeItemprice = mBinding.editPriceEdittext;
        editBtn = mBinding.editItemButton;

        mItemDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries().build().ItemDAO();

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = searchMenuItem.getText().toString();
                String description = changeDescription.getText().toString();
                Double price = Double.valueOf(changeItemprice.getText().toString());

                if(itemSearcher(item)){
                    Item itemFound = mItemDAO.getItemByName(item);
                    itemFound.setItemDescription(description);
                    itemFound.setItemPrice(price);
                    mItemDAO.update(itemFound);
                }else{
                    Log.d("Error", "this item doesn't exist");
                }
            }
        });
    }

    private boolean itemSearcher(String itemName){
        mItemSearch = mItemDAO.getItemByName(itemName);
        return  mItemSearch != null;
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, EditActivity.class);
        return intent;
    }
}