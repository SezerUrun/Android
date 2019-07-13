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

    TextView textView_Gonderici, textView_Alici, textView_Mesaj;
    EditText editText_Mesaj;
    Button button_Gonder;
    GetData getData;
    String mailAdress,userName,password,senderName,receiverName;
    int userId;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesajlasma_sayfasi);

        intent=getIntent();
        mailAdress=intent.getStringExtra("mailAdress");
        userName=intent.getStringExtra("userName");
        userId=intent.getIntExtra("userId",-1);
        password=intent.getStringExtra("password");

        getData = RetrofitClient.getRetrofitInstance().create(GetData.class);
        textView_Gonderici=findViewById(R.id.TextView_Gonderici);
        textView_Alici=findViewById(R.id.TextView_Alici);
        textView_Mesaj=findViewById(R.id.TextView_MesajIcerigi);
        editText_Mesaj=findViewById(R.id.EditText_Mesaj);
        button_Gonder=findViewById(R.id.Button_MesajGonder);

        intent =  getIntent();
        final int senderId=intent.getIntExtra("senderId",-1);
        final int receiverId=intent.getIntExtra("receiverId",-1);
        String content="";
        try{
            content= intent.getStringExtra("content");
        }
        catch(Exception e){
        }

        Call<User> call = getData.getUser(senderId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(MesajlasmaSayfasi.this,response.message(),Toast.LENGTH_SHORT).show();
                senderName=response.body().getName();
            }
            @Override
            public void onFailure(Call<User> call, Throwable throwable) {

            }
        });
        Call<User> call2 = getData.getUser(receiverId);
        call2.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call2, Response<User> response) {
                Toast.makeText(MesajlasmaSayfasi.this,response.message(),Toast.LENGTH_SHORT).show();
                receiverName=response.body().getName();
            }
            @Override
            public void onFailure(Call<User> call2, Throwable throwable) {

            }
        });

        intent=getIntent();
        if (intent.getStringExtra("yanitMi").equals("yanit")){
            textView_Gonderici.setText("Gönderen : "+senderName);
            textView_Mesaj.setText("Mesaj İçeriği : " +content);
        }
        else{
            textView_Alici.setText("Alıcı : "+receiverName);
            textView_Mesaj.setText("");
        }


        button_Gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message=new Message(senderId,receiverId,editText_Mesaj.getText().toString());
                try{
                    Call<Message> call = getData.NewMessage(message);
                    call.enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            Toast.makeText(MesajlasmaSayfasi.this, "Mesajınız başarıyla gönderildi\n"+response.message(), Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onFailure(Call<Message> call, Throwable throwable) {
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
        /*Intent intent= new Intent(MesajlasmaSayfasi.this,UyeSayfasi.class);
        intent.putExtra("mailAdress",mailAdress);
        intent.putExtra("userId",userId);
        intent.putExtra("userName",userName);
        intent.putExtra("password",password);
        startActivity(intent);*/
        super.onBackPressed();
    }

}
