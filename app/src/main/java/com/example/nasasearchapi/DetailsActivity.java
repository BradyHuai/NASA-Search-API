package com.example.nasasearchapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nasasearchapi.data.ItemNASA;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();

    ItemNASA currentItem;

    ImageView image;
    TextView title;
    TextView description;
    TextView dateCreated;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initUIComponents();

        Intent intent = getIntent();
        int itemId = intent.getIntExtra("id", 0);
        Log.d(TAG, "onCreate: item id=" + itemId);
        currentItem = MainActivity.dummyList.get(itemId);

        updateData();
    }

    private void initUIComponents() {
        title = findViewById(R.id.details_title);
        description = findViewById(R.id.details_description);
        dateCreated = findViewById(R.id.details_date_created);
        image = findViewById(R.id.details_Image);
    }

    private void updateData() {
        title.setText(currentItem.getTitle());
        description.setText(currentItem.getDescription());
        dateCreated.setText(currentItem.getDateCreated());
    }
}