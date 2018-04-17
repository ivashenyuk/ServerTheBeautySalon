package com.main.MyData;

public class DataUser {
    private int idUser;
    public String nameUser;
    public String emailUser;
    private String statusUser;
    public String password;
    public String secretCode;

    public DataUser() {
        this.idUser = -1;
        this.nameUser = "";
        this.emailUser = "";
        this.statusUser = "";
        this.password = "";
        this.secretCode = "";
    }

    public DataUser(int idUser, String nameUser, String emailUser, String statusUser, String password, String secretCode) {
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.emailUser = emailUser;
        this.statusUser = statusUser;
        this.password = password;
        this.secretCode = secretCode;
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

    public String getSecretCode() {
        return secretCode;
    }
}
