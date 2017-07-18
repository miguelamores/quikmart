package com.quikmart.ec.model;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 17/07/2017.
 */
public class ShoppingCart {

    private static int count = 1;
    private long transactionId;
    private List<Item> items = new ArrayList<>();
    private int itemCount;
    private double totalPrice;
    private String customer;

    public long getTransactionId() {
        return transactionId;
    }

    public ShoppingCart() {
        transactionId += count;
    }

    public ShoppingCart(List<Item> items, int itemCount, double totalPrice, String customer) {
        this.items = items;
        this.itemCount = itemCount;
        this.totalPrice = totalPrice;
        this.customer = customer;
    }

    public void addToCart(String itemName, int quantity, double price, String taxStatus) {
        Item tmp = new Item(itemName, quantity, price, price, "Taxable");
        totalPrice += price * quantity;
        items.add(tmp);
        itemCount += quantity;
    }

    public void removeItem(String itemName, int quantity){
        for (Item item: items) {
            if(itemName.equalsIgnoreCase(item.getName()) && item.getQuantity()>=quantity){
                totalPrice -= item.getMemberPrice()*quantity;
                itemCount -= quantity;
                if(item.getQuantity() == quantity)
                    items.remove(item);
                else
                    item.setQuantity(item.getQuantity()-quantity);
                break;
            }
        }
    }

    public String checkoutAndPrint() {
        Date now = new Date();
        String output = ""+ DateFormat.getDateTimeInstance(
                DateFormat.LONG, DateFormat.SHORT).format(now)+ "\r\n" +
                "TRANSACTION: "+transactionId+"\r\n" +
                "ITEM         QUANTITY       UNIT PRICE      TOTAL\r\n";
        for (Item item: items) {
            output += ""+item.getName()+"             "+item.getQuantity()+"           $"+
                    item.getMemberPrice()+"           $"+ item.getQuantity()*item.getMemberPrice()+"\r\n";
        }

        output += "*****************************************\r\n" +
                "TOTAL NUMBER OF ITEMS SOLD: "+itemCount+"\r\n" +
                "SUB-TOTAL: $"+totalPrice+"\r\n" +
                "*****************************************";
        return output;
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
