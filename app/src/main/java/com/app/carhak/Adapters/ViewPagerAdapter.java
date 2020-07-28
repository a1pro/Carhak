package com.app.carhak.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.app.carhak.R;
import com.app.carhak.Retrofit.ApiUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private String[] imageList;
    String type;


    public ViewPagerAdapter(Context context, String[] imageList,String type) {
        this.context = context;
        this.imageList=imageList;
        this.type=type;
    }

    @Override
    public int getCount() {
        return imageList.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

        try {
            if (type.equalsIgnoreCase("vehicle")){
                Glide.with(context).load(ApiUtils.IMAGE_BASE_URL+imageList[position]).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            }else {
                Glide.with(context).load(ApiUtils.IMAGE_PARTS_BASE_URL+imageList[position]).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            }


        }catch (NumberFormatException e){

        }



        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}
