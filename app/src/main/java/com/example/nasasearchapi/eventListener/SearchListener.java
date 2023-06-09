package com.example.nasasearchapi.eventListener;

import android.util.Log;

import java.util.HashMap;

public class SearchListener implements EventListener{

    private static final String TAG = SearchListener.class.getSimpleName();
    private HashMap<String, String> dataset;

    public SearchListener() {
        this.dataset = new HashMap<>();
    }

    public void onDataChanged(String nasaID, String imageURL) {
        Log.d(TAG, "onDataChanged: nasa id=" + nasaID + ", image url=" + imageURL);
        dataset.put(nasaID, imageURL);
    }

    public void clearDataset() {
        dataset.clear();
    }

    public String getImageAtID(String id) {
        return dataset.get(id);
    }
}
