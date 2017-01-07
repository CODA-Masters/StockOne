package com.codamasters.stockone.tasks;

import com.codamasters.stockone.model.Stock;

import java.util.ArrayList;

/**
 * Created by Juan on 07/01/2017.
 */

public interface OnSearchTaskCompleted {

    void onSearchTaskCompleted(ArrayList<Stock> stocks);

}
