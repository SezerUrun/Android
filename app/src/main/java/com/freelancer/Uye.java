package com.freelancer;

public class Uye {
    int id, bakiye;
    String kullaniciAdi, mailAdresi;

    Uye(int id, int bakiye, String kullaniciAdi,String mailAdresi){
        this.id=id;
        this.bakiye=bakiye;
        this.kullaniciAdi=kullaniciAdi;
        this.mailAdresi=mailAdresi;
    }

    void setId(int id){
        this.id=id;
    }
    void setBakiye(int bakiye){
        this.bakiye=bakiye;
    }
    void setKullaniciAdi(String kullaniciAdi){
        this.kullaniciAdi=kullaniciAdi;
    }
    void setMailAdresi(String mailAdresi){
        this.mailAdresi=mailAdresi;
    }
    int getId(){
        return id;
    }
    int getBakiye(){
        return bakiye;
    }
    String getKullaniciAdi(){
        return kullaniciAdi;
    }
    String getMailAdresi(){
        return mailAdresi;
    }
}
