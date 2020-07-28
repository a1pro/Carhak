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
import com.app.carhak.Adapters.CustomAdapterSubpart;
import com.app.carhak.Adapters.CustomAdaptercategoreies;
import com.app.carhak.Adapters.NothingSelectedSpinnerAdapter;
import com.app.carhak.Model.AllBrandData;
import com.app.carhak.Model.AllCategoriesData;
import com.app.carhak.Model.GetAllBrand;
import com.app.carhak.Model.GetAllCategories;
import com.app.carhak.Model.GetSubPart;
import com.app.carhak.Model.SubPartData;
import com.app.carhak.R;
import com.app.carhak.Retrofit.ApiInterface;
import com.app.carhak.Retrofit.ApiUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterActivity extends AppCompatActivity {
    private ImageView imageOpen;
    private Button bt_search;
    private Spinner spinYear,spinner_model,spinner_part,spinner_subpart;
    private ProgressBar progressBar;
    private List<AllBrandData> brandDataList=new ArrayList<>();
    private List<AllCategoriesData> categoriesDataList=new ArrayList<>();
    private ArrayList<String> years = new ArrayList<String>();
    private List<SubPartData> subPartData=new ArrayList<>();
    private String BrandID;
    private String categoryId;
    private String year;
    private String zipcode;
    private String subpartid;
    private EditText et_zip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        et_zip=findViewById(R.id.et_zip);
        imageOpen=findViewById(R.id.imageOpen);
        progressBar=findViewById(R.id.progressBar);
        spinYear = (Spinner)findViewById(R.id.spinYear);
        spinner_model=findViewById(R.id.spinner_model);
        spinner_part=findViewById(R.id.spinner_part);
        spinner_subpart=findViewById(R.id.spinner_subpart);
        bt_search=findViewById(R.id.bt_search);
        spinner_part.setPrompt("Select");
        spinner_model.setPrompt("Select");
        spinner_subpart.setPrompt("Select");
        setSpinner();
        Brand();
        Categoriesdata();



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

        spinner_subpart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subpartid=subPartData.get(i).getSubCatId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zipcode=et_zip.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("year",year);
                intent.putExtra("vehiclemodel",BrandID);
                intent.putExtra("part_category",categoryId);
                intent.putExtra("zipcode",zipcode);
                intent.putExtra("subpartid",subpartid);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        spinner_part.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedItemView, int pos, long id) {

                String type = categoriesDataList.get(pos).getPartName();
                categoryId = categoriesDataList.get(pos).getId();
                SubPart(categoryId);

            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here


            }

        });
    }

    private void Categoriesdata() {

        try {
            progressBar.setVisibility(View.VISIBLE);
            ApiInterface apiInteface;
            apiInteface = ApiUtils.getAPIService(this);
            apiInteface.AllCategories().enqueue(new Callback<GetAllCategories>() {
                @Override
                public void onResponse(Call<GetAllCategories> call, Response<GetAllCategories> response) {
                    if (response.isSuccessful()){
                        categoriesDataList.clear();
                        progressBar.setVisibility(View.GONE);
                        GetAllCategories data=response.body();
                        if (data!=null){
                            if (data.getCode().equalsIgnoreCase("201")){
                                categoriesDataList.addAll(data.getData());

                                if (categoriesDataList.size()>0){
                                    CustomAdaptercategoreies bb = new CustomAdaptercategoreies(FilterActivity.this, 0, 0, categoriesDataList);
                                    bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinner_part.setAdapter(bb);
                                }
                            }
                            else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(FilterActivity.this, ""+data.getStatus(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetAllCategories> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(FilterActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("register failure",""+t.getMessage());
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }


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

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
                        brandDataList.clear();
                        progressBar.setVisibility(View.GONE);
                        GetAllBrand data=response.body();
                        if (data!=null){
                            if (data.getCode().equalsIgnoreCase("201")){
                                brandDataList.addAll(data.getData());
                                if (brandDataList.size()>0){
                                    CustomAdapter aa = new CustomAdapter(FilterActivity.this, 0, 0, brandDataList);
                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinner_model.setAdapter(aa);
                                }
                            }
                            else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(FilterActivity.this, ""+data.getStatus(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetAllBrand> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(FilterActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("register failure",""+t.getMessage());
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    private void SubPart(String partid) {
        try {
            progressBar.setVisibility(View.VISIBLE);
            ApiInterface apiInteface;
            apiInteface = ApiUtils.getAPIService(this);
            apiInteface.SubPart(partid).enqueue(new Callback<GetSubPart>() {
                @Override
                public void onResponse(Call<GetSubPart> call, Response<GetSubPart> response) {
                    if (response.isSuccessful()){
                        subPartData.clear();
                        progressBar.setVisibility(View.GONE);
                        GetSubPart data=response.body();
                        if (data!=null){
                            if (data.getCode().equalsIgnoreCase("201")){
                                subPartData.addAll(data.getData());
                                if (subPartData.size()>0){
                                    CustomAdapterSubpart aa = new CustomAdapterSubpart(FilterActivity.this, 0, 0, subPartData);
                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinner_subpart.setAdapter(aa);
                                }
                            }
                            else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(FilterActivity.this, "No SubPart Found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetSubPart> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(FilterActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("register failure",""+t.getMessage());
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}