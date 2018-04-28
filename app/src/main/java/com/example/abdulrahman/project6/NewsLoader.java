package com.example.abdulrahman.project6;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    public NewsLoader(Context context) {
        super(context);
    }

    @Override
    public List<News> loadInBackground() {
        List<News> newsList = new ArrayList<>();
        URL url = null;
        try {
            url = new URL("http://content.guardianapis.com/search?&show-tags=contributor&q=debates&api-key=test");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                InputStream inputStream = httpURLConnection.getInputStream();
                newsList = listNewsJson(inputStream);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newsList;
    }

    public List<News> listNewsJson(InputStream inputStream) throws IOException, JSONException {
        List<News> newsList = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder builder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            builder.append(line);

        }
        JSONObject object = new JSONObject(builder.toString());
        JSONObject response = object.getJSONObject("response");
        JSONArray array = response.getJSONArray("results");
        String name = "";

        for (int i = 0; i < array.length(); i++) {
            JSONObject newsObject = array.getJSONObject(i);
            try {
                JSONArray profile = newsObject.getJSONArray("tags");
                JSONObject nameobject = profile.getJSONObject(0);
                name = nameobject.getString("firstName") + " " + nameobject.getString("lastName");

            } catch (Exception e) {
                e.printStackTrace();
            }
            newsList.add(new News(newsObject.getString("webTitle"), newsObject.getString("sectionName"), newsObject.getString("webPublicationDate"),
                    newsObject.getString("webUrl"), name));
            // Log.d("ERROR",newsObject.getString("webTitle"));
        }

        return newsList;
    }
}
