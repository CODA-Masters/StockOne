package com.codamasters.stockone.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codamasters.stockone.R;
import com.codamasters.stockone.model.Stock;
import com.codamasters.stockone.ui.StockActivity;

import java.util.ArrayList;

/**
 * Created by Juan on 07/01/2017.
 */

public class StockAdapter extends ArrayAdapter<Stock> {

    public StockAdapter(Context context, ArrayList<Stock> stocks){
        super(context, 0, stocks);
    }


    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        // Get the data item for this position
        final Stock stock = getItem(position);

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_stock, parent, false);

        ((TextView) convertView.findViewById(R.id.symbol)).setText(stock.getSymbol());
        ((TextView) convertView.findViewById(R.id.date)).setText(stock.getDate());
        ((TextView) convertView.findViewById(R.id.current)).setText(String.valueOf(stock.getCurrent()));
        ((TextView) convertView.findViewById(R.id.percentage)).setText(stock.getPercentage());

        if(stock.getPercentage().contains("-")){
            ((TextView) convertView.findViewById(R.id.percentage)).setBackground(convertView.getResources().getDrawable(R.drawable.rounded_red_bg));
        }else{
            ((TextView) convertView.findViewById(R.id.percentage)).setBackground(convertView.getResources().getDrawable(R.drawable.rounded_green_bg));
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getContext(), R.transition.animation_in_1,R.transition.animation_in_2).toBundle();
                getContext().startActivity(new Intent(getContext(), StockActivity.class), bndlanimation);
            }
        });

        return convertView;
    }

}
