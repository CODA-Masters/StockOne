package com.codamasters.stockone.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.codamasters.stockone.model.Stock;

import java.util.ArrayList;

/**
 * Created by Juan on 07/01/2017.
 */

public class SearchTask extends AsyncTask<String, Void, String> {

    private String url;
    private ArrayList<Stock> stocks;
    private Context context;

    private OnSearchTaskCompleted listener;

    public SearchTask(Context context, String url, OnSearchTaskCompleted listener){
        this.context = context;
        this.url = url;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... urls) {
        try {

            // TODO
            // CARGAR COSAS

            this.stocks = new ArrayList<>();


            Stock aux;

            for(int i=1; i<=30; i++){
                aux = new Stock();
                aux.setSymbol("Test " + String.valueOf(i));
                stocks.add(aux);
            }


            return "Ok";
        } catch (Exception e) {
            return "error";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        listener.onSearchTaskCompleted(stocks);
    }

}
