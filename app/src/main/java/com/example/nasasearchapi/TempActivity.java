package com.example.nasasearchapi;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;

public class TempActivity extends AppCompatActivity {

    private static final String TAG = TempActivity.class.getSimpleName();

    private static final String API_ROOT = "https://images-api.nasa.gov";
    private static final String SEARCH_ENDPOINT = "/search";
    private static final String SEARCH_ENDPOINT2 = "/asset";

    private ImageView temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        temp = findViewById(R.id.temptemp);

//        new ImageThumbRequest().execute("https://images-assets.nasa.gov/image/EC97-44347-15/EC97-44347-15~orig.jpg");
        new SearchRequestTask().execute("apo");
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
                            new ImageThumbRequest().execute(thumb);
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
                URL url = new URL(API_ROOT + SEARCH_ENDPOINT2 + "/" + assetsUrl);
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
                temp.setImageBitmap(bitmap);
            }
        }
    }
}