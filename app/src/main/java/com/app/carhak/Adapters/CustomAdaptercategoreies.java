package com.app.carhak.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.carhak.Activities.FilterActivity;
import com.app.carhak.Model.AllBrandData;
import com.app.carhak.Model.AllCategoriesData;
import com.app.carhak.R;

import java.util.List;

public class CustomAdaptercategoreies extends ArrayAdapter<AllCategoriesData> {
    LayoutInflater flater;
    public CustomAdaptercategoreies(Context context, int i, int i1, List<AllCategoriesData> list) {
        super(context,i,i1, list);
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

        AllCategoriesData rowItem = getItem(position);

        CustomAdaptercategoreies.viewHolder holder ;
        View rowview = convertView;
        if (rowview==null) {

            holder = new CustomAdaptercategoreies.viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.listitems_layout, null, false);

            holder.txtTitle = (TextView) rowview.findViewById(R.id.title);

            rowview.setTag(holder);
        }else{
            holder = (CustomAdaptercategoreies.viewHolder) rowview.getTag();
        }

        if (rowItem!=null) {
            holder.txtTitle.setText(rowItem.getPartName());
        }
        return rowview;
    }

    class viewHolder{
        TextView txtTitle;


    }
}
