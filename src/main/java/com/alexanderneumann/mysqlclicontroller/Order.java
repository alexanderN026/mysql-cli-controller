package com.alexanderneumann.mysqlclicontroller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Order {
    private static Connection connection;

    public Order(Connection connection) {
        this.connection = connection;
    }

    public static void index() {
        try {
            String sql = "SELECT * FROM orders";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("List of all orders in the table orders:");
                do {
                    int id = rs.getInt("id");
                    int productId = rs.getInt("product_id");
                    int quantity = rs.getInt("quantity");
                    double totalPrice = rs.getDouble("total_price");
                    System.out.println("id: " + id + "   product_id: " + productId + "   quantity: " + quantity + "   total_price: " + totalPrice);
                } while (rs.next());
            } else {
                System.out.println("No orders found");
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public static void show(String orderedProductId) {
        try {
            String sql = "SELECT * FROM orders WHERE product_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(orderedProductId));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                double totalPrice = rs.getDouble("total_price");
                System.out.println("Details of the order:");
                System.out.println("id: " + id + "   product_id: " + productId + "   quantity: " + quantity + "   total_price: " + totalPrice);
            } else {
                System.out.println("This order does not exist. Please enter the name of the order:");
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public static boolean productExists(String productId) {
        try {
            String sql = "SELECT * FROM products WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(productId));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                System.out.println("Order cannot be created because the Product does not exist yet. Please add a product first.");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public static double returnProductPrice(String productId) {
        try {
            String sql = "SELECT price FROM products WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setDouble(1, Double.parseDouble(productId));
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getDouble("price");
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public static void create(String productId, String quantity, double price) {
        try {
            double totalPrice = price * Double.parseDouble(quantity);
            String sql = "INSERT INTO orders (product_id, quantity , total_price) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(productId));
            pstmt.setInt(2, Integer.parseInt(quantity));
            pstmt.setDouble(3, totalPrice);
            pstmt.executeUpdate();
            show(productId);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public static boolean orderExists(String productId) {
        try {
            String sql = "SELECT * FROM orders WHERE product_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(productId));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                System.out.println("Invalid input. Please enter the right product id:");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public static void update(String newProductId, String quantity, double price, String oldProductId) {
        try {
            double totalPrice = price * Double.parseDouble(quantity);
            String sql = "UPDATE orders SET product_id = ?, quantity = ? , total_price = ? WHERE product_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(newProductId));
            pstmt.setInt(2, Integer.parseInt(quantity));
            pstmt.setDouble(3, totalPrice);
            pstmt.setInt(4, Integer.parseInt(oldProductId));
            pstmt.executeUpdate();
            show(newProductId);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public static void delete(String productId) {
        try {
            String sql = "DELETE FROM orders WHERE product_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(productId));
            pstmt.executeUpdate();
            index();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
