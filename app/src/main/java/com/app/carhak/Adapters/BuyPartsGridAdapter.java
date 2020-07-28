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
import com.app.carhak.Activities.PartsDetailActivity;
import com.app.carhak.Model.PartsData;
import com.app.carhak.R;
import com.app.carhak.Retrofit.ApiUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class BuyPartsGridAdapter extends RecyclerView.Adapter<BuyPartsGridAdapter.ViewHolder> {
    Context context;
    List<PartsData> list;

    public BuyPartsGridAdapter(Context context, List<PartsData> list) {
        this.context = context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buyparts_grid_items, parent, false);
        return new BuyPartsGridAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Drawable drawable = holder.rating.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#ffff8800"), PorterDuff.Mode.SRC_ATOP);

        if (list.get(position).getTitle()!=null){
            holder.tv_carname.setText(list.get(position).getTitle());
        }

        holder.rating.setRating(list.get(position).getAverageRating());

        if (list.get(position).getYearVehicle()!=null){
            holder.tv_year.setText("Year : "+list.get(position).getYearVehicle());
        }

        if (list.get(position).getPrice()!=null){
            holder.tv_price.setText("$"+list.get(position).getPrice());
        }

        if (list.get(position).getCityName()!=null){
            holder.tv_location.setText(list.get(position).getCityName());
        }

        if (list.get(position).getImages()!=null){
            String  names=list.get(position).getImages();
            String[] namesList = names.split(",");
            Glide.with(context).load(ApiUtils.IMAGE_PARTS_BASE_URL+namesList[0]).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.img_car);

        }

        holder.bt_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PartsDetailActivity.class);
                intent.putExtra("partId",list.get(position).getId());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button bt_detail;
        private TextView tv_carname;
        private RatingBar rating;
        private CircleImageView img_car;
        private TextView tv_year,tv_location,tv_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_price=itemView.findViewById(R.id.tv_price);
            tv_location=itemView.findViewById(R.id.tv_location);
            tv_year=itemView.findViewById(R.id.tv_year);
            img_car=itemView.findViewById(R.id.img_car);
            rating=itemView.findViewById(R.id.rating);
            bt_detail = itemView.findViewById(R.id.bt_detail);
            tv_carname=itemView.findViewById(R.id.tv_carname);
        }
    }
}