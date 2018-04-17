package com.main.MyData;

public class DataOrderLine {
    private int _id;
    private String _customer;
    private String _serveice;
    private String _price;
    private String _email;
    private String _date;
    private String _performer;
    private String _discount;

    public DataOrderLine() {
        this._id = 0;
        this._customer = "";
        this._serveice = "";
        this._price = "";
        this._email = "";
        this._date = "";
        this._performer = "";
        this._discount = "";
    }

    public DataOrderLine(int _id, String _customer, String _serveice, String _price, String _email, String _date, String _performer, String _discount) {
        this._id = _id;
        this._customer = _customer;
        this._serveice = _serveice;
        this._price = _price;
        this._email = _email;
        this._date = _date;
        this._performer = _performer;
        this._discount = _discount;
    }

    public int get_id() {
        return _id;
    }

    public String get_customer() {
        return _customer;
    }

    public String get_serveice() {
        return _serveice;
    }

    public String get_price() {
        return _price;
    }

    public String get_email() {
        return _email;
    }

    public String get_date() {
        return _date;
    }

    public String get_performer() {
        return _performer;
    }

    public String get_discount() {
        return _discount;
    }
}
