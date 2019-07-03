package com.freelancer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.freelancer.API.GetData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profil extends AppCompatActivity {
    Button newProject;
    GetData getData;
    TextView textView_KullaniciAdi,textView_mailAdresi,textView_Projelerim,textView_Tekliflerim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        textView_KullaniciAdi=findViewById(R.id.TextView_KullanıcıAdi);
        textView_mailAdresi=findViewById(R.id.TextView_MailAdresi);
        textView_Projelerim=findViewById(R.id.TextView_Projelerim);
        textView_Tekliflerim=findViewById(R.id.TextView_Tekliflerim);

        try {
            String eMail = new Intent().getStringExtra("eMail");
            textView_KullaniciAdi.setText(eMail);
        }
        catch(Exception e){

        }
        
        //YENİ PROJE OLUŞTURMA
        newProject=findViewById(R.id.Button_NewProject);
        newProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Proje p=new Proje("YeniProje","Yeni Projeeeeeeeeee",0,177,100);
                    Call<Proje> call=getData.NewProject(p);
                    call.enqueue(new Callback<Proje>() {
                        @Override
                        public void onResponse(Call<Proje> call, Response<Proje> response) {
                            Toast.makeText(Profil.this, "Yeni proje başarıyla oluşturuldu.", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onFailure(Call<Proje> call, Throwable t) {
                            Toast.makeText(Profil.this,"Bir hata oluştu\n"+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                catch (Exception e){
                    Toast.makeText(Profil.this,"Bir hata oluştu",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

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
                Intent i=new Intent(Profil.this, Profil.class);
                i.putExtra("id",new Intent().getStringExtra("id"));
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
