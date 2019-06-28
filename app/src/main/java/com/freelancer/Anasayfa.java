package com.freelancer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    Button newProject;
    String eMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);

        tv = findViewById(R.id.TextView);
        getData = RetrofitClient.getRetrofitInstance().create(GetData.class);
        newProject=findViewById(R.id.Button_NewProject);
        eMail=new Intent().getStringExtra("eMail");
        tv.setText("Merhaba " + eMail);

        /*
        Call<List<User>> call = getData.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                for (int i=0;i<response.body().size();i++){
                    tv.setText(tv.getText()+"\n User: "+response.body().get(i).getName());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable throwable) {
                Toast.makeText(Anasayfa.this, "Unable to load users", Toast.LENGTH_SHORT).show();
            }
        });
        */

        Call<List<Proje>> call2 = getData.getProjects();
        call2.enqueue(new Callback<List<Proje>>() {
            @Override
            public void onResponse(Call<List<Proje>> call, Response<List<Proje>> response) {
                for (int i=0;i<response.body().size();i++){
                    tv.setText(tv.getText()+"\nProje: "+response.body().get(i).getName());
                }
            }

            @Override
            public void onFailure(Call<List<Proje>> call, Throwable throwable) {
                Toast.makeText(Anasayfa.this, "Unable to load users", Toast.LENGTH_SHORT).show();
            }
        });

        newProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Proje p=new Proje("YeniProje","Yeni Projeeeeeeeeee",0,0,0);
                Call<Proje> call=getData.NewProject(p);
                call.enqueue(new Callback<Proje>() {
                    @Override
                    public void onResponse(Call<Proje> call, Response<Proje> response) {
                        Toast.makeText(Anasayfa.this, "New project has created succesfully!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Proje> call, Throwable t) {

                    }
                });


            }
        });

        /*
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
        */

        /*
        User user2 = new User(2,"","","",0);
        Call<User> call2 = getData.UpdateUser(2,user2);
        call2.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User loginResponse = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
        */
    }

}
