package com.quikmart.ec.model;

/**
 * Created by user on 17/07/2017.
 */
public class Item {

    private String name;
    private int quantity;
    private double regularPrice;
    private double memberPrice;
    private String taxStatus;

    public Item() {
    }

    public Item(String name, int quantity, double regularPrice, double memberPrice, String taxStatus) {
        this.name = name;
        this.quantity = quantity;
        this.regularPrice = regularPrice;
        this.memberPrice = memberPrice;
        this.taxStatus = taxStatus;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(double regularPrice) {
        this.regularPrice = regularPrice;
    }

    public double getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(double memberPrice) {
        this.memberPrice = memberPrice;
    }

    public String getTaxStatus() {
        return taxStatus;
    }

    public void setTaxStatus(String taxStatus) {
        this.taxStatus = taxStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
