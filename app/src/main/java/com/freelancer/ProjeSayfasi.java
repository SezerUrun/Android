package com.freelancer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.freelancer.API.GetData;
import com.freelancer.API.RetrofitClient;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjeSayfasi extends AppCompatActivity implements View.OnClickListener{
    int projeId,userId,ownerId,workerId,maxPrice, position;
    String header,description,state,mailAdress,userName,password;
    TextView tv_projeId,projeBasligi,projeAciklamasi,projeDurumu,maxUcret,TextView_ProjectOwner;
    EditText editText_TeklifMiktari,editText_Aciklama;
    Button Button_TeklifVer, Button_TeklifiKabulEt, Button_ProjeSahibininHesabinaGit;
    GetData getData;
    List<Offer> teklifList;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proje);

        getData = RetrofitClient.getRetrofitInstance().create(GetData.class);
        tv_projeId=findViewById(R.id.TextView_ProjeIDsi);
        projeBasligi=findViewById(R.id.TextView_ProjeAdi);
        projeAciklamasi=findViewById(R.id.TextView_ProjeAciklamasi);
        projeDurumu=findViewById(R.id.TextView_ProjeDurumu);
        maxUcret=findViewById(R.id.TextView_MaxPrice);
        Button_TeklifVer=findViewById(R.id.Button_TeklifVer);
        Button_TeklifiKabulEt=findViewById(R.id.Button_TeklifiKabulEt);
        Button_ProjeSahibininHesabinaGit=findViewById(R.id.Button_ProjeSahibininHesabinaGit);
        editText_TeklifMiktari=findViewById(R.id.EditText_TeklifMiktari);
        editText_Aciklama=findViewById(R.id.EditText_Aciklama);
        TextView_ProjectOwner=findViewById(R.id.TextView_ProjectOwner);
        Button_TeklifVer.setOnClickListener(this);
        //Button_TeklifiKabulEt.setOnClickListener(this);
        Button_ProjeSahibininHesabinaGit.setOnClickListener(this);

        intent = getIntent();
        projeId=intent.getIntExtra("projectId",-1);
        header=intent.getStringExtra("header");
        description=intent.getStringExtra("description");
        state=intent.getStringExtra("state");
        maxPrice=intent.getIntExtra("price",0);
        userId=intent.getIntExtra("userId",-1);
        userName=intent.getStringExtra("userName");
        password=intent.getStringExtra("password");
        mailAdress=intent.getStringExtra("mailAdresi");
        ownerId=intent.getIntExtra("ownerId",-1);
        tv_projeId.setText(Integer.toString(projeId));
        projeBasligi.setText(header);
        projeAciklamasi.setText(description);
        projeDurumu.setText(state);
        maxUcret.setText(Integer.toString(maxPrice));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //PROJE SAHİPLERİ KENDİ PROJELERİNE TEKLİF VEREMEZ
        if(userId==ownerId){
            TextView_ProjectOwner.setText("Proje sahipleri kendi projelerine teklif veremez.");
            editText_Aciklama.setVisibility(View.INVISIBLE);
            editText_TeklifMiktari.setVisibility(View.INVISIBLE);
            Button_TeklifVer.setVisibility(View.INVISIBLE);
            Button_ProjeSahibininHesabinaGit.setVisibility(View.INVISIBLE);
        }

        /*
        try{
            Call<List<Offer>> call=getData.getOffers(projeId);
            call.enqueue(new Callback<List<Offer>>() {
                @Override
                public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                    if (userId==ownerId){
                        teklifList=response.body();
                        final String[] teklifler =new String[teklifList.size()];
                        for (int i=0;i<teklifList.size();i++){
                            teklifler[i]=Integer.toString(teklifList.get(i).getOfferPrice());
                        }
                        ListView listView= findViewById(R.id.ListView_Teklifler);
                        ArrayAdapter<String> veriAdaptoru;
                        if (userId==ownerId){
                            veriAdaptoru=new ArrayAdapter<>(ProjeSayfasi.this, R.layout.teklif_layout, R.id.textView, teklifler);
                        }
                        else{
                            veriAdaptoru=new ArrayAdapter<>(ProjeSayfasi.this, R.layout.proje_layout, R.id.textView, teklifler);
                        }
                        listView.setAdapter(veriAdaptoru);
                    }
                }
                @Override
                public void onFailure(Call<List<Offer>> call, Throwable t) {
                    Toast.makeText(ProjeSayfasi.this,"Sunucudan veri alınırken hata oluştu(onFailure)",Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch(Exception e){
            Toast.makeText(ProjeSayfasi.this,"Teklifler yüklenemedi",Toast.LENGTH_SHORT).show();
        }
        */



        final String[] teklifler =new String[10];
        for (int i=0;i<10;i++){
            teklifler[i]=Integer.toString(i*10);
        }
        ListView listView= findViewById(R.id.ListView_Teklifler);
        ArrayAdapter<String> veriAdaptoru;
        if (userId==ownerId){
            veriAdaptoru=new ArrayAdapter<>(ProjeSayfasi.this, R.layout.teklif_layout, R.id.textView, teklifler);
        }
        else{
            veriAdaptoru=new ArrayAdapter<>(ProjeSayfasi.this, R.layout.proje_layout, R.id.textView, teklifler);
        }
        listView.setAdapter(veriAdaptoru);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(view.getId()==Button_TeklifiKabulEt.getId()){
                    workerId=teklifList.get(position).getUserId();
                    Proje proje=new Proje(header,description,projeId,ownerId,workerId,maxPrice);
                    Call<Proje> call=getData.UpdateProject(projeId,proje);
                    call.enqueue(new Callback<Proje>() {
                        @Override
                        public void onResponse(Call<Proje> call, Response<Proje> response) {
                            Toast.makeText(ProjeSayfasi.this, "Teklif kabul edildi", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onFailure(Call<Proje> call, Throwable throwable) {
                            Toast.makeText(ProjeSayfasi.this, "Teklif kabul edilirken bir hata oluştu", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
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
                i.putExtra("userId",new Intent().getStringExtra("userId"));
                i.putExtra("mailAdress",new Intent().getStringExtra("mailAdress"));
                i.putExtra("userName",new Intent().getStringExtra("userName"));
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
            if (editText_TeklifMiktari.getText().toString().length()>0){
                int teklifMiktari=Integer.parseInt(editText_TeklifMiktari.getText().toString());
                String aciklama=editText_Aciklama.getText().toString();
                Offer offer =new Offer(projeId,userId,teklifMiktari,aciklama);
                Toast.makeText(ProjeSayfasi.this, "projeId: "+projeId+"\nuserId: "+userId+"\nteklifMiktari: "+teklifMiktari+"\naciklama: "+aciklama, Toast.LENGTH_LONG).show();
                try{
                    Call<Offer> call=getData.NewOffer(offer);
                    call.enqueue(new Callback<Offer>() {
                        @Override
                        public void onResponse(Call<Offer> call, Response<Offer> response) {
                            Toast.makeText(ProjeSayfasi.this, "Teklifiniz oluşturuldu\n"+response.message(), Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onFailure(Call<Offer> call, Throwable throwable) {
                            Toast.makeText(ProjeSayfasi.this, "Teklif verilirken bir hata oluştu", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                catch(Exception e){
                    Toast.makeText(ProjeSayfasi.this, "Teklif verilirken bir hata oluştu\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(ProjeSayfasi.this, "Teklif miktarı boş geçilemez", Toast.LENGTH_SHORT).show();
            }
        }
        else if(viewId==Button_ProjeSahibininHesabinaGit.getId()){
            Intent i=new Intent(ProjeSayfasi.this,UyeSayfasi.class);
            i.putExtra("userId",userId);
            i.putExtra("ownerId",ownerId);
            startActivity(i);
        }
        else if(viewId==Button_TeklifiKabulEt.getId()){

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(ProjeSayfasi.this,Anasayfa.class);
        intent.putExtra("mailAdress",mailAdress);
        intent.putExtra("userId",userId);
        intent.putExtra("userName",userName);
        intent.putExtra("password",password);
        startActivity(intent);
        super.onBackPressed();
    }
}
