package com.alexanderneumann.mysqlclicontroller;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final String EXIT_CASE = "esc";

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("URL");
        String userName = dotenv.get("USER_NAME");
        String password = dotenv.get("PASSWORD");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, userName, password);
            Product product = new Product(connection);
            Order order = new Order(connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        String keyboardInput = "";

        while (!keyboardInput.equals("12")) {
            System.out.println("""
                                        
                    Select entry:
                    1. Show all products
                    2. Show a specific product
                    3. Add a product
                    4. Modify a product entry
                    5. Remove a specific product
                    6. Show all orders
                    7. Show a specific order
                    8. Add a order
                    9. Modify a order entry
                    10.Remove a specific order
                    11.Show all products that are on order
                    12.Exit application
                    """);

            while (true) {
                keyboardInput = scanner.nextLine();
                if (keyboardInput.matches("\\d+") && Integer.parseInt(keyboardInput) >= 1 && Integer.parseInt(keyboardInput) <= 12) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 12:");
                }
            }

            switch (keyboardInput) {
                case "1" -> Product.index();
                case "2" -> {
                    String productName;
                    boolean leaveLoop = false;
                    System.out.println("Please enter the name of the product:");
                    while (true) {
                        productName = scanner.nextLine();
                        if (productName.equals(EXIT_CASE)) {
                            leaveLoop = true;
                            break;
                        }
                        if (Product.productExists(productName)) {
                            break;
                        }
                    }

                    if (leaveLoop) {
                        break;
                    }
                    Product.show(productName);
                }

                case "3" -> {
                    String productPrice;
                    boolean leaveLoop = false;
                    System.out.println("Please enter the name of the product:");
                    String productName = scanner.nextLine();
                    if (productName.equals(EXIT_CASE)) {
                        break;
                    }

                    System.out.println("Please enter the price of the product:");
                    while (true) {
                        productPrice = scanner.nextLine();
                        if (productPrice.equals(EXIT_CASE)) {
                            leaveLoop = true;
                            break;
                        }
                        try {
                            Double.parseDouble(productPrice);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter the price of the product:");
                        }
                    }

                    if (leaveLoop) {
                        break;
                    }
                    Product.create(productName, productPrice);
                }

                case "4" -> {
                    String productName;
                    String newProductName;
                    String productPrice;
                    boolean leaveLoop = false;
                    System.out.println("Please enter the name of the product that should be modified:");
                    while (true) {
                        productName = scanner.nextLine();
                        if (productName.equals(EXIT_CASE)) {
                            leaveLoop = true;
                            break;
                        }
                        if (Product.productExists(productName)) {
                            break;
                        }
                    }
                    if (leaveLoop) {
                        break;
                    }

                    System.out.println("Please enter the new name of the product:");
                    newProductName = scanner.nextLine();
                    if (newProductName.equals(EXIT_CASE)) {
                        break;
                    }

                    System.out.println("Please enter the new price of the product:");
                    while (true) {
                        productPrice = scanner.nextLine();
                        if (productPrice.equals(EXIT_CASE)) {
                            leaveLoop = true;
                            break;
                        }
                        try {
                            Double.parseDouble(productPrice);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Input. Please enter the new price of the product:");
                        }
                    }

                    if (leaveLoop) {
                        break;
                    }
                    Product.update(newProductName, productPrice, productName);
                }

                case "5" -> {
                    String productName;
                    boolean leaveLoop = false;
                    System.out.println("Please enter the name of the product that should be deleted:");
                    while (true) {
                        productName = scanner.nextLine();
                        if (productName.equals(EXIT_CASE)) {
                            leaveLoop = true;
                            break;
                        }
                        if (Product.productExists(productName)) {
                            break;
                        }
                    }

                    if (leaveLoop) {
                        break;
                    }
                    Product.delete(productName);

                }

                case "6" -> Order.index();
                case "7" -> {
                    String productId;
                    boolean leaveLoop = false;
                    boolean numberProductId;
                    System.out.println("Please enter the product id of the order:");
                    while (true) {
                        numberProductId = false;
                        productId = scanner.nextLine();
                        if (productId.equals(EXIT_CASE)) {
                            leaveLoop = true;
                            break;
                        }
                        try {
                            Integer.parseInt(productId);
                            numberProductId = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter the product id of the order:");
                        }
                        if (numberProductId) {
                            if (Order.orderExists(productId)) {
                                break;
                            }
                        }
                    }

                    if (leaveLoop) {
                        break;
                    }
                    Order.show(productId);
                }

                case "8" -> {
                    boolean leaveLoop = false;
                    String productId;
                    String quantity;
                    System.out.println("Please enter the product id of the order:");
                    while (true) {
                        productId = scanner.nextLine();
                        if (productId.equals(EXIT_CASE)) {
                            leaveLoop = true;
                            break;
                        }
                        try {
                            Integer.parseInt(productId);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter the product id of the order:");
                        }
                    }

                    if (leaveLoop) {
                        break;
                    }

                    System.out.println("Please enter the quantity of the order:");
                    while (true) {
                        quantity = scanner.nextLine();
                        if (quantity.equals(EXIT_CASE)) {
                            leaveLoop = true;
                            break;
                        }
                        try {
                            Integer.parseInt(quantity);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter the quantity of the order: ");
                        }
                        if (Order.productExists(productId)) {
                            break;
                        } else {
                            leaveLoop = true;
                            break;
                        }
                    }

                    if (leaveLoop) {
                        break;
                    }
                    double price = Order.returnProductPrice(productId);
                    Order.create(productId, quantity, price);
                }

                case "9" -> {
                    String productId;
                    String orderQuantity;
                    boolean leaveLoop = false;
                    boolean numberProductId;
                    System.out.println("Please enter the product id that should be modified:");
                    while (true) {
                        numberProductId = false;
                        productId = scanner.nextLine();
                        if (productId.equals(EXIT_CASE)) {
                            leaveLoop = true;
                            break;
                        }
                        try {
                            Integer.parseInt(productId);
                            numberProductId = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Input. Please enter the product id that should be modified:");
                        }
                        if (numberProductId) {
                            if (Order.orderExists(productId)) {
                                break;
                            }
                        }
                    }

                    if (leaveLoop) {
                        break;
                    }

                    System.out.println("Please enter the new product id of the order:");
                    String newProductId = scanner.nextLine();
                    if (newProductId.equals(EXIT_CASE)) {
                        break;
                    }

                    System.out.println("Please enter the new quantity of the order:");
                    while (true) {
                        orderQuantity = scanner.nextLine();
                        if (orderQuantity.equals(EXIT_CASE)) {
                            leaveLoop = true;
                            break;
                        }
                        try {
                            Integer.parseInt(orderQuantity);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter the new quantity of the order");
                        }
                    }

                    if (leaveLoop) {
                        break;
                    }
                    Order.productExists(newProductId);
                    double price = Order.returnProductPrice(newProductId);
                    Order.update(newProductId, orderQuantity, price, productId);
                }

                case "10" -> {
                    String productId;
                    boolean leaveLoop = false;
                    boolean numberProductId;
                    System.out.println("Please enter the product id that should be deleted:");
                    while (true) {
                        numberProductId = false;
                        productId = scanner.nextLine();
                        if (productId.equals(EXIT_CASE)) {
                            leaveLoop = true;
                            break;
                        }
                        try {
                            Integer.parseInt(productId);
                            numberProductId = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter the new quantity of the order:");
                        }
                        if (numberProductId) {
                            if (Order.orderExists(productId)) {
                                break;
                            }
                        }
                    }

                    if (leaveLoop) {
                        break;
                    }
                    Order.delete(productId);
                }

                case "11" -> Product.indexProductsOnOrder();
                case "12" -> System.out.println("Console will now be closed.");
            }
        }
    }
}