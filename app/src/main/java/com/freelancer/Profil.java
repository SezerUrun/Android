package com.freelancer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.API.GetData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profil extends AppCompatActivity implements View.OnClickListener{
    Button button_newProject,button_sifreDegistir;
    GetData getData;
    TextView textView_KullaniciAdi,textView_mailAdresi,textView_Projelerim,textView_Bakiye;
    EditText editText_eskiSifre, editText_yeniSifre, editText_yeniSifreTekrar;
    ImageView imageView_ProfilResmi;
    String mailAdresi,sifre,kullaniciAdi,projelerim;
    int kullaniciId,bakiye;
    Intent item;
    ListView listView_Mesajlarim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        textView_KullaniciAdi=findViewById(R.id.TextView_KullanıcıAdi);
        textView_mailAdresi=findViewById(R.id.TextView_MailAdresi);
        textView_Projelerim=findViewById(R.id.TextView_Projelerim);
        textView_Bakiye=findViewById(R.id.TextView_Bakiye);
        button_sifreDegistir=findViewById(R.id.Button_SifreDegistir);
        button_newProject=findViewById(R.id.Button_NewProject);
        editText_eskiSifre=findViewById(R.id.EditText_EskiSifre);
        editText_yeniSifre=findViewById(R.id.EditText_YeniSifre);
        editText_yeniSifreTekrar=findViewById(R.id.EditText_YeniSifreTekrar);
        imageView_ProfilResmi=findViewById(R.id.ImageView_ProfilResmi);
        listView_Mesajlarim=findViewById(R.id.ListView_Mesajlarim);
        button_sifreDegistir.setOnClickListener(this);
        button_newProject.setOnClickListener(this);
        imageView_ProfilResmi.setOnClickListener(this);

        //KULLANICI BİLGİLERİNİN ÖNCEKİ ACVIVITY'DEN ALINMASI
        try {
            Intent i=new Intent();
            mailAdresi = i.getStringExtra("mailAdresi");
            sifre=i.getStringExtra("sifre");
            kullaniciId=i.getIntExtra("kullaniciId",-1);
            kullaniciAdi=i.getStringExtra("kullaniciAdi");
            projelerim=i.getStringExtra("projelerim");
            bakiye=i.getIntExtra("bakiye",0);
            textView_KullaniciAdi.setText(textView_KullaniciAdi.getText()+Integer.toString(kullaniciId));
            textView_mailAdresi.setText(textView_mailAdresi.getText()+mailAdresi);
            textView_Projelerim.setText(textView_Projelerim.getText()+projelerim);
            textView_Bakiye.setText(textView_Bakiye.getText()+Integer.toString(bakiye));
        }
        catch(Exception e){
            Toast.makeText(this, "Kullanici bilgileri alınamadı", Toast.LENGTH_SHORT).show();
        }

        //GELEN MESAJLARIN LİSTELENMESİ
        try{
            Call<List<Message>> call = getData.getMessages(kullaniciId);
            call.enqueue(new Callback<List<Message>>() {
                @Override
                public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                    final List<Message> list=response.body();
                    final String[] projeler =new String[list.size()];
                    for (int i=0;i<list.size();i++){
                        projeler[i]=list.get(i).getContent();
                    }
                    ListView listView= findViewById(R.id.listView);
                    ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<>(Profil.this, R.layout.satir_layout, R.id.textView, projeler);
                    listView.setAdapter(veriAdaptoru);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent i=new Intent(Profil.this,MesajlasmaSayfasi.class);
                            i.putExtra("content",list.get(position).getContent());
                            i.putExtra("senderId",list.get(position).getSenderId());
                            i.putExtra("receiverId",list.get(position).getReceiverId());
                            startActivity(i);
                        }
                    });
                }
                @Override
                public void onFailure(Call<List<Message>> call, Throwable throwable) {
                    Toast.makeText(Profil.this, "Projeler yüklenemedi.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch(Exception e){
            Toast.makeText(this, "Mesajlar yüklenirken bir hata oluştu.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    //SAG USTTEKI ACILIR MENUYE TIKLAMA OLAYLARI
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
                i.putExtra("mailAdresi",new Intent().getStringExtra("mailAdresi"));
                i.putExtra("kullaniciAdi",new Intent().getStringExtra("kullaniciAdi"));
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        int viewId=view.getId();
        if (viewId==button_sifreDegistir.getId()){
            if (editText_eskiSifre.getText().equals(new Intent().getStringArrayExtra("sifre"))){
                if(editText_yeniSifre.getText().length()>7){
                    if (editText_yeniSifre.getText().equals(editText_yeniSifreTekrar.getText())){
                        User user =new User(kullaniciId,kullaniciAdi,mailAdresi,sifre,bakiye);
                        Call<User> call = getData.UpdateUser(kullaniciId,user);
                        call.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                Toast.makeText(Profil.this,"Şifre başarıyla değiştirildi",Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Toast.makeText(Profil.this,"Bir hata oluştu",Toast.LENGTH_SHORT).show();
                                call.cancel();
                            }
                        });
                    }
                    else{
                        Toast.makeText(Profil.this,"Yeni şifreler eşleşmiyor",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Profil.this,"Lütfen en az 8 karakterden uzun bir şifre girin",Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(Profil.this,"Eski şifre yanlış girildi",Toast.LENGTH_SHORT).show();
            }
        }
        else if (viewId==button_newProject.getId()){
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
                        Toast.makeText(Profil.this,"Sunucudan yanıt alınamadı\n"+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
            catch (Exception e){
                Toast.makeText(Profil.this,"Proje oluşturulamadı",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        else if(viewId==imageView_ProfilResmi.getId()){
            //performFileSearch();
        }
    }


    //PROFIL RESMI DEGISTIRME
    private static final int READ_REQUEST_CODE = 42;
    public void performFileSearch() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("picture/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        item=data;
        if (requestCode == READ_REQUEST_CODE && resultCode == MainActivity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                //imageView_ProfilResmi.setBackground();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
