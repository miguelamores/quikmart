package com.quikmart.ec.model;

import java.math.BigDecimal;
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
    private double tax;

    public double getTax() {
        return tax;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public ShoppingCart() {
        transactionId += count;
    }

    public ShoppingCart(List<Item> items, int itemCount, double totalPrice, double tax) {
        this.items = items;
        this.itemCount = itemCount;
        this.totalPrice = totalPrice;
        this.tax = tax;
    }

    public void addToCart(String itemName, int quantity, double price, String taxStatus) {
        Item tmp = new Item(itemName, quantity, price, price, taxStatus);
        totalPrice += price * quantity;
        items.add(tmp);
        itemCount += quantity;
        if (taxStatus.equals("Taxable")){
            tax += price*0.065*quantity;
        }
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

    public String checkoutAndPrint(double cash) {
        BigDecimal change = BigDecimal.valueOf(totalPrice).add(BigDecimal.valueOf(tax));
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
                "TAX(6.5%): $"+tax+"\r\n" +
                "TOTAL: $"+ change+"\r\n" +
                "CASH: $"+ cash+"\r\n" +
                "CHANGE: $"+ BigDecimal.valueOf(cash).subtract(change)+ "\r\n"+
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
