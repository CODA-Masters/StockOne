package com.codamasters.stockone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codamasters.stockone.model.Stock;

import java.util.List;

/**
 * Created by Juan on 14/01/2017.
 */

public class StockRecAdapter extends RecyclerView.Adapter<StockHolder> {

    private final List<Stock> stocks;
    private Context context;
    private int itemResource;


    public StockRecAdapter(Context context, int itemResource, List<Stock> stocks) {

        // 1. Initialize our adapter
        this.stocks = stocks;
        this.context = context;
        this.itemResource = itemResource;
    }

    @Override
    public StockHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 3. Inflate the view and return the new ViewHolder
        View view = LayoutInflater.from(parent.getContext())
                .inflate(this.itemResource, parent, false);
        return new StockHolder(this.context, view);
    }

    @Override
    public void onBindViewHolder(StockHolder holder, int position) {
        Stock stock = this.stocks.get(position);
        holder.bindStock(stock);
    }

    @Override
    public int getItemCount() {
        return this.stocks.size();
    }
}
