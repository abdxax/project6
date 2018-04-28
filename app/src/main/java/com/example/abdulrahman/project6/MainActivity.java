package com.example.abdulrahman.project6;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<List<News>> {
    ArrayList<News> news;
    ListView listView;
    NewsAdapter adapter;
    List<News> newsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        news = new ArrayList<>();
        listView = findViewById(R.id.newsList);
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            //String url = "http://content.guardianapis.com/search?&show-tags=contributor&q=debates&api-key=test";
            // new NewsJson().execute(url);
            //getSupportLoaderManager().initLoader(0,null,this);
            getSupportLoaderManager().initLoader(0, null, this).forceLoad();
        } else {
            Intent showmsg = new Intent(MainActivity.this, Main2Activity.class);
            showmsg.putExtra("msg", "No network is available  ");
            startActivity(showmsg);
            finish();
        }
        adapter = new NewsAdapter(this, newsList);
        listView.setAdapter(adapter);
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

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        if (data.isEmpty()) {
            Intent showmsg = new Intent(MainActivity.this, Main2Activity.class);
            showmsg.putExtra("msg", "No data display");
            startActivity(showmsg);
            finish();
        } else {
            newsList = data;
            adapter = new NewsAdapter(this, newsList);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

    }
}
