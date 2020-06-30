package com.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView tVCases,tVRecovered,tVCritical,tVActive,tVToday,tVDeaths,tVTDeaths,tVACountries;
    ScrollView scrollStats;
    PieChart pieChart;
    SimpleArcLoader simpleArcLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tVCases = findViewById(R.id.tVCases);
        tVRecovered = findViewById(R.id.tVRecovered);
        tVCritical = findViewById(R.id.tVCritical);
        tVActive = findViewById(R.id.tVActive);
        tVToday = findViewById(R.id.tVToday);
        tVDeaths = findViewById(R.id.tVDeaths);
        tVTDeaths = findViewById(R.id.tVTDeaths);
        tVACountries = findViewById(R.id.tVACountries);
        scrollStats = findViewById(R.id.scrollStats);
        pieChart = findViewById(R.id.piechart);
        simpleArcLoader = findViewById(R.id.simpleArcLoader);

        fetchData();
    }

    private void fetchData() {
        //used to rest API's...
        String url="https://corona.lmao.ninja/v2/all";
        simpleArcLoader.start();//strat loader..
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {//request to get from url and response..
            @Override
            public void onResponse(String response) {
                //response will be come into json formate..
                try {
                    JSONObject jsonObject=new JSONObject(response.toString());
                    tVCases.setText(jsonObject.getString("cases"));
                    tVRecovered.setText(jsonObject.getString("recovered"));
                    tVCritical.setText(jsonObject.getString("critical"));
                    tVActive.setText(jsonObject.getString("active"));
                    tVToday.setText(jsonObject.getString("todayCases"));
                    tVDeaths.setText(jsonObject.getString("deaths"));
                    tVTDeaths.setText(jsonObject.getString("todayDeaths"));
                    tVACountries.setText(jsonObject.getString("affectedCountries"));
                    //show data in pie chart throw colors..
                    pieChart.addPieSlice(new PieModel("cases",Integer.parseInt(tVCases.getText().toString())
                            , Color.parseColor("#ffa726")));//Recovered....................
                    pieChart.addPieSlice(new PieModel("recovered",Integer.parseInt(tVRecovered.getText().toString())
                            , Color.parseColor("#66bb6a")));//Deaths............................
                    pieChart.addPieSlice(new PieModel("deaths",Integer.parseInt(tVDeaths.getText().toString())
                            , Color.parseColor("#ee5350")));//Active.....................
                    pieChart.addPieSlice(new PieModel("active",Integer.parseInt(tVActive.getText().toString())
                            , Color.parseColor("#29b6f6")));
                    pieChart.startAnimation();//piechart animate hoga........
                    simpleArcLoader.stop();//arcloader stop ho jayga.......
                    simpleArcLoader.setVisibility(View.GONE);//arcloader go....
                    scrollStats.setVisibility(View.VISIBLE);//textView dikhega...................
                } catch (JSONException e) {
                    e.printStackTrace();
                    // in case of false....................
                    simpleArcLoader.stop();//arcloader stop ho jayga.......
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollStats.setVisibility(View.VISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //in case of error...........
                simpleArcLoader.stop();//arcloader stop ho jayga.......
                simpleArcLoader.setVisibility(View.GONE);
                scrollStats.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();//this show to error if accure..

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);//this would be handle to request in queue..
        requestQueue.add(request);//add in queue..
    }

    public void GoButton(View view){
      startActivity(new Intent(getApplicationContext(),AffectedCountries.class));
    }

    public void IND_STATUS(View view) {

      Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.covid19india.org"));
       startActivity(i);

    }
}
