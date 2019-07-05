package com.freelancer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.API.GetData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjeSayfasi extends AppCompatActivity implements View.OnClickListener{
    int projeId,userId,ownerId,workerId,maxPrice;
    String header,description,state,mailAdresi;
    TextView tv_projeId,projeBasligi,projeAciklamasi,projeDurumu,maxUcret;
    EditText editText_TeklifMiktari,editText_Aciklama;
    Button Button_TeklifVer, Button_ProjeSahibininHesabinaGit;
    GetData getData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proje);

        tv_projeId=findViewById(R.id.TextView_ProjeIDsi);
        projeBasligi=findViewById(R.id.TextView_ProjeAdi);
        projeAciklamasi=findViewById(R.id.TextView_ProjeAciklamasi);
        projeDurumu=findViewById(R.id.TextView_ProjeDurumu);
        maxUcret=findViewById(R.id.TextView_MaxPrice);
        Button_TeklifVer=findViewById(R.id.Button_TeklifVer);
        Button_ProjeSahibininHesabinaGit=findViewById(R.id.Button_ProjeSahibininHesabinaGit);
        editText_TeklifMiktari=findViewById(R.id.EditText_TeklifMiktari);
        editText_Aciklama=findViewById(R.id.EditText_Aciklama);
        Button_TeklifVer.setOnClickListener(this);

        Intent intent = getIntent();
        projeId=intent.getIntExtra("projeId",-1);
        header=intent.getStringExtra("header");
        description=intent.getStringExtra("description");
        state=intent.getStringExtra("state");
        maxPrice=intent.getIntExtra("price",0);
        userId=intent.getIntExtra("userId",-1);
        mailAdresi=intent.getStringExtra("mailAdresi");
        ownerId=intent.getIntExtra("ownerId",-1);
        tv_projeId.setText(Integer.toString(projeId));
        projeBasligi.setText(header);
        projeAciklamasi.setText(description);
        projeDurumu.setText(state);
        maxUcret.setText(Integer.toString(maxPrice));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        /*
        Call<List<Offer>> call=getData.getOffers(projeId);
        call.enqueue(new Callback<List<Offer>>() {
            @Override
            public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                if (kullaniciId==ownnerId){
                    List<Offer> list=response.body();
                    final String[] teklifler =new String[list.size()];
                    for (int i=0;i<list.size();i++){
                        teklifler[i]=Integer.toString(list.get(i).getOfferPrice());
                    }
                    ListView listView= findViewById(R.id.ListView_Teklifler);
                    ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<>(ProjeSayfasi.this, android.R.layout.simple_list_item_1, R.id.textView, teklifler);
                    listView.setAdapter(veriAdaptoru);
                }
            }
            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {

            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profil:
                Intent i=new Intent(ProjeSayfasi.this, Profil.class);
                i.putExtra("id",new Intent().getStringExtra("id"));
                i.putExtra("mailAdresi",new Intent().getStringExtra("mailAdresi"));
                i.putExtra("kullaniciAdi",new Intent().getStringExtra("kullaniciAdi"));
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        int viewId=view.getId();
        if (viewId==Button_TeklifVer.getId()){
            int teklifMiktari=Integer.parseInt(editText_TeklifMiktari.getText().toString());
            String aciklama=editText_Aciklama.getText().toString();
            Offer offer =new Offer(projeId,userId,teklifMiktari,aciklama);
            Call<Offer> call=getData.NewOffer(offer);
            call.enqueue(new Callback<Offer>() {
                @Override
                public void onResponse(Call<Offer> call, Response<Offer> response) {
                    Toast.makeText(ProjeSayfasi.this, "Teklifiniz oluşturuldu", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<Offer> call, Throwable throwable) {
                    Toast.makeText(ProjeSayfasi.this, "Teklif verilirken bir hata oluştu", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(viewId==Button_ProjeSahibininHesabinaGit.getId()){
            Intent i=new Intent(ProjeSayfasi.this,UyeSayfasi.class);
            i.putExtra("userId",userId);
            i.putExtra("ownerId",ownerId);
            startActivity(i);
        }
    }
}
