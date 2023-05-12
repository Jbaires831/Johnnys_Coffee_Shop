package com.example.johnnyscoffeeshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.johnnyscoffeeshop.DB.AppDatabase;
import com.example.johnnyscoffeeshop.DB.CartDAO;
import com.example.johnnyscoffeeshop.DB.ItemDAO;
import com.example.johnnyscoffeeshop.DB.UserDAO;
import com.example.johnnyscoffeeshop.databinding.ActivityOrderHistoryBinding;

import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {

    TextView mOrderHistory;
    TextView mAllOrders;

    UserDAO mUserDAO;
    ItemDAO mItemDAO;
    CartDAO mCartDAO;

    User getUsers;
    List<Cart> userHistory;
    Item userOrders;

    ActivityOrderHistoryBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        mBinding = ActivityOrderHistoryBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mOrderHistory = mBinding.orderHistoryTextview;
        mAllOrders = mBinding.allOrdersTextview;

        mUserDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries().build().UserDAO();
        mItemDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries().build().ItemDAO();
        mCartDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries().build().CartDAO();

        Intent i = getIntent();
        String name = i.getStringExtra("name");

        getUsers = mUserDAO.getUserbyUserName(name);
        int userId = getUsers.getUserId();

        updateDisplay(userId);
    }

    private void updateDisplay(int userId){
        userHistory = mCartDAO.getItemsByUserId(userId);
        double orderTotal = 0.00;


        if(! userHistory.isEmpty()){
            StringBuilder sb = new StringBuilder();
            StringBuilder stringBuilt = new StringBuilder();

            for(Cart purchases : userHistory){

                int itemId = purchases.getMenuIdl();

                userOrders = mItemDAO.getItemsByItemId(itemId);

                sb.append(userOrders.getItemName() + "\n$" +
                        userOrders.getItemPrice() + "\n" +
                        "=-=-=-=-=-=-=-=-=-=-=" + "\n");

                orderTotal += userOrders.getItemPrice();
            }

            stringBuilt.append("=-=-=-=-=-=-=-=-=-=-=").append("\n");
            stringBuilt.append("Order Summary").append("/n");
            stringBuilt.append("Order total: $").append(orderTotal).append("\n");

            mAllOrders.setText(stringBuilt);
            mOrderHistory.setText(sb.toString());
        }
        else{
            mOrderHistory.setText("No items have been purchases.");
        }
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, OrderHistoryActivity.class);
        return intent;
    }
}