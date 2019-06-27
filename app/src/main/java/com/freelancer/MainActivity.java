package com.freelancer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView EditText_Email,EditText_Sifre;
    Button Button_Giris,Button_Kaydol;
    String email,sifre,kullaniciAdi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email="sezer_urun@outlook.com";
        sifre="1234";
        EditText_Email=findViewById(R.id.EditText_Email);
        EditText_Sifre=findViewById(R.id.EditText_Sifre);
        Button_Giris=findViewById(R.id.Button_Giris);
        Button_Kaydol=findViewById(R.id.Button_Kaydol);
        Button_Giris.setOnClickListener(this);
        Button_Kaydol.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id=view.getId();
        if (id==Button_Giris.getId()){
            if (EditText_Email.getText().toString().equals(email) && EditText_Sifre.getText().toString().equals(sifre)){
                Toast.makeText(MainActivity.this,"Giriş Başarılı",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(MainActivity.this,Anasayfa.class);
                intent.putExtra("KullaniciAdi",kullaniciAdi);
                startActivity(intent);
            }
            else{
                Toast.makeText(MainActivity.this,"Giriş Başarılı Değil\nEmail adresi veya şifre yanlış",Toast.LENGTH_SHORT).show();
            }
        }
        else if(id==Button_Kaydol.getId()){

        }
    }
}
