package com.samsalek.accountdiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.Window;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final int numLetters = 29;
    private final char[] swedishAlphabet = new char[numLetters];
    private final Map<Character, List<String>> accountMap = new HashMap<>();

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hideTitleBar();
        setContentView(R.layout.activity_main);
        init();
    }

    // NOT NEEDED ANYMORE - this is now done in the two themes.xml files
    private void hideTitleBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
    }

    private void init() {

        populateAlphabet();
        initAccountMap();

        accountMap.get(swedishAlphabet[0]).add("Testing");

        expandableListView = findViewById(R.id.accountExpandableListView);
        expandableListAdapter = new AccountExpandableListAdapter(this, swedishAlphabet, accountMap);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupExpandListener(groupPosition -> {

        });

    }

    private void populateAlphabet() {
        int index = 0;
        for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
            swedishAlphabet[index] = alphabet;
            index++;
        }

        swedishAlphabet[index++] = 'å';
        swedishAlphabet[index++] = 'ä';
        swedishAlphabet[index] = 'ö';
    }

    private void initAccountMap() {
        for (char c : swedishAlphabet)
            accountMap.put(c, new ArrayList<>());
    }
}
