package com.covid_19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AffectedCountries extends AppCompatActivity {
   // public static List<ModelCountry> CountryList;
    EditText editSearch;
   ListView listView;
   SimpleArcLoader simpleArcLoader;
   // api se data list view me show karna h .....
    public  static List<ModelCountry> CountryModelsList=new ArrayList<>();
    ModelCountry modelCountry;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_countries);
        editSearch=findViewById(R.id.edtSearch);
        listView=findViewById(R.id.listView);
        simpleArcLoader=findViewById(R.id.loader);
        //for back button and finish...
        getSupportActionBar().setTitle("Affected Countries");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);//for back icon..
        //call fun..
        fetchData();
        //details for countries data..
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//ye details ke liye h
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(),Details.class).putExtra("position",position));
            }
        });
        //for searching..
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //for referesh..
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                customAdapter.getFilter().filter(s);
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {
        //used to rest API's...
        String url="https://corona.lmao.ninja/v2/countries/";

        simpleArcLoader.start();
        //strat loader..
        StringRequest request=new StringRequest(Request.Method.GET, url,
                 new Response.Listener<String>() {//request to get from url and response..
            @Override
            public void onResponse(String response) {
                //response will be come into json formate..and data ko handle karna h..
                try {
                    //because data different different se aa rha h isliye array use kiya h..
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject= jsonArray.getJSONObject(i);
                        String countryName=jsonObject.getString("country");
                        String cases=jsonObject.getString("cases");
                        String todayCases=jsonObject.getString("todayCases");
                        String deaths=jsonObject.getString("deaths");
                        String todayDeaths=jsonObject.getString("todayDeaths");
                        String recovered=jsonObject.getString("recovered");
                        String active=jsonObject.getString("active");
                        String critical=jsonObject.getString("critical");
                        //for flage datas and create objects...
                        JSONObject jsonObject1=jsonObject.getJSONObject("countryInfo");//ye link me diya h..
                        String flagUrl=jsonObject1.getString("flag");//flag link me diya h...
                        //ModelCountry ke object ke throgh data store..
                        modelCountry=new ModelCountry(flagUrl,countryName,cases,todayCases,deaths,todayDeaths,recovered,active,critical);
                        CountryModelsList.add(modelCountry);
                    }
                    customAdapter=new CustomAdapter(AffectedCountries.this,CountryModelsList);
                    listView.setAdapter(customAdapter);//isse hame countries ki list dikhegi...
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);




                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                //in case of error...........

                Toast.makeText(AffectedCountries.this,error.getMessage(), Toast.LENGTH_SHORT).show();//this show to error if accure..

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);//this would be handle to request in queue..
        requestQueue.add(request);//add in queue..
    }

    }

