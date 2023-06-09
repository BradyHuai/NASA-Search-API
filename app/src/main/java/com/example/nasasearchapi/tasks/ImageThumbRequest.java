package com.example.nasasearchapi.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageThumbRequest extends AsyncTask<String, Void, Bitmap> {

    private static final String TAG = ImageThumbRequest.class.getSimpleName();

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