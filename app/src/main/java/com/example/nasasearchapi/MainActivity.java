package com.example.nasasearchapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.nasasearchapi.adapter.ItemNASAAdapter;
import com.example.nasasearchapi.data.ItemNASA;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    public static final String API_ROOT = "https://images-api.nasa.gov";
    public static final String SEARCH_ENDPOINT = "/search";
    public static final String ASSET_ENDPOINT = "/asset";

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

    class SearchRequestTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String q = strings[0];
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String result = null;

            try {
                URL url = new URL(API_ROOT + SEARCH_ENDPOINT + "?q=" + q);
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
                        Log.d(TAG, "onPostExecute: guo =================================");
                        JSONObject item = items.getJSONObject(i);
                        Log.d(TAG, "onPostExecute: guo " + item.toString());
                        JSONArray data = item.getJSONArray("data");

                        for (int j = 0; j < data.length(); j++) {
                            JSONObject dataJ = data.getJSONObject(j);

                            String title = dataJ.getString("title");
                            String dateCreated = dataJ.getString("date_created");
                            String description = dataJ.getString("description");

                            String nasa_id = dataJ.getString("nasa_id");
//                            new MediaAssetRequest().execute(nasa_id);

                            Log.d(TAG, "onPostExecute: guo title=" + title + ", nasa_id=" + nasa_id);
                        }

                        JSONArray links = item.getJSONArray("links");
                        if (links.length() > 0) {
                            JSONObject temp = links.getJSONObject(0);
                            String thumb = temp.getString("href");
                            Log.d(TAG, "onPostExecute: guo thumb=" + thumb);
                            String render = temp.getString("render"); // image
                            new MainActivity.ImageThumbRequest().execute(thumb);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    static class MediaAssetRequest extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String assetsUrl = strings[0];
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String result = null;

            try {
                URL url = new URL(API_ROOT + ASSET_ENDPOINT + "/" + assetsUrl);
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
                        JSONObject item = items.getJSONObject(i);
                        String href = item.getString("href");
                        Log.d(TAG, "onPostExecute: guo href=" + href);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    class ImageThumbRequest extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            String assetsUrl = strings[0];
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            Bitmap bitmap = null;

            try {
                URL url = new URL(assetsUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

            } catch (IOException e) {
                Log.d(TAG, "doInBackground: error");
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        Log.d(TAG, "doInBackground: reader close error");
                        e.printStackTrace();
                    }
                }
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if (bitmap != null) {
                // TODO update image
            }
        }
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