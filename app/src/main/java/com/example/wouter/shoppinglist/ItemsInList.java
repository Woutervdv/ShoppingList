package com.example.wouter.shoppinglist;

public class ItemsInList {
    private int _itemsId;
    private int _listId;
    private int _productId;

    public ItemsInList(){

    }
    public ItemsInList(int _listId , int _productId){
        this._listId = _listId;
        this._productId = _productId;

    }

    public  ItemsInList(int _itemsId , int _listId , int _productId){
        this._itemsId = _itemsId;
        this._listId = _listId;
        this._productId = _productId;
    }

    public int get_itemsId() {
        return _itemsId;
    }

    public void set_itemsId(int _itemsId) {
        this._itemsId = _itemsId;
    }

    public int get_listId() {
        return _listId;
    }

    public void set_listId(int _listId) {
        this._listId = _listId;
    }

    public int get_productId() {
        return _productId;
    }

    public void set_productId(int _productId) {
        this._productId = _productId;
    }
}
