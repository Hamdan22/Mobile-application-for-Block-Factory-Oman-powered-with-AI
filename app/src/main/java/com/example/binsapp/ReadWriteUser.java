package com.example.binsapp;

public class ReadWriteUser {
    public String username,password, email, adress, mobileNo;

    public ReadWriteUser(){};

    public ReadWriteUser(String txtuser,String txtpass,String txtemail, String txtadress, String txtmobile)
    {
        this.username = txtuser;
        this.password = txtpass;
        this.email = txtemail;
        this.adress = txtadress;
        this.mobileNo = txtmobile;
    }
}
