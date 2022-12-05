package com.samsalek.accountdiary.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.samsalek.accountdiary.Account;
import com.samsalek.accountdiary.AccountDiary;
import com.samsalek.accountdiary.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class AccountViewActivity extends AppCompatActivity {

    AccountDiary accountDiary = AccountDiary.get();

    Bitmap selectedImageBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_view);
        init();
    }

    private void init() {
        initAddImageClickListener();
        initSaveClickListener();
    }

    private void initAddImageClickListener() {
        Button gallery = findViewById(R.id.addImageButton);
        gallery.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            addImageActivityResultLauncher.launch(intent);
        });
    }

    private void initSaveClickListener() {
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> {

            // Get account name from EditText input
            EditText accountNameEditText = findViewById(R.id.accountNameEditText);
            String accountName = accountNameEditText.getText().toString();

            // Don't allow duplicate accounts
            if (accountDiary.accountExists(accountName)) {
                Toast.makeText(getBaseContext(), "Account already exists! Try another name.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create new account
            Account newAccount = new Account(accountName);
            newAccount.addImageBitmap(selectedImageBitmap);
            accountDiary.addAccount(newAccount);

            // Finish this activity. Go back to previous activity.
            finish();
        });
    }

    ActivityResultLauncher<Intent> addImageActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK && result.getData().getData() != null) {

            ImageView imageView = findViewById(R.id.imageView);

            try {
                // Get selected image bitmap and store it.
                InputStream inputStream = getContentResolver().openInputStream(result.getData().getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

                selectedImageBitmap = bitmap;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            imageView.setVisibility(View.VISIBLE);
        }
    });
}