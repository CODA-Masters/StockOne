package com.codamasters.stockone.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.codamasters.stockone.R;
import com.codamasters.stockone.model.Stock;

/**
 * Created by Juan on 14/01/2017.
 */

public class StockHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    private Stock stock;
    private Context context;

    private final TextView stockSymbol;
    private final TextView stockDate;
    private final TextView stockCurrent;
    private final TextView stockPercentage;

    public StockHolder(Context context, View itemView) {
        super(itemView);

        this.context = context;

        this.stockSymbol = (TextView) itemView.findViewById(R.id.symbol);
        this.stockDate = (TextView) itemView.findViewById(R.id.date);
        this.stockCurrent = (TextView) itemView.findViewById(R.id.current);
        this.stockPercentage = (TextView) itemView.findViewById(R.id.percentage);

        itemView.setOnClickListener(this);

    }

    public void bindStock(Stock stock){

        this.stock = stock;

        this.stockSymbol.setText(stock.getSymbol());
        this.stockDate.setText(stock.getDate());
        this.stockCurrent.setText(String.valueOf(stock.getCurrent()));
        this.stockPercentage.setText(stock.getPercentage());

        if(stock.getPercentage().contains("+")){
            this.stockPercentage.setBackground(this.context.getResources().getDrawable(R.drawable.rounded_green_bg));
        }else{
            this.stockPercentage.setBackground(this.context.getResources().getDrawable(R.drawable.rounded_red_bg));
        }

    }


    @Override
    public void onClick(View view) {

    }
}
