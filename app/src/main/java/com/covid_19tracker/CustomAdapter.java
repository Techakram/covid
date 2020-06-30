package com.covid_19tracker;

import android.content.Context;
import android.gesture.GestureLibraries;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

//we are used ModelCountry template for it......
public class CustomAdapter extends ArrayAdapter<ModelCountry> {
    private Context context;
    private List<ModelCountry> CountryModelsList;
    //for search..
    private List<ModelCountry> CountryModelsListFiltered;
    //we make its constructor......
    public CustomAdapter( Context context, List<ModelCountry> CountryModelsList) {
        super(context,R.layout.list_custom_item,CountryModelsList);
        //isme hame saperate layout design karna hota h...,resourse ke place per..
        this.context=context;
        this.CountryModelsList=CountryModelsList;
        this.CountryModelsListFiltered=CountryModelsList;
    }
    // getView method ....


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //jo data ayga ose is per handle karna h...
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_item,null,true);
        TextView tVCountryName=view.findViewById(R.id.tVCountryName);
        ImageView imageView=view.findViewById(R.id.imageFlag);
        //set karna h data ko...
        tVCountryName.setText(CountryModelsListFiltered.get(position).getCountry());
        //image ko glide library automatic set ker deti h...
        Glide.with(context).load(CountryModelsListFiltered.get(position).getFlag()).into(imageView);
        return view;
    }
    //search..

    @Override
    public int getCount() {
        return CountryModelsListFiltered.size();
    }

    @Override
    public ModelCountry getItem(int position) {
        return CountryModelsListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        Filter filter=new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults=new FilterResults();
                if(constraint==null||constraint.length() ==0)
                {
                  filterResults.count=CountryModelsList.size();
                  filterResults.values=CountryModelsList;
                }
                else
                    {
                    List<ModelCountry> resultsModel=new ArrayList<>();
                    String searchStr=constraint.toString().toLowerCase();
                    for (ModelCountry itemsModel:CountryModelsList){
                      if(itemsModel.getCountry().toLowerCase().contains(searchStr)){
                          resultsModel.add(itemsModel);
                      }
                      filterResults.count=resultsModel.size();
                      filterResults.values=resultsModel;
                    }

                    }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
              CountryModelsListFiltered=(List<ModelCountry>) results.values;
              AffectedCountries.CountryModelsList=(List<ModelCountry>) results.values;
            //  notifyDataSetChanged();
            }
        };
        return filter;
    }
}

