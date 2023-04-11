package com.alexanderneumann.mysqlclicontroller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Product {
    private static Connection connection;

    public Product(Connection connection) {
        this.connection = connection;
    }

    public static void index() {
        try {
            String sql = "SELECT * FROM products";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("List of all products in the table products:");
                do {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    System.out.println("id: " + id + "   name: " + name + "   price: " + price);
                } while (rs.next());
            } else {
                System.out.println("No products found");
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public static void show(String productName) {
        try {
            String sql = "SELECT * FROM products WHERE name = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, productName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                System.out.println("Details of the product:");
                System.out.println("id: " + id + "   name: " + name + "   price: " + price);
            } else {
                System.out.println("This product does not exist. Please enter the name of the product:");
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public static void create(String name, String price) {
        try {
            String sql = "INSERT INTO products (name, price) VALUES (?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setDouble(2, Double.parseDouble(price));
            pstmt.executeUpdate();
            show(name);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public static boolean productExists(String name) {
        try {
            String sql = "SELECT * FROM products WHERE name = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                System.out.println("Invalid input. Please enter the right name of the product:");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public static void update(String newName, String price, String oldName) {
        try {
            String sql = "UPDATE products SET name = ?, price = ? WHERE name = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, newName);
            pstmt.setDouble(2, Double.parseDouble(price));
            pstmt.setString(3, oldName);
            pstmt.executeUpdate();
            show(newName);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public static void delete(String name) {
        try {
            String sql = "DELETE FROM products WHERE name = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            index();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public static void indexProductsOnOrder() {
        try {
            String sql = "SELECT orders.product_id, products.name, products.price, orders.quantity, orders.total_price FROM products INNER JOIN orders ON products.id = orders.product_id";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("List of all products that match orders:");
                do {
                    int productId = rs.getInt("product_id");
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    double totalPrice = rs.getDouble("total_price");
                    System.out.println("product_id: " + productId + "   name: " + name + "   price: " + price + "   quantity: " + quantity + "   total_price: " + totalPrice);
                } while (rs.next());
            } else {
                System.out.println("No orders found");
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
