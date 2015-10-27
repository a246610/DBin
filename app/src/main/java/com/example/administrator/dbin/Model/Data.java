package com.example.administrator.dbin.Model;

public class Data {

    private int id;
    private String desc;
    private int state;

    public Data(int id_, String desc_, int state_){
        this.id = id_;
        this.desc = desc_;
        this.state = state_;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
