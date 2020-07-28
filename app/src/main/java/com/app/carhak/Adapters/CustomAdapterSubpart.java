package com.app.carhak.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.carhak.Activities.FilterActivity;
import com.app.carhak.Model.AllBrandData;
import com.app.carhak.Model.SubPartData;
import com.app.carhak.R;

import java.util.List;

public class CustomAdapterSubpart extends ArrayAdapter<SubPartData> {
    LayoutInflater flater;
    public CustomAdapterSubpart(Context context, int i, int i1, List<SubPartData> list) {
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

        SubPartData rowItem = getItem(position);


        CustomAdapterSubpart.viewHolder holder ;
        View rowview = convertView;
        if (rowview==null) {

            holder = new CustomAdapterSubpart.viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.listitems_layout, null, false);

            holder.txtTitle = (TextView) rowview.findViewById(R.id.title);


            rowview.setTag(holder);
        }else{

            holder = (CustomAdapterSubpart.viewHolder) rowview.getTag();
        }



        if (rowItem!=null) {

                holder.txtTitle.setText(rowItem.getPartSubCatName());


        }
        return rowview;
    }

    class viewHolder{
        TextView txtTitle;


    }
}
