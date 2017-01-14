package com.codamasters.stockone.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.codamasters.stockone.R;
import com.codamasters.stockone.adapter.StockAdapter;
import com.codamasters.stockone.adapter.StockRecAdapter;
import com.codamasters.stockone.model.Stock;
import com.codamasters.stockone.tasks.OnSearchTaskCompleted;
import com.codamasters.stockone.tasks.SearchTask;

import net.mskurt.neveremptylistviewlibrary.NeverEmptyListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnSearchTaskCompleted {


    private SwipeRefreshLayout swipeContainer;
    private FloatingActionButton searchFab;
    private NeverEmptyListView neverEmptyListView;
    private NestedScrollView nestedScrollView;
    private String searchError;

    private StockRecAdapter stockRecAdapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
        initListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Inicializamos la vista
     */

    private void initView(){
        searchFab = (FloatingActionButton) findViewById(R.id.searchFab);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

//        neverEmptyListView = (NeverEmptyListView) findViewById(R.id.listview);

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
//        nestedScrollView.setNestedScrollingEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.listview);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        stockRecAdapter = new StockRecAdapter(this, R.layout.item_stock, new ArrayList<Stock>());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(stockRecAdapter);



    }


    /**
     * Inicializamos los listeners
     */

    private void initListeners() {

        // Mostramos las opciones de búsqueda
        searchFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchOptions();
            }
        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doSearch();
            }
        });

        ArrayList<Stock> stocks = new ArrayList<>();
        StockAdapter adapter = new StockAdapter(this, stocks);
       // neverEmptyListView.setAdapter(adapter);

       /* neverEmptyListView.setHolderClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchOptions();
            }
        });*/
    }




    /**
     *  Método que muestra las opciones de búsqueda
     */

    private void showSearchOptions(){
        boolean wrapInScrollView = true;
        new MaterialDialog.Builder(this)
                .title("Search")
                .titleGravity(GravityEnum.CENTER)
                .customView(R.layout.search_view, wrapInScrollView)
                .positiveText("Search")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // TODO
                        // Guardamos las opciones de búsqueda

                        // Y realizamos la búsqueda
                        doSearch();
                    }
                })
                .show();
    }

    /** TODO
     *  FUNCIÓN PARA REALIZAR LA BÚSQUEDA
     *  COGE LOS PARÁMETROS ESCODIGOS EN LA VENTANA DE BÚSQUEDA
     *
     *  Durante el proceso de búsqueda se muestra
     */

    private void doSearch() {
        swipeContainer.setRefreshing(true);
        new SearchTask(this, "url", this).execute();
    }


    @Override
    public void onSearchTaskCompleted(ArrayList<Stock> stocks) {
        swipeContainer.setRefreshing(false);

        // Guardamos el array y lo cargamos en la lista

        //StockAdapter adapter = new StockAdapter(this, stocks);
        //neverEmptyListView.setAdapter(adapter);

        stockRecAdapter = new StockRecAdapter(this, R.layout.item_stock, stocks);


        recyclerView.setHasFixedSize(true);


        recyclerView.setAdapter(stockRecAdapter);


    }
}
