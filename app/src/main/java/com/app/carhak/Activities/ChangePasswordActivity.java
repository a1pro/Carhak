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
import android.widget.Toast;
import com.app.carhak.Model.ResponseData;
import com.app.carhak.R;
import com.app.carhak.Retrofit.ApiInterface;
import com.app.carhak.Retrofit.ApiUtils;
import com.app.carhak.Utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener{
    private ProgressBar progressBar;
    private EditText et_otp,et_password,et_Conpassword;
    private ImageView img_tick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
        String otp=getIntent().getStringExtra("otp");



    }
    private void init() {
        img_tick=findViewById(R.id.img_tick);
        et_otp=findViewById(R.id.et_otp);
        et_password=findViewById(R.id.et_password);
        et_Conpassword=findViewById(R.id.et_Conpassword);
        progressBar=findViewById(R.id.progressBar);
        img_tick.setOnClickListener(this);
    }

    private boolean Validations(){
        String password=et_password.getText().toString();
        String conpassword=et_Conpassword.getText().toString();
        if (et_otp.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Enter Otp", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (et_password.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if (!conpassword.equalsIgnoreCase(password)){
            Toast.makeText(this, "Password & Confirm Password doesn't Match", Toast.LENGTH_SHORT).show();
            return false;
        }



        return true;
    }


    private void ChangePassword (String otp,String pass,String ConPass) {
        try {
            progressBar.setVisibility(View.VISIBLE);
            ApiInterface apiInteface;
            apiInteface = ApiUtils.getAPIService(this);
            apiInteface.ChangePassword(otp,pass,ConPass).enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    if (response.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        ResponseData data=response.body();
                        if (data!=null){
                            if (data.getCode().equalsIgnoreCase("201")){

                                Toast.makeText(ChangePasswordActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();

                                Intent intent1=new Intent(ChangePasswordActivity.this,LoginActivity.class);
                                startActivity(intent1);
                                finish();
                            }
                            else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(ChangePasswordActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseData> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ChangePasswordActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
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
            case R.id.img_tick:
                if (NetworkUtils.isConnected(this)){
                    if (Validations()){
                        ChangePassword(et_otp.getText().toString()
                                ,et_password.getText().toString()
                                ,et_Conpassword.getText().toString());

                    }
                }else {
                    Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
                break;


        }
    }
}