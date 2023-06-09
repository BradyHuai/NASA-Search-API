package com.example.nasasearchapi.tasks;

import static com.example.nasasearchapi.tools.Constants.API_ROOT;
import static com.example.nasasearchapi.tools.Constants.ASSET_ENDPOINT;

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

public class MediaAssetTask extends AsyncTask<String, Void, String> {

    private static final String TAG = MediaAssetTask.class.getSimpleName();

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
