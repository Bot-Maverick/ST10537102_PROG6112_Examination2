
package com.mycompany.examination2;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;

public class MattressSalesGUI extends JFrame {

    private final JTextField customerNameField = new JTextField(22);

    private final JComboBox<String> productComboBox = new JComboBox<>(new String[] {"Cozy Nights Monarch","Restonic Barbados","Sealy Phuket"});

    private final JTextField priceField = new JTextField(22);

    private final DecimalFormat money = new DecimalFormat("0.00");


    public MattressSalesGUI() {

        setTitle("Mattress Sales Management System");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(600, 370);

        setLocationRelativeTo(null);

        setResizable(false);

        createMenu();

        createForm();
    }


    //Menu system
    private void createMenu() {

        JMenuBar bar = new JMenuBar();

        JMenu file = new JMenu("File");

        JMenuItem exit = new JMenuItem("Exit");

        exit.addActionListener(e -> System.exit(0));

        file.add(exit);


        JMenu tools = new JMenu("Tools");

        JMenuItem process = new JMenuItem("Process");

        JMenuItem clear = new JMenuItem("Clear");


        process.addActionListener(e -> processSale());

        clear.addActionListener(e -> clearForm());


        tools.add(process);

        tools.add(clear);


        bar.add(file);

        bar.add(tools);

        setJMenuBar(bar);
    }


    //GUI form
    private void createForm() {

        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(12, 8, 12, 8);

        c.anchor = GridBagConstraints.WEST;


        addRow(panel, c, 0,"Customer Name:", customerNameField);

        addRow(panel, c, 1,"Select Product:", productComboBox);

        addRow(panel, c, 2,"Enter Price (R):", priceField);


        JButton processButton = new JButton("PROCESS");

        processButton.setPreferredSize(new Dimension(200, 35));

        processButton.addActionListener(e -> processSale());


        c.gridx = 1;

        c.gridy = 3;

        c.anchor = GridBagConstraints.CENTER;

        panel.add(processButton, c);


        JLabel rates = new JLabel("VAT: 15%     Commission: 5%");

        c.gridx = 0;

        c.gridy = 4;

        c.gridwidth = 2;

        c.anchor = GridBagConstraints.CENTER;

        panel.add(rates, c);


        add(panel);
    }


    //Labels and input fields
    private void addRow(JPanel panel,GridBagConstraints c,int row,String label,JComponent field) {

        c.gridwidth = 1;

        c.gridx = 0;

        c.gridy = row;

        c.weightx = 0;

        c.fill = GridBagConstraints.NONE;

        panel.add(new JLabel(label), c);


        c.gridx = 1;

        c.weightx = 1;

        c.fill = GridBagConstraints.HORIZONTAL;

        panel.add(field, c);

        c.fill = GridBagConstraints.NONE;
    }


    //Process the sale
    private void processSale() {

        String customer = customerNameField.getText().trim();

        String product =(String) productComboBox.getSelectedItem();

        String enteredPrice = priceField.getText().trim();


        if (customer.isEmpty()) {

            showError("Please enter the customer's name.");

            customerNameField.requestFocus();

            return;
        }


        if (enteredPrice.isEmpty()) {

            showError("Please enter the product price.");

            priceField.requestFocus();

            return;
        }


        double price;

        try {

            price = Double.parseDouble(enteredPrice);

            if (!Double.isFinite(price) || price <= 0) {

                showError("Enter a valid price greater than zero.");

                priceField.requestFocus();

                return;
            }

        } catch (NumberFormatException ex) {

            showError("Price must be a number, for example 3500.");

            priceField.requestFocus();

            return;
        }


        //Calculation object
        ProductCalculations calc = new ProductCalculations(price);


        double vat = calc.GetVat();

        double commission = calc.GetCommission();

        double total = calc.GetTotal();


        //Display results
        String result = "Customer Name: " + customer + "\nProduct: " + product + "\nVAT: R " + money.format(vat) + "\nCOMMISSION: R " + money.format(commission) + "\nTOTAL: R " + money.format(total);


        try {

            saveSale(customer, product, vat, commission, total);

            JOptionPane.showMessageDialog(this, result, "Sale Details", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {

            JOptionPane.showMessageDialog(this, "Could not save the sale: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    //Saves information to data.txt
    private void saveSale(String customer, String product, double vat, double commission, double total)
            
            throws IOException {

        try (BufferedWriter writer = Files.newBufferedWriter( Path.of("data.txt"), StandardOpenOption.CREATE, StandardOpenOption.APPEND
                )) {

            writer.write("DATA LOG");

            writer.newLine();

            writer.write("****************************************");

            writer.newLine();

            writer.write("Customer Name: " + customer);

            writer.newLine();

            writer.write("Product: " + product);

            writer.newLine();

            writer.write("VAT: R " + money.format(vat));

            writer.newLine();

            writer.write("COMMISSION: R " + money.format(commission));

            writer.newLine();

            writer.write("TOTAL: R " + money.format(total));

            writer.newLine();

            writer.write("****************************************");

            writer.newLine();

            writer.newLine();
        }
    }


    //Clears all input fields
    private void clearForm() {

        customerNameField.setText("");

        priceField.setText("");

        productComboBox.setSelectedIndex(0);

        customerNameField.requestFocus();
    }


    //Display error messages
    private void showError(String message) {

        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }
}

