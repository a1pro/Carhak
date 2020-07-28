package com.app.carhak.Fragments;

import android.content.Intent;
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

import com.app.carhak.Activities.LoginActivity;
import com.app.carhak.Activities.MainActivity;
import com.app.carhak.Adapters.BuyVechileGridAdapter;
import com.app.carhak.Adapters.BuyVechileVerticalAdapter;
import com.app.carhak.Model.CarsData;
import com.app.carhak.Model.GetAllBrandCars;
import com.app.carhak.Model.GetNewCars;
import com.app.carhak.Model.LoginModel;
import com.app.carhak.Model.NewCarsSaleData;
import com.app.carhak.R;
import com.app.carhak.Retrofit.ApiInterface;
import com.app.carhak.Retrofit.ApiUtils;
import com.app.carhak.Utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BuyVechileFragment extends Fragment {
private RecyclerView recyclerview;
    private BuyVechileVerticalAdapter adapter;
    private BuyVechileGridAdapter adapter2;
    String chView;
    private ProgressBar progressBar;
    private List<NewCarsSaleData> list=new ArrayList<>();
    private String year,model,zip;

    public BuyVechileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_buy_vechile, container, false);
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
                zip=getArguments().getString("zip");


            }
        }


        if (NetworkUtils.isConnected(getContext())){
            GetAllCars();
        }else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }



        return view;
    }



    private void GetAllCars() {
        try {
            progressBar.setVisibility(View.VISIBLE);
            ApiInterface apiInteface;
            apiInteface = ApiUtils.getAPIService(getContext());
            apiInteface.AllNewCarforSale(year,model,zip).enqueue(new Callback<GetNewCars>() {
                @Override
                public void onResponse(Call<GetNewCars> call, Response<GetNewCars> response) {
                    if (response.isSuccessful()){
                        list.clear();
                        progressBar.setVisibility(View.GONE);
                        GetNewCars data=response.body();
                        if (data!=null){
                            if (data.getCode().equalsIgnoreCase("201")){

                                list.addAll(data.getData());
                       //         Toast.makeText(getContext(), ""+data.getStatus(), Toast.LENGTH_SHORT).show();

                                if (chView!=null) {
                                    if (chView.equalsIgnoreCase("vertical")) {
                                        adapter=new BuyVechileVerticalAdapter(getContext(),list);
                                        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                                        recyclerview.setAdapter(adapter);

                                    } else if (chView.equalsIgnoreCase("Grid")){
                                        adapter2=new BuyVechileGridAdapter(getContext(),list);
                                        recyclerview.setLayoutManager(new GridLayoutManager(getContext(),2));
                                        recyclerview.setAdapter(adapter2);

                                    }else {
                                        adapter=new BuyVechileVerticalAdapter(getContext(),list);
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
                public void onFailure(Call<GetNewCars> call, Throwable t) {
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

//    private void SetAdapterVertically() {
//
//    }
//
//    private  void  setGrid(){
//
//    }


}