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

public class Anasayfa extends AppCompatActivity implements View.OnClickListener{
    GetData getData;
    Button newProject;
    ListView listView;
    TextView tv;
    int projectId, userId;
    String mailAdresi, userName,password,header,description;
    LinearLayout ly;
    List<Proje> list;
    Intent intent,intent2;
    //TextViews tvs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);

        ly=findViewById(R.id.LinearLayout);
        getData = RetrofitClient.getRetrofitInstance().create(GetData.class);
        newProject=findViewById(R.id.Button_NewProject);
        intent=new Intent(Anasayfa.this,ProjeSayfasi.class);
        intent2 = getIntent();
        userId=intent2.getIntExtra("userId",-1);
        mailAdresi=intent2.getStringExtra("mailAdress");
        userName=intent2.getStringExtra("userName");
        password=intent2.getStringExtra("password");
        header=intent2.getStringExtra("header");
        description=intent2.getStringExtra("description");
        //tv=findViewById(R.id.TextView);
        //tvs=new TextViews();

        /*
        final String[] projeler =new String[10];
        for (int i=0;i<10;i++){
            projeler[i]="DenemeProje"+i;
        }
        ListView listView= findViewById(R.id.listView);
        ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<>(Anasayfa.this, R.layout.proje_layout, R.id.textView, projeler);
        listView.setAdapter(veriAdaptoru);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(Anasayfa.this,ProjeSayfasi.class);
                i.putExtra("userId",new Intent().getIntExtra("userId",500));
                i.putExtra("header","DenemeHeader"+position);
                i.putExtra("description","DenemeDescription"+position);
                i.putExtra("projeId",position);
                i.putExtra("price",position*10);
                i.putExtra("ownerId",position*100);
                startActivity(i);
            }
        });
        */

        try{
            Call<List<Proje>> call = getData.getProjects();
            call.enqueue(new Callback<List<Proje>>() {
                @Override
                public void onResponse(Call<List<Proje>> call, Response<List<Proje>> response) {
                    list=response.body();
                    final String[] projeler =new String[list.size()];
                    for (int i=0;i<list.size();i++){
                        projeler[i]=list.get(i).getHeader();
                    }
                    ListView listView= findViewById(R.id.listView);
                    ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<>(Anasayfa.this, R.layout.proje_layout, R.id.textView, projeler);
                    listView.setAdapter(veriAdaptoru);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            intent.putExtra("userId",userId);
                            intent.putExtra("userName",mailAdresi);
                            intent.putExtra("header",list.get(position).getHeader());
                            intent.putExtra("description",list.get(position).getDescription());
                            intent.putExtra("projeId",list.get(position).getId());
                            intent.putExtra("price",list.get(position).getMaxPrice());
                            intent.putExtra("ownerId",list.get(position).getOwnerId());
                            startActivity(intent);
                        }
                    });


                    /*for (int i=0;i<size;i++){
                        //tv.setText(tv.getText()+"\n"+list.get(i).getHeader()+" "+ list.get(i).getId());
                        TextView newTextView=new TextView(Anasayfa.this);
                        newTextView.setLayoutParams(tv.getLayoutParams());
                        newTextView.setPadding(30,30,30,30);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(10, 10, 10, 10);
                        newTextView.setLayoutParams(params);
                        newTextView.setBackgroundColor(getResources().getColor(R.color.siyah));
                        newTextView.setTextColor(getResources().getColor(R.color.beyaz));
                        newTextView.setText(list.get(i).getHeader()+"\n"+list.get(i).getDescription()+"\n"+newTextView.getId());
                        newTextView.setOnClickListener(Anasayfa.this);
                        ly.addView(newTextView);
                        tvs.ekle(new tv(newTextView));
                    }
                    tv.setText(tvs.listele());*/

                /*
                String[] dizi=new String[response.body().size()];
                for (int i=0;i<response.body().size();i++){
                    dizi[i]=response.body().get(i).getHeader();
                }
                ListView listemiz=(ListView) findViewById(R.id.listView);
                ArrayAdapter adapter = new ArrayAdapter<String>(Anasayfa.this,R.layout.activity_anasayfa,dizi);
                listemiz.setAdapter(adapter);
                */
                /*
                ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>(Anasayfa.this,R.id.listView,dizi);
                listView.setAdapter(veriAdaptoru);
                listView.addHeaderView(new );
                */
                }
                @Override
                public void onFailure(Call<List<Proje>> call, Throwable throwable) {
                    Toast.makeText(Anasayfa.this, "Projeler yüklenemedi.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch(Exception e){
            Toast.makeText(this, "Projeler yüklenirken bir hata oluştu.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        /*tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Anasayfa.this,ProjeSayfasi.class);
                int sira= tvs.getId(view.getId());
                i.putExtra("header",list.get(sira).getHeader());
                i.putExtra("description",list.get(sira).getDescription());
                i.putExtra("state","-");
                i.putExtra("id",list.get(sira).getId());
                startActivity(i);
            }
        });
        */
        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(Anasayfa.this,ProjeSayfasi.class);
                intent.putExtra("id",projectId);
                startActivity(intent);
            }
        });
        */
        /*
        User user2 = new User(2,"","","",0);
        Call<User> call2 = getData.UpdateUser(2,user2);
        call2.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User loginResponse = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
        */
        /*
        Call<List<User>> call = getData.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                for (int i=0;i<response.body().size();i++){
                    tv.setText(tv.getText()+"\n User: "+response.body().get(i).getName());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable throwable) {
                Toast.makeText(Anasayfa.this, "Unable to load users", Toast.LENGTH_SHORT).show();
            }
        });
        */
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
                intent.putExtra("mailAdress",mailAdresi);
                intent.putExtra("userName",userName);
                intent.putExtra("password",password);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        Intent i=new Intent(Anasayfa.this,ProjeSayfasi.class);
        i.putExtra("projectId",projectId);
        i.putExtra("header",header);
        i.putExtra("description",description);
        i.putExtra("state","-");
        startActivity(i);
    }
}
/*
class TextViews{
    int sira;
    tv bas,son;
    TextViews(){
        bas=son=null;
    }
    void ekle(tv newTV){
        if(bas==null) {
            bas = son=newTV;
            sira++;
        }
        else{
            son.sonraki=newTV;
            son=son.sonraki;
        }
    }
    int getSira(int id){
        sira=0;
        tv tmp=bas;
        while(tmp!=null){
            if (tmp.id==id){
                break;
            }
            sira++;
            tmp=tmp.sonraki;
        }
        return sira;
    }
    String listele(){
        String s="";
        tv tmp=bas;
        while(tmp!=null){
            s+=tmp.id+"\n";
            tmp=tmp.sonraki;
        }
        return s;
    }
}

class tv{
    tv sonraki;
    int id;
    tv(TextView new_tv){
        this.id=new_tv.getId();
    }
}*/