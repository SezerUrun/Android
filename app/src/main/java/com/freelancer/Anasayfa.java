package com.freelancer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.API.GetData;
import com.freelancer.API.RetrofitClient;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Anasayfa extends AppCompatActivity{
    GetData getData;
    Button newProject;
    ListView listView;
    TextView tv;
    static int projectId, userId,credit;
    static String mailAdress, userName,password,header,description;
    List<Proje> list;
    Intent intent;
    //TextViews tvs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);

        getData = RetrofitClient.getRetrofitInstance().create(GetData.class);
        newProject=findViewById(R.id.Button_NewProject);
        intent=getIntent();
        userId=intent.getIntExtra("userId",-1);

        //KULLANICI BİLGİLERİNİN ALINMASI
        Call<User> call = getData.getUser(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body()!=null){
                    mailAdress=response.body().getMail();
                    userName=response.body().getName();
                    password=response.body().getPassword();
                    credit=response.body().getCredit();
                    //Toast.makeText(Anasayfa.this,mailAdress+"\n"+userName+"\n"+password+"\n"+credit,Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Anasayfa.this,"Kullanıcı bilgileri alınırken bir hata oluştu\n"+response.message(),Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(Anasayfa.this,"Sunucu ile bağlantı kurulurken bir hata oluştu\n"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        try{
            Call<List<Proje>> call2 = getData.getProjects();
            call2.enqueue(new Callback<List<Proje>>() {
                @Override
                public void onResponse(Call<List<Proje>> call2, Response<List<Proje>> response) {
                    list=response.body();
                    list=reverseList(list);
                    if(list!=null){
                        final String[] projeler =new String[list.size()];
                        for (int i=0;i<list.size();i++){
                            projeler[i]=list.get(i).getHeader();
                        }
                        ListView listView= findViewById(R.id.listView);
                        ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<>(Anasayfa.this, R.layout.satir_layout, R.id.textView, projeler);
                        listView.setAdapter(veriAdaptoru);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                intent =new Intent(Anasayfa.this,ProjeSayfasi.class);
                                intent.putExtra("userId",userId);
                                intent.putExtra("header",list.get(position).getHeader());
                                intent.putExtra("description",list.get(position).getDescription());
                                intent.putExtra("projectId",list.get(position).getId());
                                intent.putExtra("maxPrice",list.get(position).getMaxPrice());
                                intent.putExtra("ownerId",list.get(position).getOwnerId());
                                intent.putExtra("releaseTime",list.get(position).getReleaseTime());
                                intent.putExtra("deadLine",list.get(position).getDeadline());
                                startActivity(intent);
                            }
                        });
                    }

                }
                @Override
                public void onFailure(Call<List<Proje>> call2, Throwable throwable) {
                    //Toast.makeText(Anasayfa.this, "Projeler yüklenemedi.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch(Exception e){
            //Toast.makeText(this, "Projeler yüklenirken bir hata oluştu.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

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
                Intent intent=new Intent(Anasayfa.this, Profil.class);
                intent.putExtra("userId",userId);
                intent.putExtra("mailAdress",mailAdress);
                intent.putExtra("userName",userName);
                intent.putExtra("password",password);
                intent.putExtra("credit",credit);
                startActivity(intent);
                return true;
            case R.id.action_refresh:
                try{
                    Call<List<Proje>> call2 = getData.getProjects();
                    call2.enqueue(new Callback<List<Proje>>() {
                        @Override
                        public void onResponse(Call<List<Proje>> call2, Response<List<Proje>> response) {
                            list=response.body();
                            list=reverseList(list);
                            if(list!=null){
                                final String[] projeler =new String[list.size()];
                                for (int i=0;i<list.size();i++){
                                    projeler[i]=list.get(i).getHeader();
                                }
                                ListView listView= findViewById(R.id.listView);
                                ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<>(Anasayfa.this, R.layout.satir_layout, R.id.textView, projeler);
                                listView.setAdapter(veriAdaptoru);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent =new Intent(Anasayfa.this,ProjeSayfasi.class);
                                        intent.putExtra("userId",userId);
                                        intent.putExtra("header",list.get(position).getHeader());
                                        intent.putExtra("description",list.get(position).getDescription());
                                        intent.putExtra("projectId",list.get(position).getId());
                                        intent.putExtra("maxPrice",list.get(position).getMaxPrice());
                                        intent.putExtra("ownerId",list.get(position).getOwnerId());
                                        intent.putExtra("releaseTime",list.get(position).getReleaseTime());
                                        intent.putExtra("deadLine",list.get(position).getDeadline());
                                        startActivity(intent);
                                    }
                                });
                            }

                        }
                        @Override
                        public void onFailure(Call<List<Proje>> call2, Throwable throwable) {
                            Toast.makeText(Anasayfa.this, "Projeler yüklenirken bir hata oluştu", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                catch(Exception e){
                    Toast.makeText(this, "Bir hata oluştu.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    List<Proje> reverseList(List<Proje> list){
        LinkedList<Proje> newList=new LinkedList<>();
        for(int i=list.size()-1;i>=0;i--){
            newList.add(list.get(i));
        }
        return newList;
    }

}

