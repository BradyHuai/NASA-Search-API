package com.example.nasasearchapi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nasasearchapi.R;
import com.example.nasasearchapi.data.ItemNASA;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemNASAAdapter extends ArrayAdapter<ItemNASA> {

    public ItemNASAAdapter(@NonNull Context context, int resource, List<ItemNASA> dataList) {
        super(context, resource, dataList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ItemNASA itemNASA = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cell, parent, false);
        }

        TextView itemTitle = convertView.findViewById(R.id.item_title);
        ImageView itemImage = convertView.findViewById(R.id.item_image);

        // Update content cell information
        itemTitle.setText(itemNASA.getTitle());

        Picasso.get().load(itemNASA.getThumbLink()).into(itemImage);

        return convertView;
    }
}
