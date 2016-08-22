package com.example.cc15.weathereo;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter{
    ArrayList<String> dayanddate;
    ArrayList<String> weathertype;
    ArrayList<String> maxtemp;
    ArrayList<String> mintemp;

    Context mcontext;

    private static LayoutInflater inflater=null;
    public CustomListAdapter(Context context, ArrayList<String> DayandDate, ArrayList<String> WeatherType, ArrayList<String> MaxTemp, ArrayList<String> MinTemp) {
        // TODO Auto-generated constructor stub

        dayanddate = DayandDate;
        weathertype = WeatherType;
        maxtemp = MaxTemp;
        mintemp = MinTemp;
        mcontext=context;

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dayanddate.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView DayandDate;
        TextView WeatherType;
        TextView MaxTemp;
        TextView MinTemp;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.weather_list_item, null);
        holder.DayandDate=(TextView) rowView.findViewById(R.id.text_DaynDate);
        holder.WeatherType=(TextView) rowView.findViewById(R.id.type);
        holder.MaxTemp=(TextView) rowView.findViewById(R.id.max_temp);
        holder.MinTemp=(TextView) rowView.findViewById(R.id.min_temp);
        holder.DayandDate.setText(dayanddate.get(position));
        holder.WeatherType.setText(weathertype.get(position));
        holder.MaxTemp.setText(maxtemp.get(position));
        holder.MinTemp.setText(mintemp.get(position));
        /*rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();
            }
        });*/
        return rowView;
    }

}
