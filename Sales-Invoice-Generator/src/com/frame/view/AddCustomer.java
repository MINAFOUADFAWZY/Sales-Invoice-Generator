package com.frame.view;
import javax.swing.*;
import java.awt.*;




public class AddCustomer extends JDialog {


    private final JTextField invDateField;
    private final JTextField createNCField;
    private final JLabel invNumberLabel2;




    public JTextField getCreateNCField() {

        return createNCField;
    }

    public AddCustomer(MyFrame frame) {



        setLayout(new FlowLayout());

        JLabel invNumberLabel = new JLabel("NO");
        add(invNumberLabel);
        invNumberLabel2 = new JLabel(".       ");
        add(invNumberLabel2);


        JLabel createNCLabel = new JLabel("Customer Name");
        add(createNCLabel);


        createNCField = new JTextField(15);
        add(createNCField);

        JLabel invDateLabel = new JLabel("Date        ");
        add(invDateLabel);

        invDateField = new JTextField(15);
        add(invDateField);


        JButton createBtn = new JButton("Create New Customer");
        createBtn.setActionCommand("create");

        createBtn.addActionListener(frame.myListener);
        add(createBtn);

        JButton cancelBtn = new JButton("Cancel Customer");
        cancelBtn.setActionCommand("Cancel Customer");
        cancelBtn.addActionListener(frame.myListener);
        add(cancelBtn);



        setTitle("Add New Customer");
        setSize(610, 110);
        setLocation(400, 250);
        setResizable(false);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
    }

   //getter and setter

    public JTextField getInvDateField() {
        return invDateField;
    }

    public JLabel getInvNumberLabel2() {
        return invNumberLabel2;
    }








}
