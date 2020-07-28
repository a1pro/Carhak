package com.app.carhak.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.carhak.Adapters.CustomAdapter;
import com.app.carhak.Adapters.NothingSelectedSpinnerAdapter;
import com.app.carhak.Model.AllBrandData;
import com.app.carhak.Model.GetAllBrand;
import com.app.carhak.R;
import com.app.carhak.Retrofit.ApiInterface;
import com.app.carhak.Retrofit.ApiUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterActivityVehicle extends AppCompatActivity {
    private ImageView imageOpen;
    private Button bt_search;
    private Spinner spinYear,spinner_model,spinner_part;
    private ProgressBar progressBar;
    private List<AllBrandData> brandDataList=new ArrayList<>();
    private ArrayList<String> years = new ArrayList<String>();
    private String BrandID;
    private String categoryId;
    private String year;
    private EditText et_zip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_vehicle);
        et_zip=findViewById(R.id.et_zip);
        imageOpen=findViewById(R.id.imageOpen);
        progressBar=findViewById(R.id.progressBar);
        spinYear = (Spinner)findViewById(R.id.spinYear);
        spinner_model=findViewById(R.id.spinner_model);
        bt_search=findViewById(R.id.bt_search);
        setSpinner();
        Brand();


        imageOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        spinner_model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedItemView, int pos, long id) {

                String type = brandDataList.get(pos).getBrandName();
                BrandID = brandDataList.get(pos).getId();

            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here

            }

        });


        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String zip=et_zip.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("year",year);
                intent.putExtra("vehiclemodel",BrandID);
                intent.putExtra("zipcode",zip);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    private void setSpinner() {

        spinYear.setPrompt("Select Year");

        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i >= 1980; i--) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);


        spinYear.setAdapter(adapter);
        spinYear.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));

        spinYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year=years.get(i).toString();
                //   Toast.makeText(FilterActivity.this, ""+year, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
//                year="";
//                Toast.makeText(FilterActivity.this, ""+year, Toast.LENGTH_SHORT).show();
            }


        });
    }

    private void Brand () {
        try {
            progressBar.setVisibility(View.VISIBLE);
            ApiInterface apiInteface;
            apiInteface = ApiUtils.getAPIService(this);
            apiInteface.AllBrand().enqueue(new Callback<GetAllBrand>() {
                @Override
                public void onResponse(Call<GetAllBrand> call, Response<GetAllBrand> response) {
                    if (response.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        GetAllBrand data=response.body();
                        if (data!=null){
                            if (data.getCode().equalsIgnoreCase("201")){
                                brandDataList.addAll(data.getData());
                                if (brandDataList.size()>0){
                                    CustomAdapter aa = new CustomAdapter(FilterActivityVehicle.this, 0, 0, brandDataList);
                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinner_model.setAdapter(aa);
                                }
                            }
                            else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(FilterActivityVehicle.this, ""+data.getStatus(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetAllBrand> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(FilterActivityVehicle.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("register failure",""+t.getMessage());
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}