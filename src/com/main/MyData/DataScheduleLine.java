package com.main.MyData;

public class DataScheduleLine {
    private int _id;
    private String _services;
    private String _workers;
    private String _timeWork;

    public DataScheduleLine(int _id, String _services, String _workers, String _timeWork) {
        this._id = _id;
        this._services = _services;
        this._workers = _workers;
        this._timeWork = _timeWork;
    }

    public int get_id() {
        return _id;
    }

    public String get_services() {
        return _services;
    }

    public String get_workers() {
        return _workers;
    }

    public String get_timeWork() {
        return _timeWork;
    }
}
