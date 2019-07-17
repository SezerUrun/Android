package com.freelancer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.API.GetData;
import com.freelancer.API.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Messages extends AppCompatActivity {
    ListView listView_Mesajlarim;
    GetData getData;
    static int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        getData = RetrofitClient.getRetrofitInstance().create(GetData.class);
        Intent intent=getIntent();
        userId=intent.getIntExtra("userId",-1);
        listView_Mesajlarim=findViewById(R.id.ListView_Mesajlarim);

        try{
            Call<List<Message>> call = getData.getMessages(userId);
            call.enqueue(new Callback<List<Message>>() {
                @Override
                public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                    final List<Message> list=response.body();
                    if (list!=null){
                        final String[] messages =new String[list.size()];
                        for (int i=0;i<list.size();i++){
                            messages[i]=list.get(i).getContent();
                        }
                        ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<>(Messages.this, R.layout.satir_layout, R.id.textView, messages);
                        listView_Mesajlarim.setAdapter(veriAdaptoru);
                        listView_Mesajlarim.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent i=new Intent(Messages.this,MesajlasmaSayfasi.class);
                                i.putExtra("content",list.get(position).getContent());
                                i.putExtra("receiverId",list.get(position).getSenderId());
                                i.putExtra("senderId",list.get(position).getReceiverId());
                                i.putExtra("userId",userId);
                                i.putExtra("yanitMi","yanit");
                                startActivity(i);
                            }
                        });
                    }
                }
                @Override
                public void onFailure(Call<List<Message>> call, Throwable throwable) {
                    Toast.makeText(Messages.this, "Mesajlar yüklenemedi.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch(Exception e){
            Toast.makeText(this, "Mesajlar yüklenirken bir hata oluştu.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
