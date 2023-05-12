package com.example.johnnyscoffeeshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.johnnyscoffeeshop.databinding.ActivityCanelOrderBinding;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CanelOrderActivity extends AppCompatActivity {
    AutoCompleteTextView mCancelOrderBar;
    TextView mCancelOrderItem;
    Button mFetchItemBtn;
    Button mCancelOrderBtn;

    ActivityCanelOrderBinding mBinding;

    CartDAO mCartDAO;
    ItemDAO mItemDAO;
    UserDAO mUserDAO;
    List<Cart> userCart;

    User mFoundUser;
    User mUser;
    User mNewUser;

    Item mHeldItem;
    Item mItemFind;

    Cart mNewCart;
    Cart mCartFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canel_order);

        mBinding = ActivityCanelOrderBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mCancelOrderBar = mBinding.cancelAutocomplete;
        mFetchItemBtn = mBinding.cancelSearchItemBtn;
        mCancelOrderItem = mBinding.searchedItemTextview;
        mCancelOrderBtn = mBinding.cancelSearchItemBtn;

        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries().build().UserDAO();
        mItemDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries().build().ItemDAO();
        mCartDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries().build().CartDAO();

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        if(findUser(name)){
            mUser = mUserDAO.getUserbyUserName(name);
            int userId = mUser.getUserId();

            userCart = mCartDAO.getItemsByUserId(userId);

            List<String> menuItem = new ArrayList<>();
            for(Cart cart : userCart){
                int itemId = cart.getMenuIdl();

                Item item = mItemDAO.getItemsByItemId(itemId);
                menuItem.add(item.getItemName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuItem);
            mCancelOrderBar.setAdapter(adapter);

            Log.d("tagging", userCart.toString());

            mFetchItemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String search = mCancelOrderBar.getText().toString();

                    if(itemFound(search)){
                        mHeldItem = mItemDAO.getItemByName(search);
                        int menuId = mHeldItem.getItemId();
                        String itemName = mHeldItem.getItemName();

                        if(cartEx(userId, menuId)){
                            mCancelOrderItem.setText(mHeldItem.toString() + "\n Cancel Order?");
                        }else{
                            mCancelOrderItem.setText(name + "doesn't have " + itemName + "in your cart");
                        }
                    }
                    else{
                        mCancelOrderItem.setText("Item doesn't exist");
                    }
                }
            });

            mCancelOrderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String search = mCancelOrderBar.getText().toString();

                    if(itemFound(search)){
                        mHeldItem = mItemDAO.getItemByName(search);

                        Intent i = getIntent();
                        String itemName = i.getStringExtra("name");

                        int menuId = mHeldItem.getItemId();
                        String item = mHeldItem.getItemName();

                        if(findUser(name)){
                            mNewUser = mUserDAO.getUserbyUserName(name);
                            int userId = mNewUser.getUserId();

                            if(cartEx(userId, menuId)){
                                mNewCart = mCartDAO.getCartByUserMenu(menuId, userId);

                                mCartDAO.delete(mNewCart);
                                mCancelOrderItem.setText("");

                                Toast.makeText(CanelOrderActivity.this, "Order has been cancelled", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(CanelOrderActivity.this, item + " isn't in your cart.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(CanelOrderActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(CanelOrderActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            mCancelOrderItem.setText("Error go back");
        }
    }

    private boolean cartEx(int userId, int menuId){
        mCartFind = mCartDAO.getCartByUserMenu(menuId, userId);
        return mCartFind != null;
    }

    private boolean itemFound(String itemName){
        mItemFind = mItemDAO.getItemByName(itemName);
        return mItemFind != null;
    }

    private boolean findUser(String userName){
        mFoundUser = mUserDAO.getUserbyUserName(userName);
        return mFoundUser != null;
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, CanelOrderActivity.class);
        return intent;
    }
}