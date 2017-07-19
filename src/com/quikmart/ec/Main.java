package com.quikmart.ec;

import com.quikmart.ec.model.Item;
import com.quikmart.ec.model.ShoppingCart;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        ArrayList<Item> inventoryList = new ArrayList<>();
        Item item;
        String itemName;
        double itemPrice;
        int quantity;
        boolean customer = false;

        ///Users/miguelamores/Documents/quikmart/ec/inventory.txt
        //D:\DeftConsulting\src\com\quikmart\ec\inventory.txt
        String workingDir = System.getProperty("user.dir");
        System.out.println("INVENTORY LIST");
        for (String line : Files.readAllLines(Paths.get(workingDir+"\\src\\com\\quikmart\\ec\\inventory.txt"))) {
            System.out.println(line);
            item = new Item();
            int count = 0;
            for (String part : line.split("s|:|,")) {
                String part2 = part.replaceAll("\\s","");
                if(count == 0){
                    item.setName(part);
                }
                if (count == 1){
                    item.setQuantity(Integer.valueOf(part2));
                }
                if (count == 2){
                    item.setRegularPrice(Double.valueOf(part2.replaceAll("\\$", "")));
                }
                if (count == 3){
                    item.setMemberPrice(Double.valueOf(part2.replaceAll("\\$", "")));
                }
                if (count == 4){
                    item.setTaxStatus(part2);
                }
                count++;
            }
            inventoryList.add(item);
        }
        Scanner scanner = new Scanner(System.in);
        String keepShopping = "y";
        ShoppingCart shoppingCart =  new ShoppingCart();

        System.out.print ("Select kind of member: \n" +
                "a) Rewards Member\n" +
                "b) Regular customer\n");
        //customer = scanner.next();
        switch (scanner.next()) {
            case "a": customer = true;
                break;
            case "b": customer = false;
                break;
        }

        int loop = 0;
        do {
            System.out.print ("Select the options: \n");
            System.out.print ("1) Add items to cart\n");
            System.out.print ("2) View cart\n");
            System.out.print ("3) Remove items\n");
            System.out.print ("4) Checkout and print\n");
            System.out.print ("0) Quit\n");

            switch (Integer.valueOf(scanner.next())) {
                case 1: {
                    do{
                        System.out.print ("Enter the name of the item: ");
                        itemName = scanner.next();

                        for (Item item1: inventoryList) {
                            if (item1.getName().equalsIgnoreCase(itemName)){
                                System.out.print ("Enter the quantity: ");
                                quantity = Integer.valueOf(scanner.next());

                                if (customer == Boolean.TRUE){
                                    itemPrice = item1.getMemberPrice();
                                } else {
                                    itemPrice = item1.getRegularPrice();
                                }

                                if (item1.getQuantity()>=quantity)
                                    shoppingCart.addToCart(itemName, quantity, itemPrice, item1.getTaxStatus());
                                else
                                    System.out.println("There are not enought product, currently there are: "+ item1.getQuantity());
                            }
                        }

                        System.out.print ("Continue shopping (y/n)? ");
                        keepShopping = scanner.next();
                    }while (keepShopping.equals("y"));
                    break;
                }

                case 2: System.out.println(shoppingCart.toString());
                        break;

                case 3: {
                    System.out.print("Name of item you want to remove: ");

                    itemName = scanner.next();
                    System.out.print("Quantity: ");
                    quantity = Integer.valueOf(scanner.next());
                    shoppingCart.removeItem(itemName, quantity);
                    break;
                }

                case 4: {
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(workingDir+"\\src\\com\\quikmart\\ec\\transaction_"+shoppingCart.getTransactionId()+".txt"))) {

                        bw.write(shoppingCart.checkoutAndPrint());

                        System.out.println("Done");
                        String inventoryUpdated="";
                        for (Item itemInventory: inventoryList) {
                            for (Item itemBought: shoppingCart.getItems()) {
                                if (itemBought.getName().equalsIgnoreCase(itemInventory.getName())){
                                    itemInventory.setQuantity(itemInventory.getQuantity()-itemBought.getQuantity());
                                }
                            }
                            inventoryUpdated += itemInventory.getName()+": "+itemInventory.getQuantity()+", "+itemInventory.getRegularPrice()+", " +
                                    itemInventory.getMemberPrice()+", "+itemInventory.getTaxStatus()+"\r\n";
                        }

                        try (BufferedWriter bw2 = new BufferedWriter(new FileWriter(workingDir+"\\src\\com\\quikmart\\ec\\inventory.txt"))){
                            bw2.write(inventoryUpdated);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } catch (IOException e) {

                        e.printStackTrace();

                    }
                    break;}

                case 0: loop = 1;
                default: System.out.println("Invalid option");
                    break;
            }
        } while (loop == 0);


    }
}
