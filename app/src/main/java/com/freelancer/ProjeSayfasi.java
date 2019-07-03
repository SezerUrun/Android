package com.freelancer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ProjeSayfasi extends AppCompatActivity implements View.OnClickListener{
    int projeId,kullaniciId,cost,ownnerId,workerId,maxPrice;
    String header,description,state;
    TextView tv_projeId,projeBasligi,projeAciklamasi,projeDurumu,maxUcret;
    Button Button_TeklifVer;
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

        Intent intent = getIntent();
        projeId=intent.getIntExtra("id",-1);
        header=intent.getStringExtra("header");
        description=intent.getStringExtra("description");
        state=intent.getStringExtra("state");
        maxPrice=intent.getIntExtra("price",0);
        tv_projeId.setText(Integer.toString(projeId));
        projeBasligi.setText(header);
        projeAciklamasi.setText(description);
        projeDurumu.setText(state);
        maxUcret.setText(Integer.toString(maxPrice));
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
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==Button_TeklifVer.getId()){
            /*Intent i=new Intent();
            String email=i.getStringExtra("email");
            int teklif;
            Call<>
            */
        }
    }
}
