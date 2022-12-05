package com.samsalek.accountdiary.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.samsalek.accountdiary.AccountDiary;
import com.samsalek.accountdiary.AccountExpandableListAdapter;
import com.samsalek.accountdiary.R;
import com.samsalek.accountdiary.SerializationHandler;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    private final AccountDiary accountDiary = AccountDiary.get();

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;

    public static final String ACCOUNT_GROUP_INDEX = "ACCOUNT_GROUP_INDEX";
    public static final String ACCOUNT_CHILD_INDEX = "ACCOUNT_CHILD_INDEX";
    public static final String SAVE_FILE_NAME = "savedData.txt";

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
        initSaveClickListener();
        initLoadClickListener();

        //accountDiary.addAccount(new Account("A test"));
        //accountDiary.addAccount(new Account("A test 2"));
        //accountDiary.addAccount(new Account("B test"));

        loadData();
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
            Intent intent = new Intent(MainActivity.this, ImageViewActivity.class);
            intent.putExtra(ACCOUNT_GROUP_INDEX, groupPosition);
            intent.putExtra(ACCOUNT_CHILD_INDEX, childPosition);
            startActivity(intent);

            return true;
        });
    }

    private void initSaveClickListener() {
        ImageButton saveImageButton = findViewById(R.id.saveImageButton);
        saveImageButton.setOnClickListener(v -> {
            boolean saveSuccess = saveData();
            if (saveSuccess)
                Toast.makeText(getBaseContext(), "Account data saved successfully!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getBaseContext(), "Could not save account data!", Toast.LENGTH_SHORT).show();
        });
    }

    private void initLoadClickListener() {
        ImageButton loadImageButton = findViewById(R.id.loadImageButton);
        loadImageButton.setOnClickListener(v -> {
            boolean loadSuccess = loadData();
            if (loadSuccess)
                Toast.makeText(getBaseContext(), "Account data loaded successfully!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getBaseContext(), "Could not load account data!", Toast.LENGTH_SHORT).show();
        });
    }

    private boolean saveData() {
        try {
            return SerializationHandler.saveData(openFileOutput(SAVE_FILE_NAME, MODE_PRIVATE));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean loadData() {
        try {
            return SerializationHandler.loadData(openFileInput(SAVE_FILE_NAME));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
