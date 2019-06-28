package com.freelancer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.freelancer.API.GetData;
import com.freelancer.API.RetrofitClient;
import com.freelancer.API.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView EditText_Email,EditText_Sifre;
    Button Button_Giris,Button_Kaydol;
    String email,sifre;
    GetData getData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData = RetrofitClient.getRetrofitInstance().create(GetData.class);
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
            email="a";
            sifre="b";
            if (EditText_Email.getText().toString().equals(email) && EditText_Sifre.getText().toString().equals(sifre)){
                Toast.makeText(MainActivity.this,"Giriş Başarılı",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(MainActivity.this,Anasayfa.class);
                intent.putExtra("eMail",email);
                startActivity(intent);
            }
            else{
                Toast.makeText(MainActivity.this,"Giriş Başarılı Değil\nEmail adresi veya şifre yanlış",Toast.LENGTH_SHORT).show();
            }
        }
        else if(id==Button_Kaydol.getId()){

            //YENİ KULLANICI OLUŞTURMA
            email=EditText_Email.getText().toString();
            sifre=EditText_Sifre.getText().toString();
            User user = new User(0,"",email,sifre,0);
            User user2=new User(email,sifre);
            Call<User> call = getData.NewUser(user);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    //Toast.makeText(MainActivity.this,response.code(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this,"Yeni kullanıcı oluşturuldu\nŞimdi giriş yapabilirsiniz",Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), t.getStackTrace().toString(), Toast.LENGTH_SHORT).show();
                    call.cancel();
                }
            });
        }
    }
}
