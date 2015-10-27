package com.example.administrator.dbin.Model;

import java.io.Serializable;
import java.util.List;

public class JsonDBin implements Serializable{

    private List<Data> data;
    private List<Route> route;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Route> getRoute() {
        return route;
    }

    public void setRoute(List<Route> route) {
        this.route = route;
    }

}
