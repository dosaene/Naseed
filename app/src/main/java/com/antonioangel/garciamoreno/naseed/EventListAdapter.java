package com.antonioangel.garciamoreno.naseed;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class EventListAdapter extends ArrayAdapter<Event>{

    protected ArrayList<Event> events;
    protected static AppCompatActivity context;
    protected Bitmap b;
    protected String url;
    ImageView imvItem;
    Event event;

    public EventListAdapter(AppCompatActivity context, int design, ArrayList<Event> events){
        super(context, design, events);
        EventListAdapter.context = context;
        this.events = events;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //inflamos el layout con el xml diseñado para las filas yovoy_comercio_item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listitem, parent, false);

        event = events.get(position);//guardamos cada item en un objeto

        //insertamos cada uno de los elementos en la fila
        imvItem = (ImageView) rowView.findViewById(R.id.imvItem);
        TextView txvItemDate = (TextView) rowView.findViewById(R.id.txvItemDate);
        TextView txvItemFullname = (TextView) rowView.findViewById(R.id.txvItemFullname);

        //Edit the elements with data
        txvItemDate.setText(event.getDate());
        txvItemFullname.setText(event.getFullname());
        imvItem.setImageBitmap(event.getBtmp());

        return rowView;

    }
}
