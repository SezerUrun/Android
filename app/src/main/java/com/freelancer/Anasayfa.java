package com.freelancer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.freelancer.API.GetData;
import com.freelancer.API.User;
import com.freelancer.API.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Anasayfa extends AppCompatActivity {
    TextView tv;
    GetData getData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);

        tv = findViewById(R.id.TextView);
        getData = RetrofitClient.getRetrofitInstance().create(GetData.class);

        Call<List<User>> call = getData.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                loadDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable throwable) {
                Toast.makeText(Anasayfa.this, "Unable to load users", Toast.LENGTH_SHORT).show();
            }
        });
        /*
        RetroUsers user = new RetroUsers("deneme","deneme@deneme.com","1234",12345678);
        Call<RetroUsers> call1 = getData.register(user);
        call1.enqueue(new Callback<RetroUsers>() {
            @Override
            public void onResponse(Call<RetroUsers> call, Response<RetroUsers> response) {
                RetroUsers loginResponse = response.body();
            }

            @Override
            public void onFailure(Call<RetroUsers> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
        */
        /*
        RetroUsers user2 = new RetroUsers(2,"update","update@update.com","1234",12345678);
        Call<RetroUsers> call2 = getData.update(2,user2);
        call2.enqueue(new Callback<RetroUsers>() {
            @Override
            public void onResponse(Call<RetroUsers> call, Response<RetroUsers> response) {
                RetroUsers loginResponse = response.body();
            }

            @Override
            public void onFailure(Call<RetroUsers> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
        */
    }

    private void loadDataList(List<User> usersList) {
        for (int i=0;i<usersList.size();i++){
            tv.setText(tv.getText()+"\n"+usersList.get(i).getName());
        }
    }
}
