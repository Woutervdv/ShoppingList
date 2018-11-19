package com.example.wouter.shoppinglist;

public class Product {
    private String _name;
    private  String _brand;
    private  int _id;

    public Product(){

    }

    public Product(String name, String brand){

        this._name = name;
        this._brand = brand;
    }



    public Product(String name , int id, String brand ){
        this._name = name;
        this._id = id;
        this._brand = brand;
    }

    //getters


    public int get_id() {
        return _id;
    }
    public String get_name() {
        return _name;
    }
    public String get_brand() {
        return _brand;
    }


    //Setters



    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_name(String _name) {
        this._name = _name;
    }
    public void set_brand(String _brand) {
        this._brand = _brand;
    }
}


