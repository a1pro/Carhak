package com.app.carhak.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.carhak.Model.AllBrandData;
import com.app.carhak.R;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<AllBrandData> {

    LayoutInflater flater;

    public CustomAdapter(Context context, int resouceId, int textviewId, List<AllBrandData> list){

        super(context,resouceId,textviewId, list);
//        flater = context.getLayoutInflater();
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return rowview(convertView,position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView,position);
    }

    private View rowview(View convertView , int position){

        AllBrandData rowItem = getItem(position);

        viewHolder holder ;
        View rowview = convertView;
        if (rowview==null) {

            holder = new viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.listitems_layout, null, false);

            holder.txtTitle = (TextView) rowview.findViewById(R.id.title);

            rowview.setTag(holder);
        }else{
            holder = (viewHolder) rowview.getTag();
        }

     if (rowItem!=null) {
         holder.txtTitle.setText(rowItem.getBrandName());
     }
        return rowview;
    }

    class viewHolder{
        TextView txtTitle;


    }
}