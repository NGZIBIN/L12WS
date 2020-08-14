package com.example.l12ws;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class IncidentAdapter extends ArrayAdapter<Incident> {
    private ArrayList<Incident> alIncident;
    private Context context;

    public IncidentAdapter(Context context, int resource, ArrayList<Incident> objects) {
        super(context, resource, objects);
        alIncident = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (android.view.LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);

        TextView tvHeader = (TextView) rowView.findViewById(R.id.tvHeader);
        TextView tvDesc = (TextView) rowView.findViewById(R.id.tvDesc);

        Incident incident = alIncident.get(position);

        tvHeader.setText(incident.getType());
        tvDesc.setText(incident.getMessage());

        return rowView;

    }


}
