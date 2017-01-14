package com.codamasters.stockone.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codamasters.stockone.model.Stock;
import com.google.android.gms.appdatasearch.GetRecentContextCall;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilderFactory;

import static android.R.id.list;
import static android.content.ContentValues.TAG;

/**
 * Created by Juan on 07/01/2017.
 */

public class SearchTask extends AsyncTask<String, Void, String> {

    private String url;
    private ArrayList<Stock> stocks;
    private Context context;

    private String URL_BASE = "http://www.allpennystocks.com/aps_us/hot_nasdaq_stocks.asp";

    private OnSearchTaskCompleted listener;

    Stock aux_stock;



    public SearchTask(Context context, String url, OnSearchTaskCompleted listener){
        this.context = context;
        this.url = url;
        this.listener = listener;




        gestorPeticiones.setCola(context);
    }

    @Override
    protected String doInBackground(String... urls) {
        try {

            // TODO
            // CARGAR COSAS


            //Document doc = null;

            /*
            Document doc = (Document) Jsoup.connect(this.URL_BASE).get();
            String result1 = d.body().getElementsByAttributeValue("width", "590").html();
            Document doc2 = Jsoup.parse(result1);
            Element table = doc2.select("table").get(0);
            Elements rows = table.select("tr");
            */


            this.stocks = new ArrayList<>();
            URL page = new URL(this.URL_BASE);


            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            page.openStream()));

            String inputLine;
            //Document doc = null;
            //Document doc = (Document) Jsoup.connect(this.URL_BASE).get();
            while ((inputLine = in.readLine()) != null) {
                //System.out.println(inputLine);
                if (inputLine.contains("<TR bgcolor=\"#EEEEEE\"> \t<TD nowrap width=9% ><a")) {

                    // Log.d("DATA_____: ", String.valueOf(inputLine[inputLine.lastIndexOf("target=\"_blank\" >") + 1]));

                    //Symbolos
                    Stock aux;

                    String separador = "showDetailedQuote&symbol=";
                    String[] parts = inputLine.split(separador);
                    String separador2 = "<TR";
                    String separador3 = "<TD ";
                    String[] parts2;
                    String[] parts3 = new String[0];
                    ArrayList<String[]> result = new ArrayList<String[]>();
                    for (int i = 0; i < parts.length; i++) {
                        aux = new Stock();
                        aux.setSymbol(parts[i].substring(0, 4));
                        parts2 = parts[i].split(separador2);
                        for (int j = 0; j < parts2.length; j++) {
                            parts3 = parts2[j].split(separador3);
                        }
                        result.add(parts3);

                        //stocks.add(aux);
                        Log.d("SUBSTRING_______: ", parts[i].substring(0, 4));
                    }
                    for (int i = 0; i < parts.length; i++) {
                        //PETICIONES VOLLEY
                        String URL_BASE="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22"+parts[i].substring(0, 4)+"%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
                        //String URL_BASE = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20("parts[i].substring(0, 4)+")&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
                        Map<String, String> params = new HashMap<String, String>();

                        /*params.put("username", datosUsuario.getNombre_usuario());
                        params.put("organizacion", datosUsuario.getOrganizacion());
                        params.put("flag", "True");
                        params.put("busqueda", "");
                        */

                        CustomRequest jsObjRequest = new CustomRequest(Request.Method.GET, URL_BASE, params, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    aux_stock=new Stock();
                                    Log.d("Response___________: ", response.getJSONObject("query").getJSONObject("results").getJSONObject("quote").get("Symbol").toString());
                                    aux_stock.setSymbol(response.getJSONObject("query").getJSONObject("results").getJSONObject("quote").get("Symbol").toString());
                                    aux_stock.setDate(response.getJSONObject("query").getJSONObject("results").getJSONObject("quote").get("LastTradeDate").toString());
                                    aux_stock.setOpen(Float.valueOf(response.getJSONObject("query").getJSONObject("results").getJSONObject("quote").get("Open").toString()));
                                    aux_stock.setHigh(Float.valueOf(response.getJSONObject("query").getJSONObject("results").getJSONObject("quote").get("DaysHigh").toString()));

                                    aux_stock.setLow(Float.valueOf(response.getJSONObject("query").getJSONObject("results").getJSONObject("quote").get("DaysLow").toString()));
                                    aux_stock.setClose(Float.valueOf(response.getJSONObject("query").getJSONObject("results").getJSONObject("quote").get("PreviousClose").toString()));
                                    /*aux_stock.setVolume(Integer.valueOf(response.getJSONObject("query").getJSONObject("results").getJSONObject("quote").get("Volume").toString()));
                                    aux_stock.setPercentage(Float.valueOf(response.getJSONObject("query").getJSONObject("results").getJSONObject("quote").get("ChangeinPercent").toString()));
                                    */
                                    stocks.add(aux_stock);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError response) {
                                Log.d("Response___________: ", response.toString());
                            }
                        });
                        gestorPeticiones.setCola(this.context);
                        gestorPeticiones.getCola().add(jsObjRequest);
                    }
                }



                   /* for (int z =0;z<result.size();z++){
                        for (int td=0;td<result.get(z).length;td++)
                        Log.d("result_______: ", result.get(z)[td]);
                    }*/

                   // String aux;
                    //aux=inputLine.substring(inputLine.lastIndexOf("target=\"_blank\" >")+17,inputLine.lastIndexOf("target=\"_blank\" >")+17+4);
                    //Log.d("SUBSTRING_______: ", aux);


                   // Log.d("Line: ", inputLine);

                }


            in.close();


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
