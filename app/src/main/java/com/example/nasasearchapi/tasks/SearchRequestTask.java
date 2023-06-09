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

    private List<SearchResultListener> listeners = new ArrayList<>();

    public void addListener(SearchResultListener listener) {
        this.listeners.add(listener);
    }

    private List<ItemNASA> dataset = new ArrayList<>();

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
//                    Log.d(TAG, "onPostExecute: guo =================================");
                    JSONObject item = items.getJSONObject(i);
//                    Log.d(TAG, "onPostExecute: guo " + item.toString());
                    JSONArray data = item.getJSONArray("data");

                    ItemNASA itemNASA = new ItemNASA();

                    for (int j = 0; j < data.length(); j++) {
                        JSONObject dataJ = data.getJSONObject(j);

                        String title = dataJ.optString("title", "NA");
                        String dateCreated = dataJ.optString("date_created", "NA");
                        String description = dataJ.optString("description", "NA");
                        String nasa_id = dataJ.getString("nasa_id");
                        itemNASA.setTitle(title);
                        itemNASA.setDateCreated(dateCreated);
                        itemNASA.setDescription(description);
                        itemNASA.setNasaID(nasa_id);
                    }

                    JSONArray links = item.optJSONArray("links");
                    if (links != null && links.length() > 0) {
                        JSONObject temp = links.getJSONObject(0);
                        String thumb = temp.getString("href");
                        Log.d(TAG, "onPostExecute: guo thumb=" + thumb);
                        String render = temp.optString("render", ""); // image
                        itemNASA.setThumbLink(thumb);
//                        new MainActivity.ImageThumbRequest().execute(thumb);

//                        for (SearchResultListener listener: listeners) {
//                            listener.onDataAdded("", null);
//                        }
                    } else {
                        itemNASA.setThumbLink("NA");
                    }
                    dataset.add(itemNASA);
                }

                Log.d(TAG, "onPostExecute: guo list=" + listeners.size());

                for (SearchResultListener listener: listeners) {
                    Log.d(TAG, "onPostExecute: guo on data returned");
                    listener.onDataReturned(dataset);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public List<ItemNASA> getDataset() {
        return dataset;
    }
}
