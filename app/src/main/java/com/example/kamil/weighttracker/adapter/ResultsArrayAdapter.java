package com.example.kamil.weighttracker.adapter;

/**
 * Created by Kamil on 11.11.2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kamil.weighttracker.R;
import com.example.kamil.weighttracker.model.EditCalendar;
import com.example.kamil.weighttracker.model.WeightResult;

import java.util.ArrayList;
import java.util.Locale;

public class ResultsArrayAdapter extends ArrayAdapter{
    private class ViewHolder {
        TextView id;
        TextView date;
        TextView weight;
    }

    private Context context;
    private ArrayList<WeightResult> items;

    public ResultsArrayAdapter(Context context, int textViewResourceId, ArrayList objects) {
        super(context,textViewResourceId, objects);
        this.context= context;
        items = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder=null;
        if (convertView == null)
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.resultlistviewelement, null);

            holder = new ViewHolder();
            holder.id = convertView.findViewById(R.id.IDResultTextView);
            holder.weight =  convertView.findViewById(R.id.WeightResultTextView);
            holder.date = convertView.findViewById(R.id.DateResultTextView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        WeightResult result = items.get(position);
        holder.id.setText(String.valueOf(position+1));
        holder.date.setText(EditCalendar.DATE_FORMAT.format(result.getDataLong())); // TODO
        holder.weight.setText(String.format(Locale.UK, "%.1f",((float)result.getWeightInGrams())/1000f) ); // TODO
        return convertView;
    }
}