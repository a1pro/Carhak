package com.app.carhak.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.app.carhak.Adapters.VehicleDetailReviewAdapter;
import com.app.carhak.Adapters.ViewPagerAdapter;
import com.app.carhak.Model.GetSingleVehicle;
import com.app.carhak.Model.VehicleRating;
import com.app.carhak.R;
import com.app.carhak.Retrofit.ApiInterface;
import com.app.carhak.Retrofit.ApiUtils;
import com.app.carhak.Utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageOpen, img_profile;
    private RatingBar rating, rating2;
    private TextView tv_carname, tv_price, tv_year, tv_lorem, tv_country, tv_reviewcost, tv_mileage, tv_Transmission, tv_Doors;
    private TextView tv_see, tv_city, tv_noreview, tv_seller_name;
    private ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private ProgressBar progressBar;
    private String Vehicleid;
    private RecyclerView recyclerview;
    private VehicleDetailReviewAdapter adapter;
    private List<VehicleRating> list=new ArrayList<>();
    private int visibleitem=1;
    private boolean see=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        init();

        Drawable drawable = rating.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#ffff8800"), PorterDuff.Mode.SRC_ATOP);


         Vehicleid = getIntent().getStringExtra("vehicleid");
//        Toast.makeText(this, "vehicleid"+Vehicleid, Toast.LENGTH_SHORT).show();

        if (NetworkUtils.isConnected(DetailsActivity.this)){
            if (Vehicleid!=null){
                GetDetail(Vehicleid);
            }
        }


    }

    private void setViewpager(String images) {
        String[] imageList = images.split(",");

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this,imageList,"vehicle");
        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void init() {
        tv_seller_name=findViewById(R.id.tv_seller_name);
        tv_noreview=findViewById(R.id.tv_noreview);
        tv_see=findViewById(R.id.tv_see);
        recyclerview=findViewById(R.id.recyclerview);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        progressBar=findViewById(R.id.progressBar);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        tv_city = findViewById(R.id.tv_city);
        rating = findViewById(R.id.rating);
        tv_carname = findViewById(R.id.tv_carname);
        tv_reviewcost = findViewById(R.id.tv_reviewcost);
        tv_price = findViewById(R.id.tv_price);
        tv_year = findViewById(R.id.tv_year);
        tv_country = findViewById(R.id.tv_country);
        img_profile = findViewById(R.id.img_profile);
        imageOpen = findViewById(R.id.imageOpen);
        imageOpen.setOnClickListener(this);
        tv_see.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageOpen:
                finish();
                break;

            case R.id.tv_see:
                if (see==false) {
                    visibleitem = list.size();
                    if (list.size() > 0) {
                        adapter = new VehicleDetailReviewAdapter(DetailsActivity.this, list, visibleitem);
                        recyclerview.setLayoutManager(new LinearLayoutManager(DetailsActivity.this));
                        recyclerview.setAdapter(adapter);
                    }
                    tv_see.setText("See Less");
                    see=true;
                }else {
                    visibleitem = 1;
                    if (list.size() > 0) {
                        adapter = new VehicleDetailReviewAdapter(DetailsActivity.this, list, visibleitem);
                        recyclerview.setLayoutManager(new LinearLayoutManager(DetailsActivity.this));
                        recyclerview.setAdapter(adapter);
                        see=false;
                        tv_see.setText("See More");
                    }
                }

                break;
        }

    }

    private void GetDetail(String vehicle) {
        try {
            progressBar.setVisibility(View.VISIBLE);
            ApiInterface apiInteface;
            apiInteface = ApiUtils.getAPIService(this);
            apiInteface.SingleVehicleDetail(vehicle).enqueue(new Callback<GetSingleVehicle>() {
                @Override
                public void onResponse(Call<GetSingleVehicle> call, Response<GetSingleVehicle> response) {
                    if (response.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        GetSingleVehicle data=response.body();
                        list.clear();
                        if (data!=null){
                            if (data.getCode().equalsIgnoreCase("201")){

                                if (data.getData().get(0).getImages()!=null){
                                    setViewpager(data.getData().get(0).getImages());
                                }

                                if (data.getData().get(0).getTitle()!=null){
                                    tv_carname.setText(data.getData().get(0).getTitle());
                                }

                                if (data.getData().get(0).getPrice()!=null){
                                    tv_price.setText("$"+data.getData().get(0).getPrice());
                                }

                                if (data.getData().get(0).getYearVehicle()!=null){
                                    tv_year.setText(data.getData().get(0).getYearVehicle());
                                }

                                if (data.getData().get(0).getCountryName()!=null){
                                    tv_country.setText(data.getData().get(0).getCountryName());
                                }

                                if (data.getData().get(0).getAverageRating()!=null){
                                    rating.setRating(data.getData().get(0).getAverageRating());
                                }

                                if (data.getData().get(0).getFirstName()!=null && data.getData().get(0).getLastName()!=null){
                                    tv_seller_name.setText(data.getData().get(0).getFirstName()+" "+data.getData().get(0).getLastName());
                                }


                                list.addAll(data.getRatingList());
                                if (list.size()>0){
                                    adapter=new VehicleDetailReviewAdapter(DetailsActivity.this,list,visibleitem);
                                    recyclerview.setLayoutManager(new LinearLayoutManager(DetailsActivity.this));
                                    recyclerview.setAdapter(adapter);
                                }else {
                                    tv_see.setVisibility(View.GONE);
                                    tv_noreview.setVisibility(View.VISIBLE);
                                }
                                if (list.size()==1){
                                    tv_see.setVisibility(View.GONE);
                                }


                            }
                            else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(DetailsActivity.this, ""+data.getStatus(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetSingleVehicle> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(DetailsActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(" failure",""+t.getMessage());
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}