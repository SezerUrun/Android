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

public class MesajlasmaSayfasi extends AppCompatActivity {
    TextView textView_Gonderici, textView_Alici, textView_MesajIcerigi;
    EditText editText_Mesaj;
    Button button_Gonder;
    GetData getData;
    String mailAdress,userName,password;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesajlasma_sayfasi);

        Intent intent=getIntent();
        mailAdress=intent.getStringExtra("mailAdress");
        userName=intent.getStringExtra("userName");
        userId=intent.getIntExtra("userId",-1);
        password=intent.getStringExtra("password");

        getData = RetrofitClient.getRetrofitInstance().create(GetData.class);
        textView_Gonderici=findViewById(R.id.TextView_Gonderici);
        textView_Alici=findViewById(R.id.TextView_Alici);
        editText_Mesaj=findViewById(R.id.EditText_Mesaj);
        button_Gonder=findViewById(R.id.Button_MesajGonder);

        Intent i =  getIntent();
        final int senderId=i.getIntExtra("senderId",-1);
        final int receiverId=i.getIntExtra("receiverId",-1);
        String content= i.getStringExtra("content");

        textView_Gonderici.setText(textView_Gonderici.getText()+Integer.toString(senderId));
        textView_Alici.setText(textView_Alici.getText()+Integer.toString(receiverId));
        textView_MesajIcerigi.setText(textView_MesajIcerigi.getText()+content);

        button_Gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message=new Message(senderId,receiverId,editText_Mesaj.getText().toString());
                try{
                    Call<Boolean> call = getData.NewMessage(message);
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            Toast.makeText(MesajlasmaSayfasi.this, "Mesajınız başarıyla gönderildi.", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onFailure(Call<Boolean> call, Throwable throwable) {
                            Toast.makeText(MesajlasmaSayfasi.this, "Mesaj gönderilirken bir hata oluştu.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                catch(Exception e){
                    Toast.makeText(MesajlasmaSayfasi.this, "Mesaj gönderilirken bir hata oluştu.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent intent= new Intent(MesajlasmaSayfasi.this,UyeSayfasi.class);
        intent.putExtra("mailAdress",mailAdress);
        intent.putExtra("userId",userId);
        intent.putExtra("userName",userName);
        intent.putExtra("password",password);
        startActivity(intent);
        super.onBackPressed();
    }

}
