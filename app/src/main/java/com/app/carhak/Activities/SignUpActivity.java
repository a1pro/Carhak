package com.app.carhak.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.carhak.Model.LoginModel;
import com.app.carhak.Model.ResponseData;
import com.app.carhak.R;
import com.app.carhak.Retrofit.ApiInterface;
import com.app.carhak.Retrofit.ApiUtils;
import com.app.carhak.Utils.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private ProgressBar progressBar;
    private EditText et_email,et_name,et_no,et_password;
    private ImageView img_tick,img_back;
    private TextView tv_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void init() {
        tv_signin=findViewById(R.id.tv_signin);
        img_back=findViewById(R.id.img_back);
        img_tick=findViewById(R.id.img_tick);
        et_email=findViewById(R.id.et_email);
        et_name=findViewById(R.id.et_name);
        et_no=findViewById(R.id.et_no);
        et_password=findViewById(R.id.et_password);
        progressBar=findViewById(R.id.progressBar);
        img_tick.setOnClickListener(this);
        img_back.setOnClickListener(this);
        tv_signin.setOnClickListener(this);
    }

    private boolean Validations(){
        if (et_name.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (et_email.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if (et_no.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Enter Number", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if (et_email.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void RegisterApi(String email,String password,String name,String phone) {
        try {
            progressBar.setVisibility(View.VISIBLE);
            ApiInterface apiInteface;
            apiInteface = ApiUtils.getAPIService(this);
            apiInteface.RegisterApi(email,password,name,phone).enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    if (response.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        ResponseData data=response.body();
                        if (data!=null){
                            if (data.getCode().equalsIgnoreCase("201")){

                                Toast.makeText(SignUpActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
                                Intent intent1=new Intent(SignUpActivity.this,LoginActivity.class);
                                startActivity(intent1);
                                finish();
                            }
                            else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(SignUpActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseData> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignUpActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("register failure",""+t.getMessage());
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_signin:
                Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.img_back:
                finish();
                break;
            case R.id.img_tick:
            if (NetworkUtils.isConnected(this)){
                if (Validations()){
                    RegisterApi(et_email.getText().toString(),
                            et_password.getText().toString(),
                            et_name.getText().toString(),
                            et_no.getText().toString());
                }
            }else {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
                break;
        }
    }
}