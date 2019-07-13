package com.freelancer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.API.GetData;
import com.freelancer.API.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadCredit extends AppCompatActivity {

    EditText editText_Amount, editText_CardNumber,editText_ExpirationDate,editText_SecurityNumber;
    Button button_Load;
    GetData getData;
    String mailAdress, password, userName;
    int userId, credit, artisMiktari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_credit);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getData= RetrofitClient.getRetrofitInstance().create(GetData.class);

        editText_Amount=findViewById(R.id.editText_Amount);
        editText_CardNumber=findViewById(R.id.editText_CardNumber);
        editText_ExpirationDate=findViewById(R.id.editText_ExpirationDate);
        editText_SecurityNumber=findViewById(R.id.editText_SecurityNumber);
        button_Load=findViewById(R.id.button_Load);

        Intent intent= getIntent();
        userId=intent.getIntExtra("userId",-1);
        mailAdress = intent.getStringExtra("mailAdress");
        password=intent.getStringExtra("password");
        userName=intent.getStringExtra("userName");
        credit=intent.getIntExtra("credit",0);


        button_Load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                artisMiktari=Integer.parseInt(editText_Amount.getText().toString());
                User user =new User(userId,userName,mailAdress,password,credit+artisMiktari);
                Call<User> call = getData.UpdateUser(user);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Toast.makeText(LoadCredit.this,"Bakiye yüklemesi gerçekleştirildi\n"+response.message(),Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(LoadCredit.this,"Bir hata oluştu",Toast.LENGTH_SHORT).show();
                        call.cancel();
                    }
                });
            }
        });
    }
}
