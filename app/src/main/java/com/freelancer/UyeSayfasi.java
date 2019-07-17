package com.freelancer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.API.GetData;
import com.freelancer.API.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UyeSayfasi extends AppCompatActivity {
    TextView textView_KullaniciAdi;
    EditText editText_Mesaj;
    Button button_MesajGonder;
    GetData getData;
    static int userId, ownerId;
    static String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uye_sayfasi);

        getData = RetrofitClient.getRetrofitInstance().create(GetData.class);
        textView_KullaniciAdi=findViewById(R.id.TextView_KullanıcıAdi);
        editText_Mesaj=findViewById(R.id.EditText_Mesaj);
        button_MesajGonder=findViewById(R.id.Button_MesajGonder);

        Intent intent=getIntent();
        userId=intent.getIntExtra("userId",-1);
        ownerId=intent.getIntExtra("ownerId",-1);

        Call<User> call = getData.getUser(ownerId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body()!=null){
                    userName=response.body().getName();
                    textView_KullaniciAdi.setText(textView_KullaniciAdi.getText()+userName);
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(UyeSayfasi.this,"Kullanici bilgileri alirken bir hata olustu",Toast.LENGTH_SHORT).show();
            }
        });



        try{
            button_MesajGonder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*
                    Message message=new Message(userId,ownerId,editText_Mesaj.getText().toString());
                    Call<Message> call = getData.NewMessage(message);
                    call.enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            if (response.body().getContent().length()!=0){
                                Toast.makeText(UyeSayfasi.this, "Mesaj gönderildi", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(UyeSayfasi.this, "Mesaj gönderilemedi", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Message> call, Throwable throwable) {
                            Toast.makeText(UyeSayfasi.this, "Veritabanına bağlanırken hata oluştu\n"+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });*/
                }
            });
        }
        catch (Exception e){
            Toast.makeText(UyeSayfasi.this, "Bir sorun oluştu\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
