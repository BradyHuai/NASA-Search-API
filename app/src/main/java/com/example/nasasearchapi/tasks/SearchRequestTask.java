package com.example.nasasearchapi.tasks;

import static com.example.nasasearchapi.tools.Constants.API_ROOT;
import static com.example.nasasearchapi.tools.Constants.SEARCH_ENDPOINT;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SearchRequestTask extends AsyncTask<String, Void, String> {

    private static final String TAG = SearchRequestTask.class.getSimpleName();

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
//                        new MainActivity.ImageThumbRequest().execute(thumb);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
