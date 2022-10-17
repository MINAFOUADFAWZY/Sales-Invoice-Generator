package com.frame.view;
import javax.swing.*;
import java.awt.*;


public class AddOrder extends JDialog  {

    private JLabel countLabel;

    private JLabel itemPriceLabel;
    private JButton cancelItemBtn;
    private JLabel itemNameLabel;
    private JTextField itemPriceField;
    private JTextField itemNameField;
    private JButton createItemBtn;
    private JTextField countField;



    public AddOrder(MyFrame frame){


        setLayout(new FlowLayout());
        itemNameLabel = new JLabel("Item Name");
        add(itemNameLabel);
        itemNameField = new JTextField(15);
        add(itemNameField);
        itemPriceLabel = new JLabel("Item Price");
        add(itemPriceLabel);
        itemPriceField = new JTextField(15);
        add(itemPriceField);
        countLabel = new JLabel("Count");
        add(countLabel);
        countField = new JTextField(15);
        add(countField);

        createItemBtn = new JButton("Create New Item");
        createItemBtn.setActionCommand("CreateNewItem");
        createItemBtn.addActionListener(frame.myListener);
        add(createItemBtn);

        cancelItemBtn = new JButton("Cancel Item");
        cancelItemBtn.setActionCommand("Cancel Item");
        cancelItemBtn.addActionListener(frame.myListener);
        add(cancelItemBtn);



        setTitle("Add New Item");
        setSize(710, 105);
        setLocation(300, 250);
        setResizable(false);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
    }

    public JTextField getItemPriceField() {
        return itemPriceField;
    }



    public void setItemNameField(JTextField itemNameField) {
        this.itemNameField = itemNameField;
    }

    public JTextField getCountField() {
        return countField;
    }
    public JTextField getItemNameField() {
        return itemNameField;
    }

    public void setCountField(JTextField countField) {
        this.countField = countField;
    }
    public void setItemPriceField(JTextField itemPriceField) {
        this.itemPriceField = itemPriceField;
    }

}
