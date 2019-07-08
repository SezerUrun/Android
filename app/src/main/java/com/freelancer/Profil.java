package com.freelancer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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
import com.freelancer.API.RetrofitClient;

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
    String mailAdresi,kullaniciAdi,projelerim,userName,password;
    int userId,bakiye;
    Intent item,intent;
    ListView listView_Mesajlarim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        getData = RetrofitClient.getRetrofitInstance().create(GetData.class);
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

        intent= getIntent();
        mailAdresi = intent.getStringExtra("mailAdress");
        password=intent.getStringExtra("password");
        userId=intent.getIntExtra("userId",-1);
        userName=intent.getStringExtra("userName");
        projelerim=intent.getStringExtra("projelerim");
        bakiye=intent.getIntExtra("bakiye",0);

        textView_KullaniciAdi.setText(textView_KullaniciAdi.getText()+userName);
        textView_mailAdresi.setText(textView_mailAdresi.getText()+mailAdresi);
        textView_Projelerim.setText(textView_Projelerim.getText()+projelerim);
        textView_Bakiye.setText(textView_Bakiye.getText()+Integer.toString(bakiye));

        button_sifreDegistir.setOnClickListener(this);
        button_newProject.setOnClickListener(this);
        imageView_ProfilResmi.setOnClickListener(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //GELEN MESAJLARIN LİSTELENMESİ
        try{
            Call<List<Message>> call = getData.getMessages(userId);
            call.enqueue(new Callback<List<Message>>() {
                @Override
                public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                    final List<Message> list=response.body();
                    final String[] projeler =new String[list.size()];
                    for (int i=0;i<list.size();i++){
                        projeler[i]=list.get(i).getContent();
                    }
                    ListView listView= findViewById(R.id.listView);
                    ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<>(Profil.this, R.layout.proje_layout, R.id.textView, projeler);
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
                i.putExtra("userId",new Intent().getStringExtra("userId"));
                i.putExtra("mailAdress",new Intent().getStringExtra("mailAdress"));
                i.putExtra("userName",new Intent().getStringExtra("userName"));
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
                        User user =new User(userId,kullaniciAdi,mailAdresi,password,bakiye);
                        Call<User> call = getData.UpdateUser(userId,user);
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
                Proje p=new Proje("YeniProje","Yeni Projeeeeeeeeee",0,userId,-1,100);
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

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Profil.this,Anasayfa.class);
        intent.putExtra("mailAdress",mailAdresi);
        intent.putExtra("userId",userId);
        intent.putExtra("userName",userName);
        intent.putExtra("password",password);
        super.onBackPressed();
    }
}
