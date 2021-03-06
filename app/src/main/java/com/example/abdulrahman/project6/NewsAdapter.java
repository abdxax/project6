package com.example.abdulrahman.project6;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdulrahman on 20/04/18.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    List<News> news;

    public NewsAdapter(@NonNull Context context, List<News> news) {
        super(context, 0, news);
        this.news = news;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.list_news, parent, false);
        TextView title = view.findViewById(R.id.title);
        TextView section = view.findViewById(R.id.section);
        TextView date = view.findViewById(R.id.date);
        TextView name = view.findViewById(R.id.name);
        title.setText(news.get(position).getTitle());
        section.setText(news.get(position).getSection());
        date.setText(news.get(position).getDate());
        name.setText(news.get(position).getName());
        return view;
    }
}
