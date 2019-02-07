package com.example.hezeyan.myapplication;

import java.util.ArrayList;
import java.util.HashMap;

public class ListObj {
    private String id,name,userid;
    private HashMap<String,Object> placeList = new HashMap<>();
    public ListObj (){ }
    public ListObj (String id,String name, String userid,HashMap<String,Object> placeList){
        this.id = id;
        this.name = name;
        this.userid = userid;
        this.placeList = placeList;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setPlaceList(HashMap<String,Object> placeList) {
        this.placeList = placeList;
    }

    public String getId(){

        return id;
    }

    public String getName() {
        return name;
    }

    public String getUserid() {
        return userid;
    }

    public HashMap<String,Object> getPlaceList() {
        return placeList;
    }
}
