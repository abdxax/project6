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
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<List<News>> {
    ListView listView;
    TextView msg;
    NewsAdapter adapter;
    List<News> newsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.newsList);
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        msg = findViewById(R.id.msg);
        if (info != null && info.isConnected()) {
            getSupportLoaderManager().initLoader(0, null, this).forceLoad();

        } else {

            msg.setText(R.string.noIntremt);
        }
        adapter = new NewsAdapter(this, newsList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Uri webpage = Uri.parse(newsList.get(i).getUrl());
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
            msg.setText(R.string.noData);
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
