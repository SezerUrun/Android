package com.freelancer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profil extends AppCompatActivity implements View.OnClickListener{
    Button button_newProject,button_sifreDegistir,Button_Messages,Button_Projects,Button_LoadCredit;
    GetData getData;
    TextView textView_KullaniciAdi,textView_mailAdresi,textView_Id,textView_Bakiye;
    EditText editText_eskiSifre, editText_yeniSifre, editText_yeniSifreTekrar;
    ImageView imageView_ProfilResmi;
    static String mailAdress,userName,password;
    static int userId,credit;
    Intent item,intent;
    ListView listView_Mesajlarim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        getData = RetrofitClient.getRetrofitInstance().create(GetData.class);
        textView_KullaniciAdi=findViewById(R.id.TextView_KullanıcıAdi);
        textView_Id=findViewById(R.id.TextView_Id);
        textView_mailAdresi=findViewById(R.id.TextView_MailAdresi);
        textView_Bakiye=findViewById(R.id.TextView_Bakiye);
        button_sifreDegistir=findViewById(R.id.Button_SifreDegistir);
        button_newProject=findViewById(R.id.Button_NewProject);
        Button_LoadCredit=findViewById(R.id.Button_LoadCredit);
        editText_eskiSifre=findViewById(R.id.EditText_EskiSifre);
        editText_yeniSifre=findViewById(R.id.EditText_YeniSifre);
        editText_yeniSifreTekrar=findViewById(R.id.EditText_YeniSifreTekrar);
        imageView_ProfilResmi=findViewById(R.id.ImageView_ProfilResmi);
        listView_Mesajlarim=findViewById(R.id.ListView_Mesajlarim);
        Button_Messages=findViewById(R.id.Button_GoToMessages);
        Button_Projects=findViewById(R.id.Button_Projects);

        intent= getIntent();
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

                    textView_KullaniciAdi.setText(textView_KullaniciAdi.getText()+userName);
                    textView_mailAdresi.setText(textView_mailAdresi.getText()+mailAdress);
                    textView_Id.setText(textView_Id.getText().toString()+userId);
                    textView_Bakiye.setText(textView_Bakiye.getText()+Integer.toString(credit));
                    //Toast.makeText(Profil.this,mailAdress+"\n"+userName+"\n"+password+"\n"+credit,Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Profil.this,"Kullanıcı bilgileri alınırken bir hata oluştu\n"+response.message(),Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(Profil.this,"Sunucu ile bağlantı kurulurken bir hata oluştu\n"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        button_sifreDegistir.setOnClickListener(this);
        button_newProject.setOnClickListener(this);
        Button_Messages.setOnClickListener(this);
        Button_Projects.setOnClickListener(this);
        Button_LoadCredit.setOnClickListener(this);
        imageView_ProfilResmi.setOnClickListener(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                Call<User> call = getData.getUser(userId);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.body()!=null){
                            mailAdress=response.body().getMail();
                            userName=response.body().getName();
                            password=response.body().getPassword();
                            credit=response.body().getCredit();

                            textView_KullaniciAdi.setText("Kullanıcı Adı : "+userName);
                            textView_mailAdresi.setText("Mail Adresi : "+mailAdress);
                            textView_Id.setText("Id : "+userId);
                            textView_Bakiye.setText("Bakiye : "+Integer.toString(credit));
                            //Toast.makeText(Profil.this,mailAdress+"\n"+userName+"\n"+password+"\n"+credit,Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(Profil.this,"Kullanıcı bilgileri alınırken bir hata oluştu\n"+response.message(),Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(Profil.this,"Sunucu ile bağlantı kurulurken bir hata oluştu\n"+t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

        }
        return super.onOptionsItemSelected(item);
    }

    //SAG USTTEKI ACILIR MENUYE TIKLAMA OLAYLARI
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View view) {
        int viewId=view.getId();
        if (viewId==button_sifreDegistir.getId()){
            if (editText_eskiSifre.getText().toString().equals(password)){
                if(editText_yeniSifre.getText().toString().length()>7){
                    if (editText_yeniSifre.getText().toString().equals(editText_yeniSifreTekrar.getText().toString())){
                        password=editText_yeniSifre.getText().toString();
                        User user =new User(userId,userName,mailAdress,password,credit);
                        Call<User> call = getData.UpdateUser(user);
                        call.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                editText_eskiSifre.setText("");
                                editText_yeniSifre.setText("");
                                editText_yeniSifreTekrar.setText("");
                                Toast.makeText(Profil.this,"Şifre başarıyla değiştirildi\n"+response.message(),Toast.LENGTH_SHORT).show();
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
            intent = new Intent(Profil.this,ProjeOlusturmaSayfasi.class);
            intent.putExtra("userId",userId);
            startActivity(intent);
        }
        else if(viewId==Button_Messages.getId()){
            intent=new Intent(Profil.this,Messages.class);
            intent.putExtra("userId",userId);
            startActivity(intent);
        }
        else if(viewId==Button_Projects.getId()){
            intent=new Intent(Profil.this,Projects.class);
            intent.putExtra("userId",userId);
            startActivity(intent);
        }
        else if(viewId==Button_LoadCredit.getId()){
            intent=new Intent(Profil.this,LoadCredit.class);
            intent.putExtra("userId",userId);
            startActivity(intent);
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
        /*Intent intent=new Intent(Profil.this,Anasayfa.class);
        intent.putExtra("mailAdress",mailAdress);
        intent.putExtra("userId",userId);
        intent.putExtra("userName",userName);
        intent.putExtra("password",password);*/
        super.onBackPressed();
    }
}
