package com.example.nasasearchapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.nasasearchapi.adapter.ItemNASAAdapter;
import com.example.nasasearchapi.data.ItemNASA;
import com.example.nasasearchapi.eventListener.SearchResultListener;
import com.example.nasasearchapi.tasks.SearchRequestTask;
import com.example.nasasearchapi.tools.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchResultListener{

    public static final String TAG = MainActivity.class.getSimpleName();

    // UI Components
    SearchView searchView;
    ListView contentList;

    // Data
    private final ArrayList<ItemNASA> dataset = new ArrayList<>();
    ItemNASAAdapter adapter;

    SearchRequestTask searchTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUIComponents();
    }

    @Override
    protected void onStart() {
        super.onStart();
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

    /**
     * Set up Search view in MainActivity for entering search query
     */
    private void initSearchView() {
        searchView = findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "onQueryTextSubmit: " + s);
                clearDataset();
                beginTextSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(TAG, "onQueryTextChange: " + s);
                return false;
            }
        });

        searchView.setEnabled(true);
    }

    /**
     * Set up list view in MainActivity which list out the search results
     */
    private void initListView() {
        contentList = findViewById(R.id.content_list);

        adapter = new ItemNASAAdapter(getApplicationContext(), R.layout.item_cell, dataset);
        contentList.setAdapter(adapter);

        // set up on item click listener callback
        contentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startDetailsActivity(i);
            }
        });
    }

    /**
     * Starts DetailsActivity
     * @param position position of the selected item in the list view
     */
    private void startDetailsActivity(int position) {
        ItemNASA selected = (ItemNASA) contentList.getItemAtPosition(position);
        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        intent.putExtra(Constants.INTENT_SELECTED, selected);
        startActivity(intent);
    }

    /**
     * Perform search task on entered string
     * @param s query
     */
    private void beginTextSearch(String s) {
        if (searchTask != null && searchTask.getStatus() == AsyncTask.Status.RUNNING) {
            searchTask.cancel(true);
        }
        searchTask = new SearchRequestTask();
        searchTask.addListener(this);
        searchTask.execute(s);
    }

    private void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDataAdded(String id, ItemNASA item) {
        dataset.add(item);
        notifyDataSetChanged();
    }

    @Override
    public void onDataReturned(List<ItemNASA> dataset) {}

    public void clearDataset() {
        dataset.clear();
        notifyDataSetChanged();
    }
}