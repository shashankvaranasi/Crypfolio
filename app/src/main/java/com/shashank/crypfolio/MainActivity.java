package com.shashank.crypfolio;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView currencyRV;
    private EditText searchEdt;
    private ArrayList<CurrencyModel> currencyModelArrayList;
    private CurrencyRVAdapter currencyRVAdapter;
    private ProgressBar loadingPB;
    public static String IMAGE_URL_FORMAT = "https://s2.coinmarketcap.com/static/img/coins/32x32/%s.png";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchEdt = findViewById(R.id.idSearchCurrency);

        loadingPB = findViewById(R.id.idPBLoading);
        currencyRV = findViewById(R.id.idRVcurrency);
        currencyModelArrayList = new ArrayList<>();

        currencyRVAdapter = new CurrencyRVAdapter(currencyModelArrayList, this);

        currencyRV.setLayoutManager(new LinearLayoutManager(this));

        currencyRV.setAdapter(currencyRVAdapter);

        getData();

        searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String filter) {

        ArrayList<CurrencyModel> filteredlist = new ArrayList<>();
        for (CurrencyModel item : currencyModelArrayList) {
            if (item.getName().toLowerCase().contains(filter.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No currency found..", Toast.LENGTH_SHORT).show();
        } else {
            currencyRVAdapter.filterList(filteredlist);
        }
    }

    private void getData() {
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            loadingPB.setVisibility(View.GONE);
            try {
                JSONArray dataArray = response.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataObj = dataArray.getJSONObject(i);
                    String symbol = dataObj.getString("symbol");
                    String rank = dataObj.getString("cmc_rank");
                    String name = dataObj.getString("name");
                    JSONObject quote = dataObj.getJSONObject("quote");
                    JSONObject USD = quote.getJSONObject("USD");
                    double price = USD.getDouble("price");
                    String id=dataObj.getString("id");

                    currencyModelArrayList.add(new CurrencyModel(name, symbol, price, rank,id));
                }
                currencyRVAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Something went wrong.Please try again later", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Toast.makeText(MainActivity.this, "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("X-CMC_PRO_API_KEY", "32be019a-97cb-4940-80a2-7034c6c1ed7b");
                return headers;
            }
        };
        queue.add(jsonObjectRequest);


    }

}