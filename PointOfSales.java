/*
* Author: Paul O'Sullivan
* Date: 11/24/23
* Program: A Point of Sales System for a Burger & Fries fast food establishment
* Features: Take an Order where you can remove items as needed, A queue of unfulfilled orders where you can view and mark them as complete, and finally a Sales History
* where you can view all past sales with a Total Sales for how much money has been taken in since last open.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

public class PointOfSales {
    private static Queue<String> currentOrder = new LinkedList<>(); // Keep track of what is currently in an order under takeOrders.
    private static double orderTotal = 0.0; // orderTotal in takeOrders panel
    private static Queue<String> unfulfilledOrders = new LinkedList<>(); // Queue of unfulfilledOrders for unfulfilledOrders section.
    private static Queue<String> salesHistory = new LinkedList<>(); // Queue for completed orders
    private static double totalSales = 0.0; // Total Sales or money made
    private static int orderNumber = 1; // Keep track of orderNumber throughout day of business

    public static void main(String[] args) {
        createGUI();
    } // end main

    private static void createGUI() {
        JFrame frame = new JFrame("Point of Sale System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton sideButton1 = createButton("Sales History", e -> openSalesHistory());
        JButton sideButton3 = createButton("Unfulfilled Orders", e -> openUnfulfilledOrders());
        JButton sideButton4 = createButton("Take Order", e -> takeOrder());

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(sideButton1);
        panel.add(sideButton3);
        panel.add(sideButton4);

        frame.getContentPane().add(panel);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setVisible(true);
    } // end createGUI or homescreen.

    private static JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        button.setPreferredSize(new Dimension(150, 300));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.BLACK);
        return button;
    } // end createButton (Generic function for cleaner code)

    private static void openSalesHistory() {
        JFrame salesHistoryWindow = createWindow("Sales History", 600, 600);

        JTextArea salesHistoryArea = new JTextArea();
        salesHistoryArea.setEditable(false);

        JScrollPane scrollSH = new JScrollPane(salesHistoryArea);
        scrollSH.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel totalSalesPanel = new JPanel();
        totalSalesPanel.setLayout(new FlowLayout());
        totalSalesPanel.add(new JLabel("Total Sales: $"));
        JLabel totalSalesLabel = new JLabel("0.0");
        totalSalesPanel.add(totalSalesLabel);

        salesHistoryWindow.getContentPane().setLayout(new BorderLayout());
        salesHistoryWindow.getContentPane().add(scrollSH, BorderLayout.CENTER);
        salesHistoryWindow.getContentPane().add(totalSalesPanel, BorderLayout.SOUTH);

        updateSalesHistory(salesHistoryArea, totalSalesLabel);

        salesHistoryWindow.setVisible(true);
    } // end openSalesHistory

    private static void openUnfulfilledOrders() {
        JFrame unfulfilledOrdersWindow = createWindow("Unfulfilled Orders", 600, 600);

        JTextArea unfulfilledOrdersArea = new JTextArea();
        unfulfilledOrdersArea.setEditable(false);

        JScrollPane scrollUO = new JScrollPane(unfulfilledOrdersArea);
        scrollUO.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JButton markDoneButton = new JButton("Mark as Done");
        markDoneButton.addActionListener(e -> markOrderAsDone(unfulfilledOrdersArea));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(markDoneButton);

        unfulfilledOrdersWindow.getContentPane().setLayout(new BorderLayout());
        unfulfilledOrdersWindow.getContentPane().add(scrollUO, BorderLayout.CENTER);
        unfulfilledOrdersWindow.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        updateUnfulfilledOrders(unfulfilledOrdersArea);

        unfulfilledOrdersWindow.setVisible(true);
    } // end openUnfulfilledOrders

    private static void takeOrder() {
        JFrame takeOrderWindow = createWindow("Take Order", 600, 600);

        JButton itemButton1 = createOrderButton("Hamburger", 5);
        JButton itemButton2 = createOrderButton("Cheeseburger", 7);
        JButton itemButton3 = createOrderButton("6 Piece Chicken Nuggets", 4);
        JButton itemButton4 = createOrderButton("10 Piece Chicken Nuggets", 6);
        JButton itemButton5 = createOrderButton("Medium Fries", 2);
        JButton itemButton6 = createOrderButton("Large Fries", 3);
        JButton itemButton7 = createOrderButton("Small Soda", 1);
        JButton itemButton8 = createOrderButton("Medium Soda", 2);
        JButton itemButton9 = createOrderButton("Large Soda", 3);

        JTextArea orderSummaryArea = new JTextArea();
        orderSummaryArea.setEditable(false);

        ActionListener itemButtonListener = e -> {
            JButton clickedButton = (JButton) e.getSource();
            String itemName = clickedButton.getText().split(" - ")[0];
            double itemPrice = Double.parseDouble(clickedButton.getText().split("\\$")[1]);
            currentOrder.add(itemName);
            orderTotal += itemPrice;
            updateOrderSummary(orderSummaryArea);
        };

        itemButton1.addActionListener(itemButtonListener);
        itemButton2.addActionListener(itemButtonListener);
        itemButton3.addActionListener(itemButtonListener);
        itemButton4.addActionListener(itemButtonListener);
        itemButton5.addActionListener(itemButtonListener);
        itemButton6.addActionListener(itemButtonListener);
        itemButton7.addActionListener(itemButtonListener);
        itemButton8.addActionListener(itemButtonListener);
        itemButton9.addActionListener(itemButtonListener);

        JButton createOrderButton = new JButton("Create Order");
        createOrderButton.addActionListener(e -> {
            createOrder();
            takeOrderWindow.dispose();
        });

        JButton removeItemButton = new JButton("Remove Item");
        removeItemButton.addActionListener(e -> removeItem(orderSummaryArea));

        JPanel itemButtonsPanel = new JPanel();
        itemButtonsPanel.setLayout(new GridLayout(9, 1));
        itemButtonsPanel.add(itemButton1);
        itemButtonsPanel.add(itemButton2);
        itemButtonsPanel.add(itemButton3);
        itemButtonsPanel.add(itemButton4);
        itemButtonsPanel.add(itemButton5);
        itemButtonsPanel.add(itemButton6);
        itemButtonsPanel.add(itemButton7);
        itemButtonsPanel.add(itemButton8);
        itemButtonsPanel.add(itemButton9);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));
        buttonPanel.add(createOrderButton);
        buttonPanel.add(removeItemButton);

        takeOrderWindow.getContentPane().setLayout(new BorderLayout());
        takeOrderWindow.getContentPane().add(itemButtonsPanel, BorderLayout.WEST);
        takeOrderWindow.getContentPane().add(buttonPanel, BorderLayout.EAST);
        takeOrderWindow.getContentPane().add(orderSummaryArea, BorderLayout.CENTER);

        takeOrderWindow.setVisible(true);
    } // end takeOrder

    private static JButton createOrderButton(String itemName, int price) {
        JButton button = new JButton(itemName + " - $" + price);
        return button;
    } // end createOrderButton

    private static void updateOrderSummary(JTextArea orderSummaryArea) {
        orderSummaryArea.setText("Order Summary:\n");
        for (String item : currentOrder) {
            orderSummaryArea.append(item + "\n");
        }
        orderSummaryArea.append("\nTotal: $" + orderTotal);
    } // end updateOrderSummary (Keep accurate order list in take order)

    private static void removeItem(JTextArea orderSummaryArea) {
        if (!currentOrder.isEmpty()) {
            String removedItem = currentOrder.poll();
            double removedItemPrice = getItemPrice(removedItem);
            orderTotal -= removedItemPrice;
            updateOrderSummary(orderSummaryArea);
        }
    } // end removeItem (remove top item from current order being taken)

    private static double getItemPrice(String itemName) {
        switch (itemName) {
            case "Hamburger":
                return 5;
            case "Cheeseburger":
                return 7;
            case "6 Piece Chicken Nuggets":
                return 4;
            case "10 Piece Chicken Nuggets":
                return 6;
            case "Medium Fries":
                return 2;
            case "Large Fries":
                return 3;
            case "Small Soda":
                return 1;
            case "Medium Soda":
                return 2;
            case "Large Soda":
                return 3;
            default:
                return 0;
        }
    } // end getItemPrice (Keep accurate order total when removing items)

    private static void createOrder() {
        StringBuilder orderDetails = new StringBuilder("New Order #" + orderNumber + ":\n");
        while (!currentOrder.isEmpty()) {
            orderDetails.append(currentOrder.poll()).append("\n");
        }
        orderDetails.append("Total: $" + orderTotal);

        unfulfilledOrders.add(orderDetails.toString());
        orderNumber++;

        currentOrder.clear();
        orderTotal = 0.0;
    } // end createOrder (Clear current order & create a new order sent to unfulfilled orders)

    private static void markOrderAsDone(JTextArea unfulfilledOrdersArea) {
        if (!unfulfilledOrders.isEmpty()) {
            String doneOrder = unfulfilledOrders.poll();
            unfulfilledOrdersArea.setText("");
            updateUnfulfilledOrders(unfulfilledOrdersArea);
            moveOrderToSalesHistory(doneOrder);
        }
    } // end markOrderAsDone (Clear from unfulfilled orders and move to past orders)

    private static void updateUnfulfilledOrders(JTextArea unfulfilledOrdersArea) {
        unfulfilledOrdersArea.setText("Unfulfilled Orders:\n");
        int i = 1;
        for (String order : unfulfilledOrders) {
            unfulfilledOrdersArea.append(i + ". " + order + "\n");
            i++;
        }
    } // end updateUnfulfilledOrders

    private static void moveOrderToSalesHistory(String order) {
        salesHistory.add(order);
        totalSales += getOrderTotal(order);
    } // end moveOrderToSalesHistory (When completed, add to sales history queue)

    private static double getOrderTotal(String order) {
        String[] lines = order.split("\n");
        String totalLine = lines[lines.length - 1];
        return Double.parseDouble(totalLine.split("\\$")[1]);
    } // end getOrderTotal

    private static void updateSalesHistory(JTextArea salesHistoryArea, JLabel totalSalesLabel) {
        salesHistoryArea.setText("Sales History:\n");
        int i = 1;
        for (String order : salesHistory) {
            salesHistoryArea.append(i + ". " + order + "\n");
            i++;
        }
        totalSalesLabel.setText(String.format("%.2f", totalSales));
    } // end updateSalesHistory

    private static JFrame createWindow(String title, int width, int height) {
        JFrame window = new JFrame(title);
        window.setSize(width, height);
        window.setLocationRelativeTo(null);
        return window;
    } // end createWindow (Generic function for clean code)
} // end PointOfSales class
