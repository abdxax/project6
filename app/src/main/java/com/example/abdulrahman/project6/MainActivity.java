package com.example.abdulrahman.project6;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ArrayList<News> news;
ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        news=new ArrayList<>();
        listView=findViewById(R.id.newsList);
        String url="http://content.guardianapis.com/search?q=debates&api-key=test";
        new NewsJson().execute(url);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Uri webpage = Uri.parse(news.get(i).getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });



    }

    public class NewsJson extends AsyncTask<String , String,String>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
          if (s!=null){
              try {

                  JSONObject object=new JSONObject(s);
                  JSONObject response=object.getJSONObject("response");
                  JSONArray array=response.getJSONArray("results");
                  for (int i=0;i<array.length();i++){
                      JSONObject newsObject=array.getJSONObject(i);
                      news.add(new News(newsObject.getString("webTitle"),newsObject.getString("sectionName"),newsObject.getString("webPublicationDate"),
                              newsObject.getString("webUrl")));
                     // Log.d("ERROR",newsObject.getString("webTitle"));
                  }
                  NewsAdapter newsAdapter=new NewsAdapter(getApplicationContext(),news);
                  listView.setAdapter(newsAdapter);
              } catch (JSONException e) {
                  e.printStackTrace();
              }
          }
          else{
              Toast.makeText(getApplicationContext(),"Json is empty",Toast.LENGTH_LONG).show();
          }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... urls) {
            String Json="";
            try {
                URL url=new URL(urls[0]);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode()==200){
                    InputStream inputStream=httpURLConnection.getInputStream();
                    Json=News(inputStream);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Json;
        }

        public String News(InputStream inputStream){
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String Line;
            String newsJson="";
            try {
                while ((Line=bufferedReader.readLine())!=null){
                    newsJson=Line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newsJson;
        }
    }
}