package com.yjm.passengerapp.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.yjm.passengerapp.MapsActivity;
import com.yjm.passengerapp.R;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ScheduleAdapter extends BaseAdapter {

    private  static LayoutInflater inflater = null;
    Context context;
    ArrayList<String[]> data;

    public ScheduleAdapter(Context context, ArrayList<String[]> data)
    {
        this.context = context;
        this.data = data;

    }
    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.listview_cell, parent,false);

        TextView to_text = (TextView) vi.findViewById(R.id.to_text);
        TextView from_text = (TextView) vi.findViewById(R.id.from_text);
        TextView date_text = (TextView) vi.findViewById(R.id.date_text);
        TextView day_text = (TextView) vi.findViewById(R.id.day_text);

        try{
            String[] data_per_position = (String[])data.get(position);

            to_text.setText(data_per_position[0]);
            from_text.setText(data_per_position[1]);
            date_text.setText(data_per_position[2]);
            day_text.setText(data_per_position[2]);
        }
        catch (Exception e)
        {
            Toast.makeText(context, "No data from server!", Toast.LENGTH_SHORT).show();
        }



//        to_text.setText((String) Array.get(data_per_position, 0));
//        from_text.setText((String) Array.get(data_per_position, 1));
//        date_text.setText((String) Array.get(data_per_position, 2));
//        day_text.setText((String) Array.get(data_per_position, 3));



        return vi;
    }
}
