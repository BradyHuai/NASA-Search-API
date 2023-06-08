package com.example.nasasearchapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

public class MainActivity extends AppCompatActivity {

    // UI Components
    SearchView searchView;
    ListView contentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUIComponents();
    }

    private void initUIComponents(){
        searchView = findViewById(R.id.search_view);
        contentList = findViewById(R.id.content_list);
    }
}