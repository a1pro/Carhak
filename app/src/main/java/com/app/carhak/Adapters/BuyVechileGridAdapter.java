package com.app.carhak.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.carhak.Activities.DetailsActivity;
import com.app.carhak.Model.NewCarsSaleData;
import com.app.carhak.R;
import com.app.carhak.Retrofit.ApiUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BuyVechileGridAdapter extends RecyclerView.Adapter<BuyVechileGridAdapter.ViewHolder> {
    Context context;
    List<NewCarsSaleData> list;
    public BuyVechileGridAdapter(Context context, List<NewCarsSaleData> list) {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buyvechile_grid_items, parent, false);
        return new BuyVechileGridAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Drawable drawable = holder.rating.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#ffff8800"), PorterDuff.Mode.SRC_ATOP);

        holder.rating.setRating(list.get(position).getAverage_rating());

        if(list.get(position).getTitle()!=null){
            holder.tv_carname.setText(list.get(position).getTitle());
        }
        if (list.get(position).getPrice()!=null){
            holder.tv_price.setText("$"+list.get(position).getPrice());
        }

        if (list.get(position).getYearVehicle()!=null){
            holder.tv_model.setText("Year: "+list.get(position).getYearVehicle());
        }

        if (list.get(position).getImages()!=null){
            String  names=list.get(position).getImages();
            String[] namesList = names.split(",");
            Glide.with(context).load(ApiUtils.IMAGE_BASE_URL+namesList[0]).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.img_car);

        }
        if (list.get(position).getFirst_name()!=null){
            holder.tv_name.setText(list.get(position).getFirst_name());
        }
    holder.bt_detail.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context, DetailsActivity.class);
            intent.putExtra("vehicleid",list.get(position).getId());
            context.startActivity(intent);
        }
    });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView img_car;
        private RatingBar rating;
        private TextView tv_carname,tv_price,tv_model,tv_name;
        private Button bt_detail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
            rating=itemView.findViewById(R.id.rating);
            img_car=itemView.findViewById(R.id.img_car);
            tv_model=itemView.findViewById(R.id.tv_model);
            tv_price=itemView.findViewById(R.id.tv_price);
            bt_detail=itemView.findViewById(R.id.bt_detail);
            tv_carname=itemView.findViewById(R.id.tv_carname);
        }
    }
}
