package com.timur.templategensystem;

public class Cisco1941Template {
    String id;
    String buyer;
    int quantity;
    String orderAddress;

    public Cisco1941Template(String id, String buyer, int quantity, String orderAddress) {
        this.id = id;
        this.buyer = buyer;
        this.quantity = quantity;
        this.orderAddress = orderAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }
}
