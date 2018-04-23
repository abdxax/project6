package com.example.abdulrahman.project6;

/**
 * Created by Abdulrahman on 20/04/18.
 */

public class News {
    private String title;
    private String Section;
    private String date;
    private String url;
    private String name;


    public News(String title, String section, String date, String url, String naem) {
        this.title = title;
        Section = section;
        this.date = date;
        this.url = url;
        this.name = naem;
    }

    public String getTitle() {
        return title;
    }

    public String getSection() {
        return Section;
    }


    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }
}
