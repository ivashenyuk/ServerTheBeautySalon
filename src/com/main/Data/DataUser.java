package com.main.Data;

public class DataUser {
    private int idUser;
    public String nameUser;
    public String emailUser;
    private String statusUser;
    public String password;

    public DataUser() {
        this.idUser = -1;
        this.nameUser = "Івашенюк Юрій";
        this.emailUser = "yura.ivash@gmail.com";
        this.statusUser = "admin";
        this.password = "1111";
    }

    public DataUser(int idUser, String nameUser, String emailUser, String statusUser, String password) {
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.emailUser = emailUser;
        this.statusUser = statusUser;
        this.password = password;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public String getStatusUser() {
        return statusUser;
    }

    public String getPassword() {
        return password;
    }
}
