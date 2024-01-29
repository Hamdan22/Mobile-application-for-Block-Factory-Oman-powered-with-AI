package com.example.binsapp;


public class CartItem {

    private String itemId;
    private String itemName;
    private Double quantity;
    private  String userEmail;
    private  String userID;
    private Double price;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public CartItem(String itmID, String itemName, Double itemQuntity, Double price, String userEmail, String userID)
    {
        this.itemId = itmID;
        this.itemName = itemName;
        this.quantity = itemQuntity;
        this.userEmail = userEmail;
        this.price = price;
        this.userID = userID;

    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }




    public CartItem()
    {

    }

}

