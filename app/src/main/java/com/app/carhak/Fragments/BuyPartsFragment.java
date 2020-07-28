package com.app.carhak.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.carhak.Activities.MainActivity;
import com.app.carhak.Adapters.BuyPartsGridAdapter;
import com.app.carhak.Adapters.BuyPartsVerticalAdapter;
import com.app.carhak.Adapters.BuyVechileGridAdapter;
import com.app.carhak.Adapters.BuyVechileVerticalAdapter;
import com.app.carhak.Model.GetAllBrandCars;
import com.app.carhak.Model.GetAllParts;
import com.app.carhak.Model.PartsData;
import com.app.carhak.R;
import com.app.carhak.Retrofit.ApiInterface;
import com.app.carhak.Retrofit.ApiUtils;
import com.app.carhak.Utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BuyPartsFragment extends Fragment {
private RecyclerView recyclerview;
private BuyPartsVerticalAdapter adapter;
private BuyPartsGridAdapter adapter2;
String chView;
private ProgressBar progressBar;
private List<PartsData> list=new ArrayList<>();
String year,model,category,zip,subpartid;


    public BuyPartsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_buy_parts, container, false);
        recyclerview=view.findViewById(R.id.recyclerview);
        progressBar=view.findViewById(R.id.progressBar);


        if (getArguments()!=null){
            if (getArguments().getString("view")!=null){
                chView=getArguments().getString("view");
            }
        }

        if (getArguments()!=null){
            if (getArguments().getString("partYear")!=null){
                year=getArguments().getString("partYear");
                model=getArguments().getString("model");
                category=getArguments().getString("part_category");
                zip=getArguments().getString("zip");
                subpartid=getArguments().getString("subpart");


            }
        }

        if (NetworkUtils.isConnected(getContext())){
            GetAllParts();
        }else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


        return view;
    }



    private void GetAllParts() {
        try {
            progressBar.setVisibility(View.VISIBLE);
            ApiInterface apiInteface;
            apiInteface = ApiUtils.getAPIService(getContext());
            apiInteface.GetAllParts(year,model,category,zip,subpartid).enqueue(new Callback<GetAllParts>() {
                @Override
                public void onResponse(Call<GetAllParts> call, Response<GetAllParts> response) {
                    if (response.isSuccessful()){
                        list.clear();
                        progressBar.setVisibility(View.GONE);
                        GetAllParts data=response.body();
                        if (data!=null){
                            if (data.getCode().equalsIgnoreCase("201")){

                                list.addAll(data.getData());
                            //    Toast.makeText(getContext(), ""+data.getStatus(), Toast.LENGTH_SHORT).show();

                                if (chView!=null) {
                                    if (chView.equalsIgnoreCase("vertical")) {
                                        adapter=new BuyPartsVerticalAdapter(getContext(),list);
                                        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                                        recyclerview.setAdapter(adapter);

                                    } else if (chView.equalsIgnoreCase("Grid")){
                                        adapter2=new BuyPartsGridAdapter(getContext(),list);
                                        recyclerview.setLayoutManager(new GridLayoutManager(getContext(),2));
                                        recyclerview.setAdapter(adapter2);
                                    }else {
                                        adapter=new BuyPartsVerticalAdapter(getContext(),list);
                                        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                                        recyclerview.setAdapter(adapter);

                                    }
                                }


                            }
                            else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "No Item Found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetAllParts> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("failure",""+t.getMessage());
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



}