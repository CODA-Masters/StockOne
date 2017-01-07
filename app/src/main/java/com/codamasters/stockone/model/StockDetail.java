package com.codamasters.stockone.model;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Juan on 07/01/2017.
 */

public class StockDetail {

    private ArrayList<Float> values;
    private ArrayList<News> news;

    public StockDetail(){

    }

    public StockDetail(ArrayList<Float> values, ArrayList<News> news){
        this.values = values;
        this.news = news;
    }


    public ArrayList<Float> getValues() {
        return values;
    }

    public void setValues(ArrayList<Float> values) {
        this.values = values;
    }

    public ArrayList<News> getNews() {
        return news;
    }

    public void setNews(ArrayList<News> news) {
        this.news = news;
    }
}
