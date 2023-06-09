package com.example.nasasearchapi.eventListener;

import com.example.nasasearchapi.data.ItemNASA;

import java.util.List;

public interface SearchResultListener {
    void onDataAdded(String id, ItemNASA item);

    void onDataReturned(List<ItemNASA> dataset);
}
