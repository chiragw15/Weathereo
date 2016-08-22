package com.example.cc15.weathereo;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cc15 on 20/8/16.
 */
public class WeatherListFragment extends Fragment {

   public WeatherListFragment(){

    }

    private ArrayAdapter<String> mForecastAdapter;
    private CustomListAdapter WeatherAdapter;
    public ArrayList<String> dayndate = new ArrayList<String>();
    public ArrayList<String> weatherType = new ArrayList<String>();
    public ArrayList<String> maxTemp = new ArrayList<String>();
    public ArrayList<String> minTemp = new ArrayList<String>();
    ListView listview;

    @Override
    public void onStart() {

        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {


        try{
            FetchWeatherTask weatherTask = new FetchWeatherTask();
            weatherTask.execute("801103");
        }catch (Exception e){
            Log.v("TAG","chirag is hot");
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.main_fragment,container,false);
        //List<String> weekForecast = new ArrayList<String>(Arrays.asList(data));

        /*mForecastAdapter =
                new ArrayAdapter<String>(
                        getActivity(),android.R.layout.simple_list_item_1,android.R.id.text1,weekForecast);*/
        listview = (ListView) view.findViewById(R.id.list_main);



        return view;

    }

    public class FetchWeatherTask extends AsyncTask<String, Void, Boolean> {

        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

        private String getReadableDateString(long time) {
            SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
            return shortenedDateFormat.format(time);
        }


        private String formatTemp(double temp) {
            // For presentation, assume the user doesn't care about tenths of a degree.
            long roundedTemp = Math.round(temp);

            String tempStr = "" + roundedTemp;
            return tempStr;
        }

        private Boolean getWeatherDataFromJson(String forecastJsonStr, int numDays)
                throws JSONException {

            final String OWM_LIST = "list";
            final String OWM_WEATHER = "weather";
            final String OWM_TEMPERATURE = "temp";
            final String OWM_MAX = "max";
            final String OWM_MIN = "min";
            final String OWM_DESCRIPTION = "main";

            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);

            Time dayTime = new Time();
            dayTime.setToNow();

            int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);

            dayTime = new Time();

            String[] resultStrs = new String[numDays];
            for (int i = 0; i < weatherArray.length(); i++) {

                String day;
                String description;
                String highAndLow;

                JSONObject dayForecast = weatherArray.getJSONObject(i);

                long dateTime;

                dateTime = dayTime.setJulianDay(julianStartDay + i);
                day = getReadableDateString(dateTime);

                JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
                description = weatherObject.getString(OWM_DESCRIPTION);

                JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
                double high = temperatureObject.getDouble(OWM_MAX);
                double low = temperatureObject.getDouble(OWM_MIN);

                //highAndLow = high + "/" + low ;
                //resultStrs[i] = day + " - " + description + " - " + highAndLow;
                Log.v("TAG","Add(nothing)done");
                Log.v("TAG",day+" chirag");
                //System.out.println(dayndate.get(i) + " ed " + weatherType.get(i) + " nb " + maxTemp.get(i) + " " + minTemp.get(i));
                dayndate.add(day);
                //Log.v("TAG", "Add(day)done");
                weatherType.add(description);
                //Log.v("TAG", "Add(description)done");
                maxTemp.add(formatTemp(high));
                //Log.v("TAG", "Add(high)done");
                minTemp.add(formatTemp(low));
                //Log.v("TAG", "Add(low)done");
                //System.out.println(dayndate.get(i) + " ed " + weatherType.get(i) + " nb " + maxTemp.get(i) + " " + minTemp.get(i));
            }


            /*for (String s : resultStrs) {
                Log.v(LOG_TAG, "Forecast entry: " + s);
           */
            if (dayndate.isEmpty()) {
                return false;
            } else
                return true;
        }

        @Override
        protected Boolean doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }


            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String forecastJsonStr = null;

            String format = "json";
            String units = "metric";
            int numDays = 7;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                final String FORECAST_BASE_URL =
                        "http://api.openweathermap.org/data/2.5/forecast/daily?";
                final String QUERY_PARAM = "q";
                final String FORMAT_PARAM = "mode";
                final String UNITS_PARAM = "units";
                final String DAYS_PARAM = "cnt";
                final String APPID_PARAM = "APPID";

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, params[0])
                        .appendQueryParameter(FORMAT_PARAM, format)
                        .appendQueryParameter(UNITS_PARAM, units)
                        .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                        .appendQueryParameter(APPID_PARAM, "0e8c9776b5316e8cbf21b07e30afccab")
                        .build();

                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();

                Log.v(LOG_TAG, "Forecast string: " + forecastJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getWeatherDataFromJson(forecastJsonStr, numDays);
            } catch (JSONException e) {
                Log.v("TAG","chirag is sexy");
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result) {
                WeatherAdapter = new CustomListAdapter(getActivity(), dayndate, weatherType, maxTemp, minTemp);
                listview.setAdapter(WeatherAdapter);
            }
            else{
                Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_SHORT).show();
            }
        }
    }

}

