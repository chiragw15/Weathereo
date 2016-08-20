package com.example.cc15.weathereo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cc15 on 20/8/16.
 */
public class WeatherListFragment extends Fragment {

   public WeatherListFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String[] data = {
                "Mon 6/23 - Sunny - 31/17",
                "Tue 6/24 - Foggy - 21/8",
                "Wed 6/25 - Cloudy - 22/17",
                "Thurs 6/26 - Rainy - 18/11",
                "Fri 6/27 - Foggy - 21/10",
                "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
                "Sun 6/29 - Sunny - 20/7"
        };
        View view = inflater.inflate(R.layout.main_fragment,container,false);
        List<String> weekForecast = new ArrayList<String>(Arrays.asList(data));

        ArrayAdapter mForecastAdapter =
                new ArrayAdapter<String>(
                        getActivity(),android.R.layout.simple_list_item_1,android.R.id.text1,weekForecast);
        ListView listview = (ListView) view.findViewById(R.id.list_main);

        listview.setAdapter(mForecastAdapter);
        return view;

    }
}
