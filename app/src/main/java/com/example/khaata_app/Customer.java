package com.example.khaata_app;

public class Customer {
    int cid;
    int vid;
    String name;
    String date;
    String time;
    int remaining_amount;
    public Customer(){

    }
    public Customer(int cid, int vid, String name, String date, String time,int remaining_amount) {
        this.cid = cid;
        this.vid = vid;
        this.name = name;
        this.date = date;
        this.time = time;
        this.remaining_amount=remaining_amount;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getRemaining_amount() {
        return remaining_amount;
    }

    public void setRemaining_amount(int remaining_amount) {
        this.remaining_amount = remaining_amount;
    }
}
