package com.freelancer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.API.GetData;
import com.freelancer.API.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeklifSayfasi extends AppCompatActivity {

    TextView  TextView_OfferOwnerName,textView_OfferPrice,textView_OfferDesciption;
    Button Button_AcceptTheOffer,Button_MessageToOfferOwner;
    GetData getData;
    String offerOwnerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teklif_sayfasi);

        getData = RetrofitClient.getRetrofitInstance().create(GetData.class);
        TextView_OfferOwnerName=findViewById(R.id.TextView_OfferOwnerName);
        textView_OfferPrice=findViewById(R.id.TextView_OfferPrice);
        textView_OfferDesciption=findViewById(R.id.TextView_OfferDescription);
        Button_AcceptTheOffer=findViewById(R.id.Button_AcceptTheOffer);
        Button_MessageToOfferOwner=findViewById(R.id.Button_MessageToOfferOwner);

        Intent intent=getIntent();
        String userName=intent.getStringExtra("userName");
        String password=intent.getStringExtra("password");
        final int offerId=intent.getIntExtra("offerId",-1);
        final int offerOwnerId=intent.getIntExtra("offerOwnerId",-1);
        final int offerPrice=intent.getIntExtra("offerPrice",-1);
        String offerDescription=intent.getStringExtra("offerDescription");
        final int projectId=intent.getIntExtra("projectId",-1);
        final int userId=intent.getIntExtra("userId",-1);
        final String header=intent.getStringExtra("header");
        final String description=intent.getStringExtra("description");
        final int projectOwnerId=intent.getIntExtra("ownerId",-1);
        final int maxPrice=intent.getIntExtra("maxprice",-1);
        final String releaseTime=intent.getStringExtra("releaseTime");
        final String deadLine=intent.getStringExtra("deadLine");
        int workerId=intent.getIntExtra("workerId",-1);

        Call<User> call = getData.getUser(offerOwnerId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                offerOwnerName=response.body().getName();
                TextView_OfferOwnerName.setText(TextView_OfferOwnerName.getText()+offerOwnerName);
            }
            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                Toast.makeText(TeklifSayfasi.this,"Sunucudan bilgi alınıken bir hata oluştu",Toast.LENGTH_SHORT).show();
            }
        });


        textView_OfferPrice.setText(textView_OfferPrice.getText()+Integer.toString(offerPrice));
        textView_OfferDesciption.setText(textView_OfferDesciption.getText()+offerDescription);
        if (workerId==-1){

        }
        Button_AcceptTheOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Offer offer= new Offer(projectId,offerOwnerId,offerPrice,description);
                Call<Boolean> call = getData.AcceptOffer(offer);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        Toast.makeText(TeklifSayfasi.this,"Teklif kabul edildi\n"+response.message(),Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Toast.makeText(TeklifSayfasi.this,"Bir hata oluştu\n"+t.getMessage(),Toast.LENGTH_SHORT).show();
                        call.cancel();
                    }
                });
            }
        });
        Button_MessageToOfferOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(TeklifSayfasi.this,MesajlasmaSayfasi.class);
                i.putExtra("senderId",userId);
                i.putExtra("receiverId",offerOwnerId);
                i.putExtra("yanitMi","degil");
                startActivity(i);
            }
        });

    }
}
