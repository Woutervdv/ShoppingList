package com.example.wouter.shoppinglist;

public class Product {
    private String _name;
    private  int _id;

    public Product(){

    }

    public Product(String name){
        this._name = name;
    }


    public Product(String name , int id){
        this._name = name;
        this._id = id;
    }

    //getters
    public String get_name() {
        return _name;
    }

    public int get_id() {
        return _id;
    }


    //Setters
    public void set_name(String _name) {
        this._name = _name;
    }


    public void set_id(int _id) {
        this._id = _id;
    }
}
