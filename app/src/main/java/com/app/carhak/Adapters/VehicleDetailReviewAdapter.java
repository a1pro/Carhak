package com.app.carhak.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.carhak.Model.VehicleRating;
import com.app.carhak.R;
import com.app.carhak.Retrofit.ApiUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.security.AccessControlContext;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class VehicleDetailReviewAdapter extends RecyclerView.Adapter<VehicleDetailReviewAdapter.ViewHolder> {
    List<VehicleRating> list;
    Context context;
    int visibleitem;

    public VehicleDetailReviewAdapter(Context context, List<VehicleRating> list, int visibleitem) {
        this.context = context;
        this.list = list;
        this.visibleitem=visibleitem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new VehicleDetailReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Drawable drawable = holder.rating.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#ffff8800"), PorterDuff.Mode.SRC_ATOP);
        if (list.get(position).getVContent()!=null){
            holder.tv_desc.setText(list.get(position).getVContent());
        }
        if (list.get(position).getFirstName()!=null){
            holder.tv_header.setText(list.get(position).getFirstName());
        }

        if (list.get(position).getProfileImg()!=null && !list.get(position).getProfileImg().isEmpty()){
            Glide.with(context).load(ApiUtils.IMAGE_URL+list.get(position).getProfileImg()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.profileimg);
        }

        if (list.get(position).getVRating()!=null){
            holder.rating.setRating(Integer.parseInt(list.get(position).getVRating()));
        }
    }


    @Override
    public int getItemCount() {
        return visibleitem;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_header, tv_desc,tv_reviewcost;
        private RatingBar rating;
        private CircleImageView profileimg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_header = itemView.findViewById(R.id.tv_header);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            rating = itemView.findViewById(R.id.rating);
            profileimg=itemView.findViewById(R.id.profileimg);
        }
    }
}
