package com.example.administrator.dbin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.administrator.dbin.Model.Data;
import com.example.administrator.dbin.R;

import java.util.List;

public class DataAdapter extends ArrayAdapter<Data> {

    Context context;
    List<Data> objects;
    int resource;

    public DataAdapter(Context context, int resource, List<Data> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int rowState = objects.get(position).getState();

        View v;
        if (rowState >= 0){
            v = inflater.inflate(R.layout.listview_content_row, null);
            TextView textView_row_desc =(TextView) v.findViewById(R.id.textView_row_desc);
            textView_row_desc.setText(objects.get(position).getDesc());

            TextView textView_row_state =(TextView) v.findViewById(R.id.textView_row_state);
            textView_row_state.setText(objects.get(position).getState() + "%");
        }
        else{
            v = inflater.inflate(R.layout.listview_header_row, null);
            TextView textView_header_floor =(TextView) v.findViewById(R.id.textview_header_floor);
            textView_header_floor.setText(context.getResources().getString(R.string.str_listview_floor_title) + " " + objects.get(position).getId());
        }

        return v;
    }

    static class ViewHolder{
        TextView textView_row_desc;
        TextView textView_row_state;
        TextView textView_header_floor;
    }
}
