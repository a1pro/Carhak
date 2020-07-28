package com.app.carhak.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.carhak.Model.LoginModel;
import com.app.carhak.R;
import com.app.carhak.Retrofit.ApiInterface;
import com.app.carhak.Retrofit.ApiUtils;
import com.app.carhak.SharedPrefence.DataProccessor;
import com.app.carhak.Utils.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
private TextView tv_signup,tv_forgot;
private ImageView img_tick,img_back;
private ProgressBar progressBar;
private EditText et_email,et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
    }

    private void init() {
        img_back=findViewById(R.id.img_back);
        tv_forgot=findViewById(R.id.tv_forgot);
        et_password=findViewById(R.id.et_password);
        et_email=findViewById(R.id.et_email);
        progressBar=findViewById(R.id.progressBar);
        img_tick=findViewById(R.id.img_tick);
        tv_signup=findViewById(R.id.tv_signup);
        img_tick.setOnClickListener(this);
        tv_signup.setOnClickListener(this);
        tv_forgot.setOnClickListener(this);
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_forgot:
                Intent intent1=new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_signup:
                Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                break;

            case R.id.img_tick:
               if (NetworkUtils.isConnected(LoginActivity.this)){
                   if (Validations()){
                       LoginApi(et_email.getText().toString(),et_password.getText().toString());
                   }
               }
                break;
        }
    }

    private boolean Validations(){
        if (et_email.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (et_password.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void LoginApi(String email,String password) {
        try {
            progressBar.setVisibility(View.VISIBLE);
            ApiInterface apiInteface;
            apiInteface = ApiUtils.getAPIService(this);
            apiInteface.LoginApi(email,password,"423dsfsrtwt43t4dfgd111","1").enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    if (response.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        LoginModel data=response.body();
                        if (data!=null){
                            if (data.getCode().equalsIgnoreCase("201")){
                                DataProccessor dataProccessor=new DataProccessor(LoginActivity.this);
                                dataProccessor.setUserid("userId",data.getData().get(0).getId());
                                Toast.makeText(LoginActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
                                savelogindata(data.getData().get(0).getEmail());
                                Intent intent1=new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent1);
                                finish();
                            }
                            else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Please Verify Your Email First", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("register failure",""+t.getMessage());
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    private void savelogindata(String email) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", email);
        editor.apply();

    }

}