package com.freelancer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.API.GetData;
import com.freelancer.API.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjeOlusturmaSayfasi extends AppCompatActivity {
    String mailAdress,password,userName,header,description;
    int userId, maxPrice;
    GetData getData;
    EditText EditText_Header, EditText_Description, EditText_MaxPrice;
    Button Button_ReleaseProject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proje_olusturma_sayfasi);

        EditText_Header=findViewById(R.id.EditText_ProjectHeader);
        EditText_Description=findViewById(R.id.EditText_ProjectDescription);
        EditText_MaxPrice=findViewById(R.id.EditText_MaxPrice);
        Button_ReleaseProject=findViewById(R.id.Button_ReleaseProject);
        Intent intent=getIntent();
        userId=intent.getIntExtra("userId",-1);
        mailAdress = intent.getStringExtra("mailAdress");
        password=intent.getStringExtra("password");
        userName=intent.getStringExtra("userName");
        getData = RetrofitClient.getRetrofitInstance().create(GetData.class);

        Button_ReleaseProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                header=EditText_Header.getText().toString();
                description=EditText_Description.getText().toString();
                try{
                    maxPrice=Integer.parseInt(EditText_MaxPrice.getText().toString());
                }
                catch (Exception e){
                }

                if (header.length()>0 && description.length()>0 && maxPrice>0){
                    try{
                        Proje p=new Proje(header,description,-1 ,userId,maxPrice);
                        Call<Proje> call=getData.NewProject(p);
                        call.enqueue(new Callback<Proje>() {
                            @Override
                            public void onResponse(Call<Proje> call, Response<Proje> response) {
                                Toast.makeText(ProjeOlusturmaSayfasi.this,response.toString(),Toast.LENGTH_SHORT).show();
                                Toast.makeText(ProjeOlusturmaSayfasi.this, "Yeni proje başarıyla oluşturuldu\n"+header+"\n"+description+"\n"+maxPrice, Toast.LENGTH_LONG).show();
                                EditText_Header.setText("");
                                EditText_Description.setText("");
                                EditText_MaxPrice.setText("");
                            }
                            @Override
                            public void onFailure(Call<Proje> call, Throwable t) {
                                Toast.makeText(ProjeOlusturmaSayfasi.this,"Sunucudan yanıt alınamadı",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    catch (Exception e){
                        Toast.makeText(ProjeOlusturmaSayfasi.this,"Bir sorun oluştu",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(ProjeOlusturmaSayfasi.this,"Lütfen geçerli değerler girin",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
