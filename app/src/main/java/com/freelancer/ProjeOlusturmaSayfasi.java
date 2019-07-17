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

public class ProjeOlusturmaSayfasi extends AppCompatActivity {
    static String mailAdress,password,userName,header,description;
    static int userId, maxPrice,credit;
    GetData getData;
    EditText EditText_Header, EditText_Description, EditText_MaxPrice;
    Button Button_ReleaseProject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proje_olusturma_sayfasi);


        getData = RetrofitClient.getRetrofitInstance().create(GetData.class);
        EditText_Header=findViewById(R.id.EditText_ProjectHeader);
        EditText_Description=findViewById(R.id.EditText_ProjectDescription);
        EditText_MaxPrice=findViewById(R.id.EditText_MaxPrice);
        Button_ReleaseProject=findViewById(R.id.Button_ReleaseProject);

        Intent intent=getIntent();
        userId=intent.getIntExtra("userId",-1);

        //EDITTEXT'E OTOMATIK ODAKLANMAYI KAPATMA
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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
                    Toast.makeText(ProjeOlusturmaSayfasi.this,"Kullanıcı bilgileri alınırken bir hata oluştu\n"+response.message(),Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ProjeOlusturmaSayfasi.this,"Sunucu ile bağlantı kurulurken bir hata oluştu\n"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

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
                                Toast.makeText(ProjeOlusturmaSayfasi.this, "Yeni proje başarıyla oluşturuldu", Toast.LENGTH_LONG).show();
                                //Toast.makeText(ProjeOlusturmaSayfasi.this,response.toString(),Toast.LENGTH_SHORT).show();
                                EditText_Header.setText("");
                                EditText_Description.setText("");
                                EditText_MaxPrice.setText("");
                            }
                            @Override
                            public void onFailure(Call<Proje> call, Throwable t) {
                                Toast.makeText(ProjeOlusturmaSayfasi.this,"Sunucudan yanıt alınamadı\n"+t.getMessage(),Toast.LENGTH_SHORT).show();
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
