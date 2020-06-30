 package com.covid_19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

 public class Details extends AppCompatActivity {
     private int positionCountry;
     TextView tVCountry,tVCases,tVRecovered,tVCritical,tVActive,tVTodayCases,tVDeaths,tVTodayDeaths;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent=getIntent();
        positionCountry=intent.getIntExtra("position",0);
        getSupportActionBar().setTitle("Details of "+AffectedCountries.CountryModelsList.get(positionCountry).getCountry());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tVCountry=findViewById(R.id.tVCountry);
        tVCases=findViewById(R.id.tVCases);
        tVRecovered=findViewById(R.id.tVRecovered);
        tVCritical=findViewById(R.id.tVCritical);
        tVActive=findViewById(R.id.tVActive);
        tVTodayCases=findViewById(R.id.tVTodayCases);
        tVDeaths=findViewById(R.id.tVTotalDeaths);
        tVTodayDeaths=findViewById(R.id.tVTodayDeaths);
        tVCountry.setText(AffectedCountries.CountryModelsList.get(positionCountry).getCountry());
        tVCases.setText(AffectedCountries.CountryModelsList.get(positionCountry).getCases());
        tVRecovered.setText(AffectedCountries.CountryModelsList.get(positionCountry).getRecovered());
        tVCritical.setText(AffectedCountries.CountryModelsList.get(positionCountry).getCritical());
        tVActive.setText(AffectedCountries.CountryModelsList.get(positionCountry).getActive());
        tVTodayCases.setText(AffectedCountries.CountryModelsList.get(positionCountry).getTodayCases());
        tVDeaths.setText(AffectedCountries.CountryModelsList.get(positionCountry).getDeaths());
        tVTodayDeaths.setText(AffectedCountries.CountryModelsList.get(positionCountry).getTodayDeaths());
    }
     @Override
     public boolean onOptionsItemSelected(@NonNull MenuItem item) {//back button ke liye
         if (item.getItemId()==android.R.id.home)
             finish();
         return super.onOptionsItemSelected(item);
     }

}
