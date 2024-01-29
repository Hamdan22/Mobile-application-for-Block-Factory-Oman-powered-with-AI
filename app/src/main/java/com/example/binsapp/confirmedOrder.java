package com.example.binsapp;

public class confirmedOrder {

    public confirmedOrder() {
    }
    String   cartItemsid, deliveryAddress,  totalCost,  getuserId,  userEmail,  userName, userMobile, quntity;



    public String getCartItems() {
        return cartItemsid;
    }

    public void setCartItems(String cartItems) {
        this.cartItemsid = cartItems;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getGetuserId() {
        return getuserId;
    }

    public void setGetuserId(String getuserId) {
        this.getuserId = getuserId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getQuntity() {
        return quntity;
    }

    public void setQuntity(String quntity) {
        this.quntity = quntity;
    }

    public  confirmedOrder(String quntity, String deliveryAddress, String totalCost, String getuserId, String userEmail, String userName, String userMobile)
    {
        this.quntity = quntity;
        this.cartItemsid = cartItemsid;
        this.deliveryAddress = deliveryAddress;
        this. totalCost = totalCost;
        this. getuserId = getuserId;
        this.userEmail = userEmail;
        this.userName = userName;
        this. userMobile = userMobile;

    }
}
