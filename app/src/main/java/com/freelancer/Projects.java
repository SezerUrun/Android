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
import android.widget.EditText;
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

public class Projects extends AppCompatActivity {

    TextView textView_Gonderici, textView_Alici, textView_Mesaj;
    EditText editText_Mesaj;
    Button button_Gonder;
    GetData getData;
    static String mailAdress,userName,password,senderName,receiverName;
    static int userId;
    ListView ListView_YayinladigimProjeler, ListView_CalistigimProjeler;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        getData = RetrofitClient.getRetrofitInstance().create(GetData.class);

        intent=getIntent();
        userId=intent.getIntExtra("userId",-1);

        ListView_YayinladigimProjeler=findViewById(R.id.ListView_YayinladigimProjeler);
        ListView_CalistigimProjeler=findViewById(R.id.ListView_CalistigimProjeler);

        refreshProjects();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh:
                refreshProjects();
        }
        return super.onOptionsItemSelected(item);
    }

    void refreshProjects(){
        //YAYINLADIĞIM PROJELERİN LİSTELENMESİ
        try{
            Call<List<Proje>> call = getData.getOwnerProjects(userId);
            call.enqueue(new Callback<List<Proje>>() {
                @Override
                public void onResponse(Call<List<Proje>> call, Response<List<Proje>> response) {
                    final List<Proje> list=response.body();
                    if (list!=null){
                        final String[] messages =new String[list.size()];
                        for (int i=0;i<list.size();i++){
                            messages[i]=list.get(i).getHeader();
                        }
                        ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<>(Projects.this, R.layout.satir_layout, R.id.textView, messages);
                        ListView_YayinladigimProjeler.setAdapter(veriAdaptoru);
                        ListView_YayinladigimProjeler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                intent=new Intent(Projects.this,ProjeSayfasi.class);
                                intent.putExtra("header",list.get(position).getHeader());
                                intent.putExtra("description",list.get(position).getDescription());
                                intent.putExtra("releaseTime",list.get(position).getReleaseTime());
                                intent.putExtra("deadLine",list.get(position).getDeadline());
                                intent.putExtra("ownerId",list.get(position).getOwnerId());
                                try{
                                    intent.putExtra("workerId",list.get(position).getWorkerId());
                                }
                                catch(Exception e){
                                }
                                intent.putExtra("maxPrice",list.get(position).getMaxPrice());
                                intent.putExtra("completedOwner",list.get(position).getCompletedOwner());
                                intent.putExtra("completedWorker",list.get(position).getCompletedWorker());
                                intent.putExtra("projectId",list.get(position).getId());
                                intent.putExtra("userId",userId);;
                                startActivity(intent);
                            }
                        });
                    }
                }
                @Override
                public void onFailure(Call<List<Proje>> call, Throwable throwable) {
                    Toast.makeText(Projects.this, "Sunucuya bağlanırken bir hata oluştu", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch(Exception e){
            Toast.makeText(this, "Bir hata oluştu\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        //CALIŞTIĞIM PROJELERİN LİSTELENMESİ
        try{
            Call<List<Proje>> call = getData.getWorkerProjects(userId);
            call.enqueue(new Callback<List<Proje>>() {
                @Override
                public void onResponse(Call<List<Proje>> call, Response<List<Proje>> response) {
                    final List<Proje> list=response.body();
                    if (list!=null){
                        final String[] messages =new String[list.size()];
                        for (int i=0;i<list.size();i++){
                            messages[i]=list.get(i).getHeader();
                        }
                        ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<>(Projects.this, R.layout.satir_layout, R.id.textView, messages);
                        ListView_CalistigimProjeler.setAdapter(veriAdaptoru);
                        ListView_CalistigimProjeler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                intent=new Intent(Projects.this,ProjeSayfasi.class);
                                intent.putExtra("userId",userId);
                                intent.putExtra("header",list.get(position).getHeader());
                                intent.putExtra("description",list.get(position).getDescription());
                                intent.putExtra("releaseTime",list.get(position).getReleaseTime());
                                intent.putExtra("deadLine",list.get(position).getDeadline());
                                intent.putExtra("ownerId",list.get(position).getOwnerId());
                                intent.putExtra("workerId",list.get(position).getWorkerId());
                                intent.putExtra("completedOwner",list.get(position).getCompletedOwner());
                                intent.putExtra("completedWorker",list.get(position).getCompletedWorker());
                                intent.putExtra("projectId",list.get(position).getId());
                                intent.putExtra("releaseTime",list.get(position).getReleaseTime());
                                intent.putExtra("deadLine",list.get(position).getDeadline());
                                intent.putExtra("maxPrice",list.get(position).getMaxPrice());
                                startActivity(intent);
                            }
                        });
                    }
                }
                @Override
                public void onFailure(Call<List<Proje>> call, Throwable throwable) {
                    Toast.makeText(Projects.this, "Sunucuya bağlanırken bir hata oluştu", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch(Exception e){
            Toast.makeText(this, "Bir hata oluştu\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
