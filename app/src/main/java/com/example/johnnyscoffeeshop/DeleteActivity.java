package com.example.johnnyscoffeeshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.johnnyscoffeeshop.DB.AppDatabase;
import com.example.johnnyscoffeeshop.DB.ItemDAO;
import com.example.johnnyscoffeeshop.databinding.ActivityDeleteBinding;

public class DeleteActivity extends AppCompatActivity {

    EditText mItemName;
    Button mDeleteBtn;
    TextView mFoundItemDisplay;

    Item mFindItem;
    Item mItemFound;

    ItemDAO mItemDAO;

    ActivityDeleteBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        mBinding = ActivityDeleteBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mItemName = mBinding.deleteItemNameEdittext;
        mDeleteBtn = mBinding.deleteItemButton;
        mFoundItemDisplay = mBinding.itemDeletedTextview;

        mItemDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries().build().ItemDAO();

        mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String findItem = mItemName.getText().toString();
                if(itemSearch(findItem)){
                    mItemFound = mItemDAO.getItemByName(findItem);

                    AlertDialog newAlert = new AlertDialog.Builder(DeleteActivity.this).create();
                    newAlert.setTitle("Please  confirm deletion");
                    newAlert.setMessage(mItemFound.toString());
                    newAlert.setButton(newAlert.BUTTON_POSITIVE, "NO",
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i){
                                dialogInterface.dismiss();
                            }
                        });
                    newAlert.setButton(newAlert.BUTTON_NEGATIVE, "YES",
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i){
                                mItemDAO.delete(mItemFound);
                                dialogInterface.dismiss();
                            }
                    });
                    newAlert.show();
                }else{
                    mFoundItemDisplay.setText("Item doesn't exist");
                }
            }
        });
    }

    private boolean itemSearch(String itemName){
        return mItemDAO.getItemByName(itemName) != null;
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, DeleteActivity.class);
        return intent;
    }
}