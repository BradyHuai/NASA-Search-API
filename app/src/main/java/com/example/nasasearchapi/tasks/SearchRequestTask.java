package com.example.nasasearchapi.tasks;

import static com.example.nasasearchapi.tools.Constants.API_ROOT;
import static com.example.nasasearchapi.tools.Constants.SEARCH_ENDPOINT;
import static com.example.nasasearchapi.tools.Constants.SEARCH_TYPE;

import android.os.AsyncTask;
import android.util.Log;

import com.example.nasasearchapi.data.ItemNASA;
import com.example.nasasearchapi.eventListener.SearchResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchRequestTask extends AsyncTask<String, Void, String> {

    private static final String TAG = SearchRequestTask.class.getSimpleName();

    private final List<SearchResultListener> listeners = new ArrayList<>();

    public void addListener(SearchResultListener listener) {
        this.listeners.add(listener);
    }

    @Override
    protected String doInBackground(String... strings) {
        String q = strings[0];
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String result = null;

        try {
            URL url = new URL(API_ROOT + SEARCH_ENDPOINT + "?q=" + q + SEARCH_TYPE);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            StringBuilder stringBuilder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            result = stringBuilder.toString();
        } catch (IOException e) {
            Log.d(TAG, "doInBackground: error");
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.d(TAG, "doInBackground: reader close error");
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (s != null) {
            try {
                JSONObject response = new JSONObject(s);
                JSONObject collection = response.getJSONObject("collection");
                JSONArray items = collection.getJSONArray("items");

                for (int i = 0; i < items.length(); i++) {
                    ItemNASA itemNASA = new ItemNASA();

                    JSONObject item = items.getJSONObject(i);
                    JSONArray data = item.optJSONArray("data");

                    if (data != null && data.length() > 0) {
                        JSONObject dataTemp = data.getJSONObject(0);

                        String title = dataTemp.optString("title", "NA");
                        String dateCreated = dataTemp.optString("date_created", "NA");
                        String description = dataTemp.optString("description", "NA");
                        String nasa_id = dataTemp.getString("nasa_id");
                        itemNASA.setTitle(title);
                        itemNASA.setDateCreated(dateCreated);
                        itemNASA.setDescription(description);
                        itemNASA.setNasaID(nasa_id);
                    } else {
                        continue;
                    }

                    JSONArray links = item.optJSONArray("links");
                    if (links != null && links.length() > 0) {
                        JSONObject linkTemp = links.getJSONObject(0);
                        String thumb = linkTemp.getString("href");
                        itemNASA.setThumbLink(thumb);
                    } else {
                        itemNASA.setThumbLink("NA");
                    }
                    for (SearchResultListener listener: listeners) { // Notify listeners
                        listener.onDataAdded("", itemNASA);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
