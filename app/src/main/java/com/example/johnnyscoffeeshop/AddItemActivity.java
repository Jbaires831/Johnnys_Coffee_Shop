package com.example.johnnyscoffeeshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.johnnyscoffeeshop.DB.AppDatabase;
import com.example.johnnyscoffeeshop.DB.ItemDAO;
import com.example.johnnyscoffeeshop.databinding.ActivityAddItemBinding;
import com.example.johnnyscoffeeshop.databinding.ActivityAdminBinding;

public class AddItemActivity extends AppCompatActivity {

    TextView title;
    EditText mItemName;
    EditText mItemDesc;
    EditText mItemPrice;
    Button mAddBtn;

    ItemDAO mItemDAO;

    ActivityAddItemBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mBinding = ActivityAddItemBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        title = mBinding.addTitle;
        mItemName = mBinding.itemNameEdit;
        mItemDesc = mBinding.itemDescEdit;
        mItemPrice = mBinding.itemPriceEdit;
        mAddBtn = mBinding.itemAddBtn;

        mItemDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries().build().ItemDAO();

        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddMenuItem();
            }
        });
    }

    private void AddMenuItem(){
        String itemName = mItemName.getText().toString();
        String itemDesc = mItemDesc.getText().toString();
        Double itemPrice = Double.parseDouble(mItemPrice.getText().toString());

        Item menuItem = new Item(itemName, itemDesc, itemPrice);

        mItemDAO.insert(menuItem);
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, AddItemActivity.class);
        return intent;
    }
}