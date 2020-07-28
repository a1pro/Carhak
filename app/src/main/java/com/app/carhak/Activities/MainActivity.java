package com.app.carhak.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.app.carhak.Model.ResponseData;
import com.app.carhak.R;
import com.app.carhak.Retrofit.ApiInterface;
import com.app.carhak.Retrofit.ApiUtils;
import com.app.carhak.SharedPrefence.DataProccessor;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavController navController;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ProgressBar progressBar1;
    private ImageView imageOpen, img_close, img_view, img_filter;
    private TextView title_tv;
    private BottomNavigationView navigation;
    private boolean changeview = false;
    private int pos;
    String year,model,category,subpartid;
    private ProgressBar progressBar;
    private String userid;
    private String Zipcode;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.bottom_buyparts:
                    pos = 1;
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("view", "vertical");
                    navController.navigate(R.id.buyPartsFragment, bundle2);
                    title_tv.setText("Buy Parts");
                    img_view.setVisibility(View.VISIBLE);
                    img_filter.setVisibility(View.VISIBLE);
                    img_view.setImageDrawable(getResources().getDrawable(R.mipmap.category));
                    year="";
                    model="";
                    category="";
                    Zipcode="";
                    subpartid="";
                    return true;

                case R.id.bottom_buyvechiles:
                    pos = 2;
                    Bundle bundle = new Bundle();
                    bundle.putString("view", "vertical");
                    navController.navigate(R.id.buyVechileFragment, bundle);
                    title_tv.setText("Buy Vehicles");
                    img_view.setVisibility(View.VISIBLE);
                    img_filter.setVisibility(View.VISIBLE);
                    img_view.setImageDrawable(getResources().getDrawable(R.mipmap.category));
                    year="";
                    model="";
                    Zipcode="";
                    return true;

                case R.id.bottom_profile:
                    pos = 3;
                    navController.navigate(R.id.profileFragment);
                    title_tv.setText("Profile");
                    img_view.setImageDrawable(getResources().getDrawable(R.mipmap.logout));
                    img_filter.setVisibility(View.GONE);
                    return true;


            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        DataProccessor dataProccessor = new DataProccessor(MainActivity.this);
        userid = dataProccessor.getUserid("userId");




        imageOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(Gravity.LEFT);


            }
        });

        View headerView = navigationView.getHeaderView(0);
        img_close = headerView.findViewById(R.id.img_close);

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos==1){
                    Intent intent = new Intent(MainActivity.this, FilterActivity.class);
                    startActivityForResult(intent,1);
                }else {
                    Intent intent = new Intent(MainActivity.this, FilterActivityVehicle.class);
                    startActivityForResult(intent,2);
                }

            }
        });

        img_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos == 1) {
                    if (changeview == false) {
                        Bundle bundle = new Bundle();
                        bundle.putString("view", "Grid");
                        bundle.putString("partYear", year);
                        bundle.putString("model", model);
                        bundle.putString("partcategory", category);
                        bundle.putString("zip", Zipcode);
                        bundle.putString("subpart", subpartid);
                        navController.navigate(R.id.buyPartsFragment, bundle);
                        changeview = true;
                        img_view.setImageDrawable(getResources().getDrawable(R.drawable.bullted_list));

                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("view", "vertical");
                        bundle.putString("partYear", year);
                        bundle.putString("model", model);
                        bundle.putString("partcategory", category);
                        bundle.putString("zip", Zipcode);
                        bundle.putString("subpart", subpartid);
                        navController.navigate(R.id.buyPartsFragment, bundle);
                        img_view.setImageDrawable(getResources().getDrawable(R.mipmap.category));
                        changeview = false;

                    }
                } else if (pos==2){
                    if (changeview == false) {
                        Bundle bundle = new Bundle();
                        bundle.putString("view", "Grid");
                        bundle.putString("partYear", year);
                        bundle.putString("model", model);
                        bundle.putString("zip", Zipcode);
                        navController.navigate(R.id.buyVechileFragment, bundle);
                        changeview = true;
                        img_view.setImageDrawable(getResources().getDrawable(R.drawable.bullted_list));
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("view", "vertical");
                        bundle.putString("partYear", year);
                        bundle.putString("model", model);
                        bundle.putString("zip", Zipcode);
                        navController.navigate(R.id.buyVechileFragment, bundle);
                        img_view.setImageDrawable(getResources().getDrawable(R.mipmap.category));
                        changeview = false;
                    }
                }else if (pos==3){
                    showPopup();
                }
            }
        });


    }

    private void showPopup() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setMessage("Are you sure?")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener()                 {

                    public void onClick(DialogInterface dialog, int which) {
                        if (userid!=null){
                            Logout(userid);
                        }


                    }
                }).setNegativeButton("Cancel", null);

        AlertDialog alert1 = alert.create();
        alert1.show();
    }

    private void init() {
        navController = Navigation.findNavController(MainActivity.this, R.id.mainNavFragment2);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        progressBar1 = findViewById(R.id.pro1);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        img_view = findViewById(R.id.img_view);
        img_filter = findViewById(R.id.img_filter);
        progressBar=findViewById(R.id.progressBar);
        title_tv = findViewById(R.id.title_tv);
        imageOpen = findViewById(R.id.imageOpen);
        navigationView.setItemIconTintList(null);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
        navController.getCurrentDestination().getId();
        navController.addOnDestinationChangedListener(onDestinationChangedListener);
        img_view.setVisibility(View.VISIBLE);
        navigation = (BottomNavigationView) findViewById(R.id.nav_view);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigationView);
        pos = 1;
        title_tv.setText("Buy Parts");
        Bundle bundle2 = new Bundle();
        bundle2.putString("view", "vertical");
        navController.navigate(R.id.buyPartsFragment, bundle2);
    }


    NavController.OnDestinationChangedListener onDestinationChangedListener = new NavController.OnDestinationChangedListener() {
        @Override
        public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {

            if (R.id.buyPartsFragment == destination.getId()) {
                pos = 1;
                img_view.setImageDrawable(getResources().getDrawable(R.mipmap.category));
                img_filter.setVisibility(View.VISIBLE);
            }

            if (R.id.buyVechileFragment == destination.getId()) {
                pos = 2;
                img_view.setImageDrawable(getResources().getDrawable(R.mipmap.category));
                img_filter.setVisibility(View.VISIBLE);


            }
            if (R.id.profileFragment == destination.getId()) {
                pos = 3;
                img_view.setImageDrawable(getResources().getDrawable(R.mipmap.logout));
                img_filter.setVisibility(View.GONE);


            }

        }
    };


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();
        int id = menuItem.getItemId();
        switch (id) {

            case R.id.buyparts:
                Bundle bundle2 = new Bundle();
                bundle2.putString("view", "vertical");
                navController.navigate(R.id.buyPartsFragment, bundle2);
                title_tv.setText("Buy Parts");
                img_view.setVisibility(View.VISIBLE);
                year="";
                model="";
                category="";
                Zipcode="";
                subpartid="";
                break;


            case R.id.buyvechile:
                Bundle bundle = new Bundle();
                bundle.putString("view", "vertical");
                navController.navigate(R.id.buyVechileFragment, bundle);
                title_tv.setText("Buy Vehicles");
                img_view.setVisibility(View.VISIBLE);
                year="";
                model="";
                Zipcode="";
                break;


            case R.id.profile:
                pos = 3;
                navController.navigate(R.id.profileFragment);
                title_tv.setText("Profile");
                // img_view.setImageDrawable(getResources().getDrawable(R.mipmap.logout));
                //   img_filter.setVisibility(View.GONE);
                break;

//            case R.id.notification:
//                title_tv.setText("Notification");
//                img_view.setVisibility(View.GONE);
//                break;


            case R.id.settings:
                Intent intent2=new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent2);
                break;


            case R.id.about:
                Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
                startActivity(intent);
                break;

            case R.id.contact:
                Intent intent1 = new Intent(MainActivity.this, ContactUsActivity.class);
                startActivity(intent1);
                break;


        }
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(MainActivity.this, R.id.mainNavFragment2), drawerLayout);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                 year = data.getStringExtra("year");
                 model = data.getStringExtra("vehiclemodel");
                 category = data.getStringExtra("part_category");
                 Zipcode=data.getStringExtra("zipcode");
                subpartid=data.getStringExtra("subpartid");
                pos=1;
                Bundle bundle = new Bundle();
                bundle.putString("view", "vertical");
                bundle.putString("partYear", year);
                bundle.putString("model", model);
                bundle.putString("partcategory", category);
                bundle.putString("zip", Zipcode);
                bundle.putString("subpart", subpartid);
                navController.navigate(R.id.buyPartsFragment,bundle);

            }
        }

        if (requestCode==2){
            if (resultCode == RESULT_OK){
                assert data != null;
                year = data.getStringExtra("year");
                model = data.getStringExtra("vehiclemodel");
                Zipcode=data.getStringExtra("zipcode");


                pos=2;
                Bundle bundle = new Bundle();
                bundle.putString("view", "vertical");
                bundle.putString("partYear", year);
                bundle.putString("model", model);
                bundle.putString("zip", Zipcode);
                navController.navigate(R.id.buyVechileFragment,bundle);
            }
        }
    }

    private void Logout (String user_id) {
        try {
            progressBar.setVisibility(View.VISIBLE);
            ApiInterface apiInteface;
            apiInteface = ApiUtils.getAPIService(this);
            apiInteface.Logout(user_id).enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    if (response.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        ResponseData data=response.body();
                        if (data!=null){
                            if (data.getCode().equalsIgnoreCase("201")){

                                Toast.makeText(MainActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();

                                Intent intent1=new Intent(MainActivity.this,LoginActivity.class);
                                startActivity(intent1);
                                finish();
                            }
                            else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseData> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("register failure",""+t.getMessage());
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}