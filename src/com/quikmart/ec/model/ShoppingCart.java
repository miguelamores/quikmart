package com.quikmart.ec.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 17/07/2017.
 */
public class ShoppingCart {

    private List<Item> items = new ArrayList<>();
    private int itemCount;
    private double totalPrice;
    private String customer;

    public ShoppingCart() {

    }

    public ShoppingCart(List<Item> items, int itemCount, double totalPrice, String customer) {
        this.items = items;
        this.itemCount = itemCount;
        this.totalPrice = totalPrice;
        this.customer = customer;
    }

    public void addToCart(String itemName, int quantity, double price, String taxStatus) {
        Item tmp = new Item(itemName, quantity, price, 1.3, "Taxable");
        totalPrice += price * quantity;
        items.add(tmp);
        itemCount += quantity;
    }

    public String checkoutAndPrint() {
        return "";
    }

    @Override
    public String toString() {
        String totalItems = "";
        for (Item item: items) {
            totalItems += item.getName()+",";
        }

        return "********************************************************\n" +
                "Your ShoppingCart is: \n"
                +
                "items= " + totalItems + "\n"+
                ", Number of items=" + itemCount + "\n"+
                ", total price= $" + totalPrice + "\n"+
                "*******************************************************";
    }
}
