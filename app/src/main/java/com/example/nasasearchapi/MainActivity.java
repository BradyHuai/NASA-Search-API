package com.example.nasasearchapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.nasasearchapi.adapter.ItemNASAAdapter;
import com.example.nasasearchapi.data.ItemNASA;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    // UI Components
    SearchView searchView;
    ListView contentList;

    // Data
    public static ArrayList<ItemNASA> dummyList = new ArrayList<>();
    ItemNASAAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUIComponents();
    }

    @Override
    protected void onStart() {
        super.onStart();

        importDummyData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initUIComponents(){
        initSearchView();
        initListView();
    }

    private void initSearchView() {
        searchView = findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "onQueryTextSubmit: " + s);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(TAG, "onQueryTextChange: " + s);
                return false;
            }
        });
    }

    private void initListView() {
        contentList = findViewById(R.id.content_list);

        adapter = new ItemNASAAdapter(getApplicationContext(), R.layout.item_cell, dummyList);
        contentList.setAdapter(adapter);

        // set up on item click listener callback
        contentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemClick: position=" + i);
                ItemNASA selected = (ItemNASA) contentList.getItemAtPosition(i);
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("id", selected.getId());
                startActivity(intent);
            }
        });


    }

    private void importDummyData() {
        ItemNASA item1 = new ItemNASA(0, "Moon", "This is an image of Moon", "2010-09");
        ItemNASA item2 = new ItemNASA(1, "Earth", "This is an image of Earth", "2011-09");
        ItemNASA item3 = new ItemNASA(2, "Mars", "This is an image of Mars", "2020-09");
        ItemNASA item4 = new ItemNASA(3, "Mercury", "This is an image of Mercury", "2010-01");
        ItemNASA item5 = new ItemNASA(4, "Venus", "This is an image of Venus", "2000-02");
        ItemNASA item6 = new ItemNASA(5, "Jupiter", "This is an image of Jupiter", "2001-10");
        ItemNASA item7 = new ItemNASA(6, "Saturn", "This is an image of Saturn", "2011-12");

        dummyList.add(item1);
        dummyList.add(item2);
        dummyList.add(item3);
        dummyList.add(item4);
        dummyList.add(item5);
        dummyList.add(item6);
        dummyList.add(item7);

        adapter.notifyDataSetChanged();
    }
}