package com.example.johnnyscoffeeshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johnnyscoffeeshop.DB.AppDatabase;
import com.example.johnnyscoffeeshop.DB.CartDAO;
import com.example.johnnyscoffeeshop.DB.ItemDAO;
import com.example.johnnyscoffeeshop.DB.UserDAO;
import com.example.johnnyscoffeeshop.databinding.ActivitySearchItemBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchItemActivity extends AppCompatActivity {

    AutoCompleteTextView searchBar;
    Button fetchItemBtn;
    TextView itemDescript;
    Button buyItemBtn;
    List<Item> menuItems;

    ActivitySearchItemBinding mBinding;
    ItemDAO mItemDAO;
    Item mItemSearch;
    Item mHeldItem;

    Item mNewItem;

    CartDAO mCartDAO;
    UserDAO mUserDAO;
    User mFindUser;
    User mNewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);

        mBinding = ActivitySearchItemBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();

        setContentView(view);

        searchBar = mBinding.searchAutoComplete;
        fetchItemBtn = mBinding.searchItemButton;
        itemDescript = mBinding.searchedItemTextview;
        buyItemBtn = mBinding.purchaseItemButton;

        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries().build().UserDAO();
        mItemDAO = Room.databaseBuilder(this,  AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries().build().ItemDAO();
        mCartDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries().build().CartDAO();

        menuItems = mItemDAO.getMenuItems();

        List<String> items = new ArrayList<>();
        for(Item item : menuItems){
            String itemName = item.getItemName();
            items.add(itemName);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, items);
        searchBar.setAdapter(adapter);

        fetchItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = searchBar.getText().toString();
                if(itemFinder(search)){
                    mHeldItem = mItemDAO.getItemByName(search);
                    itemDescript.setText(mHeldItem.toString() + "\n confirm purchase");
                }
                else{
                    itemDescript.setText("Item not avaiable");
                }
            }
        });

        buyItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = searchBar.getText().toString();
                if(itemFinder(search)){
                    mHeldItem = mItemDAO.getItemByName(search);
                    Intent i = getIntent();
                    String name = i.getStringExtra("name");

                    if(findUser(name)){
                        mNewUser = mUserDAO.getUserbyUserName(name);
                        int mFoundId = mNewUser.getUserId();
                        int mItemId = mHeldItem.getItemId();

                        Cart newCart = new Cart(mItemId, mFoundId);
                        mCartDAO.insert(newCart);
                        Toast.makeText(SearchItemActivity.this, "Item has been bought", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(SearchItemActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(SearchItemActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean itemFinder(String itemName){
        mItemSearch = mItemDAO.getItemByName(itemName);
        return mItemSearch != null;
    }

    private boolean findUser(String userName){
        mFindUser = mUserDAO.getUserbyUserName(userName);
        return mFindUser != null;
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, SearchItemActivity.class);
        return intent;
    }
}