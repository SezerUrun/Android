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
    static String mailAdress, password, userName;
    static int userId, credit, artisMiktari;

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
        //KULLANICI BİLGİLERİNİN ALINMASI
        Call<User> call = getData.getUser(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body()!=null){
                    mailAdress=response.body().getMail();
                    userName=response.body().getName();
                    password=response.body().getPassword();
                    credit=response.body().getCredit();
                }
                else{
                    Toast.makeText(LoadCredit.this,"Kullanıcı bilgileri alınırken bir hata oluştu\n"+response.message(),Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoadCredit.this,"Sunucu ile bağlantı kurulurken bir hata oluştu\n"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        button_Load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                artisMiktari=Integer.parseInt(editText_Amount.getText().toString());
                User user =new User(userId,userName,mailAdress,password,credit+artisMiktari);
                Call<User> call = getData.UpdateUser(user);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        editText_Amount.setText("");
                        editText_CardNumber.setText("");
                        editText_ExpirationDate.setText("");
                        editText_SecurityNumber.setText("");
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
