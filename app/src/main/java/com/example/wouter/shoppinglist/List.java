package com.example.wouter.shoppinglist;

import java.io.Serializable;

public class List implements Serializable {
    private int _listId;
    private String _listName;

    public List(){

    }



    public List(String _listName){
        this._listName = _listName;
    }

    public List(int _listId, String _listName){
        this._listId = _listId;
        this._listName = _listName;
    }

    public int get_listId() {
        return _listId;
    }

    public String get_listName() {
        return _listName;
    }


    public void set_listId(int _listId) {
        this._listId = _listId;
    }


    public void set_listName(String _listName) {
        this._listName = _listName;
    }
}
