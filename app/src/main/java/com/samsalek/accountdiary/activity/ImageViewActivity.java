package com.samsalek.accountdiary.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.samsalek.accountdiary.Account;
import com.samsalek.accountdiary.AccountDiary;
import com.samsalek.accountdiary.R;

public class ImageViewActivity extends AppCompatActivity {

    private final AccountDiary accountDiary = AccountDiary.get();

    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        init();
    }

    private void init() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) storeAccountData(extras);

        TextView accountNameTextView = findViewById(R.id.accountNameTextView);
        accountNameTextView.setText(account.getName());

        if (account.getImageBitmaps().size() != 0) {
            ImageView accountImagesView = findViewById(R.id.accountImagesView);
            accountImagesView.setImageBitmap(account.getImageBitmaps().get(0));
        }

        initBackClickListener();
    }

    private void storeAccountData(Bundle extras) {
        int groupPosition = extras.getInt(MainActivity.ACCOUNT_GROUP_INDEX);
        int childPosition = extras.getInt(MainActivity.ACCOUNT_CHILD_INDEX);
        account = accountDiary.getAccount(groupPosition, childPosition);
    }

    private void initBackClickListener() {
        ImageButton backImageButton = findViewById(R.id.backImageButton);
        backImageButton.setOnClickListener(v -> {
            finish();   // Finish this activity. Go back to previous activity.
        });

        TextView backTextView = findViewById(R.id.backTextView);
        backTextView.setOnClickListener(v -> {
            finish();   // Finish this activity. Go back to previous activity.
        });
    }
}