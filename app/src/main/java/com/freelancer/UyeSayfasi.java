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
    int userId, ownerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uye_sayfasi);

        getData = RetrofitClient.getRetrofitInstance().create(GetData.class);
        textView_KullaniciAdi=findViewById(R.id.TextView_KullanıcıAdi);
        editText_Mesaj=findViewById(R.id.EditText_Mesaj);
        button_MesajGonder=findViewById(R.id.Button_MesajGonder);
        final String kullaniciAdi=new Intent().getStringExtra("kullaniciAdi");
        textView_KullaniciAdi.setText(kullaniciAdi);
        userId=new Intent().getIntExtra("userId",-1);
        ownerId=new Intent().getIntExtra("ownerId",-1);
        try{
            button_MesajGonder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Message message=new Message(userId,ownerId,editText_Mesaj.getText().toString());
                    Call<Boolean> call = getData.NewMessage(message);
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if (response.body()){
                                Toast.makeText(UyeSayfasi.this, "Mesaj gönderildi", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(UyeSayfasi.this, "Mesaj gönderilemedi", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Boolean> call, Throwable throwable) {
                            Toast.makeText(UyeSayfasi.this, "Veritabanına bağlanırken hata oluştu.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
        catch (Exception e){
            Toast.makeText(UyeSayfasi.this, "Bir sorun oluştu\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
