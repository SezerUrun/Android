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
import android.widget.LinearLayout;
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
    static int projectId,userId,projectOwnerId,workerId,maxPrice, position, odemeMiktari, receiverId,offerOwnerId;
    static String header,description,state,mailAdress,userName,password,releaseTime,deadLine;
    TextView tv_projeId,projeBasligi,projeAciklamasi,maxUcret,TextView_ProjectOwner, TextView_Teklifler,TextView_ReleaseTime,TextView_DeadLine;
    EditText editText_TeklifMiktari,editText_Aciklama;
    Button Button_TeklifVer, Button_DeleteProject, Button_ProjeSahibinineMesajGonder,Button_ProjeTamamlandi;
    GetData getData;
    List<Offer> teklifList;
    Intent intent;
    ListView listView;
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proje);

        getData = RetrofitClient.getRetrofitInstance().create(GetData.class);
        layout=findViewById(R.id.layout);
        TextView_Teklifler=findViewById(R.id.TextView_Teklifler);
        tv_projeId=findViewById(R.id.TextView_ProjeIDsi);
        projeBasligi=findViewById(R.id.TextView_ProjeAdi);
        projeAciklamasi=findViewById(R.id.TextView_ProjeAciklamasi);
        maxUcret=findViewById(R.id.TextView_MaxPrice);
        Button_TeklifVer=findViewById(R.id.Button_TeklifVer);
        Button_ProjeSahibinineMesajGonder=findViewById(R.id.Button_ProjeSahibinineMesajGonder);
        Button_ProjeTamamlandi=findViewById(R.id.Button_ProjeTamamlandi);
        Button_DeleteProject=findViewById(R.id.Button_DeleteProject);
        editText_TeklifMiktari=findViewById(R.id.EditText_TeklifMiktari);
        editText_Aciklama=findViewById(R.id.EditText_Aciklama);
        TextView_ProjectOwner=findViewById(R.id.TextView_ProjectOwner);
        TextView_ReleaseTime=findViewById(R.id.TextView_ReleaseTime);
        TextView_DeadLine=findViewById(R.id.TextView_DeadLine);
        listView= findViewById(R.id.ListView_Teklifler);
        //Button_TeklifiKabulEt.setOnClickListener(this);
        Button_TeklifVer.setOnClickListener(this);
        Button_ProjeSahibinineMesajGonder.setOnClickListener(this);
        Button_ProjeTamamlandi.setOnClickListener(this);
        Button_DeleteProject.setOnClickListener(this);

        //EDITTEXT'E OTOMATIK ODAKLANMAYI KAPATMA
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        intent = getIntent();
        projectId=intent.getIntExtra("projectId",-1);
        header=intent.getStringExtra("header");
        description=intent.getStringExtra("description");
        state=intent.getStringExtra("state");
        maxPrice=intent.getIntExtra("maxPrice",0);
        userId=intent.getIntExtra("userId",-1);
        projectOwnerId=intent.getIntExtra("ownerId",-1);
        workerId=intent.getIntExtra("workerId",-1);
        releaseTime=intent.getStringExtra("releaseTime");
        deadLine=intent.getStringExtra("deadLine");
        offerOwnerId=intent.getIntExtra("offerOwnerId",offerOwnerId);

        Call<User> call = getData.getUser(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body()!=null){
                    mailAdress=response.body().getMail();
                    userName=response.body().getName();
                    password=response.body().getPassword();
                }
                else{
                    Toast.makeText(ProjeSayfasi.this,"Kullanıcı bilgileri alınırken bir hata oluştu\n"+response.message(),Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ProjeSayfasi.this,"Sunucu ile bağlantı kurulurken bir hata oluştu\n"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        tv_projeId.setText(tv_projeId.getText()+Integer.toString(projectId));
        projeBasligi.setText(projeBasligi.getText()+header);
        projeAciklamasi.setText(projeAciklamasi.getText()+description);
        maxUcret.setText(maxUcret.getText()+Integer.toString(maxPrice));
        TextView_ReleaseTime.setText(TextView_ReleaseTime.getText()+releaseTime);
        TextView_DeadLine.setText(TextView_DeadLine.getText()+deadLine);


        //PROJE SAHİPLERİ KENDİ PROJELERİNE TEKLİF VEREMEZ
        if(userId==projectOwnerId || userId==workerId){
            layout.removeView(editText_Aciklama);
            layout.removeView(editText_TeklifMiktari);
            layout.removeView(Button_TeklifVer);
            layout.removeView(Button_ProjeSahibinineMesajGonder);
            if (userId==projectOwnerId){
                TextView_ProjectOwner.setText("Proje sahipleri kendi projelerine teklif veremez.");
                Button_ProjeTamamlandi.setVisibility(View.VISIBLE);
                if(workerId==-1){
                    layout.removeView(Button_ProjeTamamlandi);
                }
                Button_DeleteProject.setVisibility(View.VISIBLE);
            }
            else if(userId==workerId){
                TextView_ProjectOwner.setText("Bu proje için çalışıyorsunuz");
                layout.removeView(Button_DeleteProject);
            }
        }
        else{
            layout.removeView(TextView_ProjectOwner);
            layout.removeView(Button_ProjeTamamlandi);
            layout.removeView(Button_DeleteProject);
        }

        //TEKLİF SAYFASINA GİTME
        if (workerId==-1){
            try{
                Call<List<Offer>> call2=getData.getOffers(projectId);
                call2.enqueue(new Callback<List<Offer>>() {
                    @Override
                    public void onResponse(Call<List<Offer>> call2, Response<List<Offer>> response) {
                        teklifList=response.body();
                        if (teklifList.size()>0){
                            if (userId==projectOwnerId){
                                final String[] teklifler =new String[teklifList.size()];
                                for (int i=0;i<teklifList.size();i++){
                                    teklifler[i]=Integer.toString(teklifList.get(i).getOfferPrice());
                                }
                                ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<>(ProjeSayfasi.this, R.layout.satir_layout, R.id.textView, teklifler);
                                listView.setAdapter(veriAdaptoru);

                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        intent=new Intent(ProjeSayfasi.this,TeklifSayfasi.class);
                                        intent.putExtra("offerId",teklifList.get(position).getId());
                                        intent.putExtra("offerPrice",teklifList.get(position).getOfferPrice());
                                        intent.putExtra("offerDescription",teklifList.get(position).getOfferDescription());
                                        intent.putExtra("offerOwnerId",teklifList.get(position).getUserId());
                                        intent.putExtra("projectId",projectId);
                                        intent.putExtra("header",header);
                                        intent.putExtra("description",description);
                                        intent.putExtra("maxPrice",maxPrice);
                                        intent.putExtra("ownerId",projectOwnerId);
                                        intent.putExtra("releaseTime",releaseTime);
                                        intent.putExtra("deadLine",deadLine);
                                        intent.putExtra("workerId",workerId);
                                        intent.putExtra("userId",userId);
                                        startActivity(intent);
                                    }
                                });
                            }
                            else{
                                int enDusuk=teklifList.get(0).getOfferPrice();
                                for(int i=0;i<teklifList.size();i++){
                                    if(teklifList.get(i).getOfferPrice()<enDusuk){
                                        enDusuk=teklifList.get(i).getOfferPrice();
                                    }
                                }
                                TextView_Teklifler.setText("En düşük teklif : "+enDusuk);
                            }
                        }
                        else{
                            TextView_Teklifler.setText("Bu projeye henüz kimse teklif vermedi.");
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Offer>> call2, Throwable t) {
                        Toast.makeText(ProjeSayfasi.this,"Veriler sunucudan alınamadı",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            catch(Exception e){
                Toast.makeText(ProjeSayfasi.this,"Bir hata oluştu",Toast.LENGTH_SHORT).show();
            }
        }

        /*
        final String[] teklifler =new String[10];
        for(int i=0;i<10;i++){
            teklifler[i]=Integer.toString(i+10);
        }
        ArrayAdapter<String> veriAdaptoru;
        veriAdaptoru=new ArrayAdapter<>(ProjeSayfasi.this, R.layout.satir_layout, R.id.textView, teklifler);
        listView.setAdapter(veriAdaptoru);*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.action_profil:
                i=new Intent(ProjeSayfasi.this, Profil.class);
                i.putExtra("userId",userId);
                i.putExtra("mailAdress",mailAdress);
                i.putExtra("userName",userName);
                i.putExtra("password",password);
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
                Offer offer =new Offer(projectId,userId,teklifMiktari,aciklama);
                //Toast.makeText(ProjeSayfasi.this, "projeId: "+projectId+"\nuserId: "+userId+"\nteklifMiktari: "+teklifMiktari+"\naciklama: "+aciklama, Toast.LENGTH_LONG).show();
                try{
                    Call<Offer> call=getData.NewOffer(offer);
                    call.enqueue(new Callback<Offer>() {
                        @Override
                        public void onResponse(Call<Offer> call, Response<Offer> response) {
                            if (response.message().equals("Created")){
                                editText_TeklifMiktari.setText("");
                                editText_Aciklama.setText("");
                                Toast.makeText(ProjeSayfasi.this, "Teklifiniz oluşturuldu", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(ProjeSayfasi.this, "Teklif verilirken bir hata oluştu", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Offer> call, Throwable throwable) {
                            Toast.makeText(ProjeSayfasi.this, "Sunucu ile bağlantıda bir hata oluştu", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                catch(Exception e){
                    Toast.makeText(ProjeSayfasi.this, "Bir hata oluştu\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(ProjeSayfasi.this, "Teklif miktarı boş geçilemez", Toast.LENGTH_SHORT).show();
            }
        }
        else if(viewId==Button_ProjeSahibinineMesajGonder.getId()){
            Intent i=new Intent(ProjeSayfasi.this,MesajlasmaSayfasi.class);
            i.putExtra("senderId",userId);
            i.putExtra("receiverId",projectOwnerId);
            i.putExtra("yanitMi","degil");
            startActivity(i);
        }
        else if(viewId==Button_ProjeTamamlandi.getId()){

            if (userId==projectOwnerId){
                Call<Proje> call = getData.SetCompletedOwner(projectId);
                call.enqueue(new Callback<Proje>() {
                    @Override
                    public void onResponse(Call<Proje> call, Response<Proje> response) {
                        //Toast.makeText(Profil.this,"Proje tamamlandı ve bakiye aktarıldı",Toast.LENGTH_SHORT).show();
                        Toast.makeText(ProjeSayfasi.this,response.message(),Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<Proje> call, Throwable t) {
                        Toast.makeText(ProjeSayfasi.this,"Bir hata oluştu\n"+t.getMessage(),Toast.LENGTH_SHORT).show();
                        call.cancel();
                    }
                });
                /*
                Proje proje = new Proje(header,description,projectId,projectOwnerId,maxPrice);
                proje.setWorkerId(workerId);
                proje.setReleaseTime(releaseTime);
                proje.setDeadline(deadLine);
                proje.setCompletedOwner(true);
                Call<Proje> call = getData.UpdateProject(proje);
                call.enqueue(new Callback<Proje>() {
                    @Override
                    public void onResponse(Call<Proje> call, Response<Proje> response) {
                        Toast.makeText(ProjeSayfasi.this,"Teklif kabul edildi\n"+response.message(),Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<Proje> call, Throwable t) {
                        Toast.makeText(ProjeSayfasi.this,"Bir hata oluştu\n"+t.getMessage(),Toast.LENGTH_SHORT).show();
                        call.cancel();
                    }
                });*/
            }
            else if(userId==workerId){
                Call<Proje> call = getData.SetCompletedWorker(projectId);
                call.enqueue(new Callback<Proje>() {
                    @Override
                    public void onResponse(Call<Proje> call, Response<Proje> response) {
                        Toast.makeText(ProjeSayfasi.this,response.message(),Toast.LENGTH_SHORT).show();
                        //Toast.makeText(Profil.this,"Teklif kabul edildi",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<Proje> call, Throwable t) {
                        Toast.makeText(ProjeSayfasi.this,"Bir hata oluştu\n"+t.getMessage(),Toast.LENGTH_SHORT).show();
                        call.cancel();
                    }
                });
                /*
                Proje proje = new Proje(header,description,projectId,projectOwnerId,maxPrice);
                proje.setWorkerId(workerId);
                proje.setReleaseTime(releaseTime);
                proje.setDeadline(deadLine);
                proje.setCompletedWorker(true);
                Call<Proje> call = getData.UpdateProject(proje);
                call.enqueue(new Callback<Proje>() {
                    @Override
                    public void onResponse(Call<Proje> call, Response<Proje> response) {
                        Toast.makeText(ProjeSayfasi.this,"Teklif kabul edildi\n"+response.message(),Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<Proje> call, Throwable t) {
                        Toast.makeText(ProjeSayfasi.this,"Bir hata oluştu\n"+t.getMessage(),Toast.LENGTH_SHORT).show();
                        call.cancel();
                    }
                });*/
            }
            /*
            Call<Proje> call = getData.UpdateProject(proje);
            call.enqueue(new Callback<Proje>() {
                @Override
                public void onResponse(Call<Proje> call, Response<Proje> response) {
                    Toast.makeText(ProjeSayfasi.this,response.message(),Toast.LENGTH_SHORT).show();
                    //Toast.makeText(Profil.this,"Teklif kabul edildi",Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<Proje> call, Throwable t) {
                    Toast.makeText(ProjeSayfasi.this,"Bir hata oluştu",Toast.LENGTH_SHORT).show();
                    call.cancel();
                }
            });*/

        }
        else if (viewId==Button_DeleteProject.getId()){
            Call<String> call = getData.DeleteProject(projectId);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(ProjeSayfasi.this,"Proje Silindi\n"+response.message(),Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(ProjeSayfasi.this,"Bir hata oluştu\n"+t.getMessage(),Toast.LENGTH_SHORT).show();
                    call.cancel();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        /*Intent intent= new Intent(ProjeSayfasi.this,Anasayfa.class);
        intent.putExtra("mailAdress",mailAdress);
        intent.putExtra("userId",userId);
        intent.putExtra("userName",userName);
        intent.putExtra("password",password);
        startActivity(intent);*/
        super.onBackPressed();
    }
}
