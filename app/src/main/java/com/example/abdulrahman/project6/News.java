package com.example.abdulrahman.project6;

/**
 * Created by Abdulrahman on 20/04/18.
 */

public class News {
    private String title;
    private String Section;
    private String date;
    String url;

    public News(String title, String section, String date,String url) {
        this.title = title;
        Section = section;
        this.date = date;
        this.url=url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String section) {
        Section = section;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
