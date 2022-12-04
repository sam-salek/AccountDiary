package com.samsalek.accountdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private final AccountDiary accountDiary = AccountDiary.get();

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        expandableListView = findViewById(R.id.accountExpandableListView);
        expandableListAdapter = new AccountExpandableListAdapter(this, accountDiary.getSwedishAlphabet(), accountDiary.getAccountMap());
        expandableListView.setAdapter(expandableListAdapter);

        initAddAccountClickListener();
        initExpandableListChildClickListener();

        accountDiary.addAccount(new Account("A test"));
        accountDiary.addAccount(new Account("A test 2"));
        accountDiary.addAccount(new Account("B test"));
    }

    private void initAddAccountClickListener() {
        Button addAccountButton = findViewById(R.id.addAccountButton);
        addAccountButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AccountViewActivity.class);
            startActivity(intent);
        });
    }

    private void initExpandableListChildClickListener() {
        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {

            // Return if no image stored in account
            if (accountDiary.getAccount(groupPosition, childPosition).getImageBitmaps().size() == 0) {
                return false;
            }

            // Set image
            ImageView image = findViewById(R.id.imageView);
            image.setImageBitmap(accountDiary.getAccount(groupPosition, childPosition).getImageBitmaps().get(0));

            // Toggle visibility
            if (image.getVisibility() == View.VISIBLE)
                image.setVisibility(View.GONE);
            else if (image.getVisibility() == View.GONE)
                image.setVisibility(View.VISIBLE);

            return true;
        });
    }
}
