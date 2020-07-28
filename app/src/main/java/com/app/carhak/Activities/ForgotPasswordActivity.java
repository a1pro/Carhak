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
import com.app.carhak.Model.ForgotPasswordModel;
import com.app.carhak.R;
import com.app.carhak.Retrofit.ApiInterface;
import com.app.carhak.Retrofit.ApiUtils;
import com.app.carhak.Utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText et_email;
    private ImageView img_tick,img_back;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        et_email=findViewById(R.id.et_email);
        img_tick=findViewById(R.id.img_tick);
        img_back=findViewById(R.id.img_back);
        progressBar=findViewById(R.id.progressBar);

        img_tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.isConnected(ForgotPasswordActivity.this)){
                    if (Validations()){
                        ForgotPassword(et_email.getText().toString());
                    }
                }else {
                    Toast.makeText(ForgotPasswordActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean Validations(){
        if (et_email.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private void ForgotPassword (String email) {
        try {
            progressBar.setVisibility(View.VISIBLE);
            ApiInterface apiInteface;
            apiInteface = ApiUtils.getAPIService(this);
            apiInteface.ForgotPassword(email).enqueue(new Callback<ForgotPasswordModel>() {
                @Override
                public void onResponse(Call<ForgotPasswordModel> call, Response<ForgotPasswordModel> response) {
                    if (response.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        ForgotPasswordModel data=response.body();
                        if (data!=null){
                            if (data.getCode().equalsIgnoreCase("201")){

                                Toast.makeText(ForgotPasswordActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();

                                Intent intent1=new Intent(ForgotPasswordActivity.this,ChangePasswordActivity.class);
                                intent1.putExtra("otp",data.getOtp());
                                startActivity(intent1);

                            }
                            else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(ForgotPasswordActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ForgotPasswordModel> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ForgotPasswordActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("register failure",""+t.getMessage());
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}