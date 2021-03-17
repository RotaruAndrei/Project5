package com.example.project5;

import java.util.ArrayList;

public class OrderModel {
    private int id;
    private ArrayList<GroceryModel> items;
    private String address, postCode, phone , email;
    private double totalPrice;
    private String paymentMethod;
    private boolean success;

    public OrderModel(ArrayList<GroceryModel> items, String address, String postCode, String phone, String email, double totalPrice, String paymentMethod, boolean success) {
        this.items = items;
        this.address = address;
        this.postCode = postCode;
        this.phone = phone;
        this.email = email;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.success = success;
        this.id = Utils.getOrderID();
    }

    public OrderModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<GroceryModel> getItems() {
        return items;
    }

    public void setItems(ArrayList<GroceryModel> items) {
        this.items = items;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "id=" + id +
                ", items=" + items +
                ", address='" + address + '\'' +
                ", postCode='" + postCode + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", totalPrice=" + totalPrice +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", success=" + success +
                '}';
    }
}
