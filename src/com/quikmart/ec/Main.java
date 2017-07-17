package com.quikmart.ec;

import com.quikmart.ec.model.Item;
import com.quikmart.ec.model.ShoppingCart;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        /*Files.lines(Paths.get("D:\\DeftConsulting\\src\\com\\quikmart\\ec\\inventory.txt"))
                .map(line -> line.split("s|:|,")) // Stream<String[]>
                .flatMap(Arrays::stream) // Stream<String>
                .forEach(System.out::println);*/
        //.map(line -> line.split("\\s+ :+ ,")) // Stream<String[]>



        ArrayList<Item> inventoryList = new ArrayList<>();
        Item item;
        String itemName;
        double itemPrice;
        int quantity;
        boolean customer = false;

        for (String line : Files.readAllLines(Paths.get("D:\\DeftConsulting\\src\\com\\quikmart\\ec\\inventory.txt"))) {
            System.out.println(line);
            item = new Item();
            System.out.println(item);
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

        do{
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


            System.out.print ("Enter the name of the item: ");
            itemName = scanner.next();

            for (Item item1: inventoryList) {
                if (item1.getName().equals(itemName)){
                    System.out.print ("Enter the quantity: ");
                    quantity = Integer.valueOf(scanner.next());

                    if (customer == Boolean.TRUE){
                        itemPrice = item1.getMemberPrice();
                    } else {
                        itemPrice = item1.getRegularPrice();
                    }

                    shoppingCart.addToCart(itemName, quantity, itemPrice, item1.getTaxStatus());
                }
            }


            System.out.println(shoppingCart);


            System.out.print ("Continue shopping (y/n)? ");
            keepShopping = scanner.next();
        }while (keepShopping.equals("y"));


    }
}
