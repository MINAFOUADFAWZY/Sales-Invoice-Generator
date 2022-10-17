package com.frame.controller;

import com.frame.model.InvoiceLine;
import com.frame.model.InvoiceItem;
import com.frame.model.InvoiceHeader;
import com.frame.model.InvoicesTable;
import com.frame.view.MyFrame;
import com.frame.view.AddCustomer;
import com.frame.view.AddOrder;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Listener implements ActionListener, ListSelectionListener {
    private final MyFrame Frame;
    public static DateFormat dataformate = new SimpleDateFormat("dd-MM-yyyy");

      private AddCustomer newCustomer;
    private AddOrder neworder;


    public Listener(MyFrame frame) {
        this.Frame = frame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {

            case "S" -> saveFile();
            case "New Inv" -> newInv();
            case "Delete" -> deleteInvoice();
            case "Delete Item" -> deleteItem();
            case "create" -> create();
            case "CreateNewItem" -> createNewItem();
            case "Cancel Item" -> cancelItem();
            case "Cancel Customer" -> cancelCustomer();
            case "New Item" -> createItem();
            case "L" -> loadFile();
        }


    }

    private void createNewItem() {

        int currInvoice;
        currInvoice = Frame.getInvoiceData().getSelectedRow();
        String itemName = neworder.getItemNameField().getText();
        String itemPrAsString = neworder.getItemPriceField().getText();
        String countString = neworder.getCountField().getText();
        double itemPrice = 0;
        int itemCount = 0;
        try {
            itemPrice = Double.parseDouble(itemPrAsString);
        }
        catch (NumberFormatException exception) {  neworder.dispose();
            neworder = null;
            JOptionPane.showMessageDialog(Frame, "Error Empty price Field Not Allowed.", "", JOptionPane.ERROR_MESSAGE);
        }
        try {
            itemCount = Integer.parseInt(countString);
        }
        catch (NumberFormatException exception){
            JOptionPane.showMessageDialog(Frame, "Error Empty count Field Not Allowed", "", JOptionPane.ERROR_MESSAGE);
        }

        if (currInvoice != -1 && neworder != null){
            InvoiceLine invoiceNum = Frame.getInvoiceList().get(currInvoice);
            InvoiceItem invLine = new InvoiceItem(invoiceNum, itemName, itemPrice, itemCount);
            Frame.getItems().add(invLine);
            Frame.getInvoicesTable().fireTableDataChanged();
            Frame.getInvoiceData().setRowSelectionInterval (currInvoice, currInvoice);
            Frame.getInvoiceItemsTable().fireTableDataChanged();
            neworder.dispose();
            neworder = null;
        }



    }
    private void createItem() {
        neworder = new AddOrder(Frame);
        neworder.setVisible(true);
    }
    private void create() {
        int invNo = 0;


        for (InvoiceLine invoice : Frame.getInvoiceList()) {
            if (invoice.getInvNu() > invNo)
                invNo = invoice.getInvNu();
        }
        invNo++;
        String cutstomerName = newCustomer.getCreateNCField().getText();
        String invoDate = newCustomer.getInvDateField().getText();
        Date invDate = null;
        try {

            invDate = dataformate.parse(invoDate);

        } catch (ParseException exception) {newCustomer.dispose();newCustomer = null;
            JOptionPane.showMessageDialog(Frame, "Date format should be: dd-MM-yyyy", "", JOptionPane.ERROR_MESSAGE);
            return;

        }

        if (cutstomerName.length() == 0) { newCustomer.dispose();
            JOptionPane.showMessageDialog(Frame, "Name cannot be empty", "", JOptionPane.ERROR_MESSAGE);


        } else {
            InvoiceLine invHeader = new InvoiceLine(invNo, invDate, cutstomerName);
            Frame.getInvoiceList().add(invHeader);
            Frame.getInvoicesTable().fireTableDataChanged();
            newCustomer.dispose();
            newCustomer = null;
        }


    }


    private void loadFile() {
        JFileChooser fc = new JFileChooser();
        try{ int l;JOptionPane.showMessageDialog(Frame,"Select InvoiceLine Header ","", JOptionPane.WARNING_MESSAGE);
         l = fc.showOpenDialog(Frame);
         if (l == JFileChooser.APPROVE_OPTION){
             File invoiceHeader;
             invoiceHeader = fc.getSelectedFile();
             Path pathInvoiceHeader = Paths.get(invoiceHeader.getAbsolutePath());
             ArrayList<InvoiceLine>invoiceList ;
             invoiceList = new ArrayList<>();
             List<String> invoicesData;
             invoicesData = Files.readAllLines(pathInvoiceHeader);
             for (String invoiceData : invoicesData ){
                 String[] data = invoiceData.split(",");
                 String firstNo = data[0];
                 String secDate = data[1];
                 String thirdCustomer = data[2];

                 int number = Integer.parseInt(firstNo);
                 Date dateInHeader = dataformate.parse(secDate);
                 InvoiceLine invoHeader = new InvoiceLine(number, dateInHeader, thirdCustomer);
                 invoiceList.add(invoHeader);

             }
             Frame.setInvoiceList(invoiceList);
             JOptionPane.showMessageDialog(Frame, "Now select Items file ", "", JOptionPane.WARNING_MESSAGE);
             l = fc.showOpenDialog(Frame);
             if (l == JFileChooser.CANCEL_OPTION){
                 JOptionPane.showMessageDialog(Frame, "Please Select (.CSV) File.", "", JOptionPane.ERROR_MESSAGE);
             }
             else if (l == JFileChooser.APPROVE_OPTION){
                 File fileOfLine = fc.getSelectedFile();
                 Path pathForLine = Paths.get(fileOfLine.getAbsolutePath());

                 ArrayList<InvoiceItem> product = new ArrayList<>();
                 List<String> itemsList = Files.readAllLines(pathForLine);

                 for (String item : itemsList){
                     String[] itemData = item.split(",");
                     String frstNo = itemData[0];
                     String secItnam = itemData[1];
                     String thrdItPri = itemData[2];
                     String frthItCon = itemData[3];

                     int numVal;
                     numVal = Integer.parseInt(frstNo);
                     double itemPrice = Double.parseDouble(thrdItPri);
                     int itemCount = Integer.parseInt(frthItCon);
                     InvoiceLine invoHeader = Frame.getNum(numVal);

                     InvoiceItem invLine = new InvoiceItem(invoHeader, secItnam, itemPrice,itemCount);
                     invoHeader.getInvItems().add(invLine);
                 }

                 Frame.setItems(product);


             }
             InvoicesTable inTable;

             inTable = new InvoicesTable( invoiceList);
             Frame.setInvTable(inTable);
             Frame.getInvoiceData().setModel(inTable);

         }

        } catch (NumberFormatException | ParseException | IOException e) {
            JOptionPane.showMessageDialog(Frame, "Failed Loading CSV File.\n Please Select file in (.CSV) .", "", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void deleteInvoice() {
        int invID;
        invID = Frame.getInvoiceData().getSelectedRow();
        if (invID != -1){
            Frame.getInvoiceList().remove(invID);
            Frame.getInvoicesTable().fireTableDataChanged();
            Frame.getItemsJTable().setModel(new InvoiceHeader(new ArrayList<>()));
            Frame.getNo().setText("");
            Frame.getCusName().setText("");
            Frame.getInvDate().setText("");
            Frame.getInvTotal().setText("");
        }
    }
    public void valueChanged(ListSelectionEvent ev) {
        int selInv = Frame.getInvoiceData().getSelectedRow();
        if (selInv != -1) {
            InvoiceLine selectedInv;
            selectedInv = Frame.getInvoiceList().get(selInv);
            ArrayList<InvoiceItem> items = selectedInv.getInvItems();
            InvoiceHeader invoiceItemsTable = new InvoiceHeader(items);
            Frame.getItemsJTable().setModel(invoiceItemsTable);
            Frame.setItems(items);
            Frame.getCusName().setText(selectedInv.getInvCus());
            Frame.getNo().setText("" + selectedInv.getInvNu());
            Frame.getInvDate().setText(dataformate.format(selectedInv.getInvD()));
            Frame.getInvTotal().setText("" + selectedInv.getInvT());
        }
    }

    private void newInv() {
        newCustomer = new AddCustomer(Frame);
        newCustomer.setVisible(true);
       try {
            int invNo = 0;
            for (InvoiceLine invoice : Frame.getInvoiceList()) {
               if (invoice.getInvNu()> invNo)
                    invNo = invoice.getInvNu();
            }
           invNo++;
           newCustomer.getInvNumberLabel2().setText("" + invNo);
       }
       catch(Exception ep){
           JOptionPane.showMessageDialog(Frame,"Empty customer data Not Allowed.", "", JOptionPane.ERROR_MESSAGE);
           newCustomer.setVisible(false);
      }


    }
    private void saveFile() {
        JOptionPane.showMessageDialog(Frame, "Choose location for InvoiceLine file...","", JOptionPane.INFORMATION_MESSAGE);
        JFileChooser chooser = new JFileChooser();
        try {
            int saV = chooser.showSaveDialog(Frame);
            if (saV == JFileChooser.APPROVE_OPTION){
                File invF = chooser.getSelectedFile();
                FileWriter fileSA = new FileWriter(invF);
                ArrayList<InvoiceLine> headersList = Frame.getInvoiceList();


                StringBuilder invData = new StringBuilder();
                StringBuilder records = new StringBuilder();

                for (InvoiceLine header: headersList){
                    invData.append(header.toString());
                    invData.append("\n");
                    for (InvoiceItem item: header.getInvItems()){
                        records.append(item.toString());
                        records.append("\n");
                    }
                }

                JOptionPane.showMessageDialog(Frame, "choose location for Items file...","", JOptionPane.INFORMATION_MESSAGE);
                chooser.showSaveDialog(Frame);
                File fI = chooser.getSelectedFile();
                FileWriter FI = new FileWriter(fI);
                invData = new StringBuilder(invData.substring(0, invData.length() - 1));
                fileSA.write(invData.toString());
                fileSA.close();
                records = new StringBuilder(records.substring(0, records.length() - 1));
                FI.write(records.toString());
                FI.close();
                JOptionPane.showMessageDialog(Frame, "Save Done","", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (IOException e)

        {
            JOptionPane.showMessageDialog(Frame, "Failed Save CSV File.\n Please Save file in (.CSV) .", "", JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception exp){
            JOptionPane.showMessageDialog(Frame, "Saving Failed.", "", JOptionPane.ERROR_MESSAGE);
        }

    }





    private void cancelItem() {
        neworder.dispose();
        neworder = null;
    }

        private void cancelCustomer() {
            newCustomer.dispose();
            newCustomer = null;
    }


    private void deleteItem() {
        int selectedInvoice = Frame.getInvoiceData().getSelectedRow();
        int selectedItemIndex = Frame.getItemsJTable().getSelectedRow();
        if (selectedItemIndex != -1){
            Frame.getItems().remove(selectedItemIndex);
            Frame.getInvoicesTable().fireTableDataChanged();
            Frame.getInvoiceData().setRowSelectionInterval(selectedInvoice, selectedInvoice);
            Frame.getInvoiceItemsTable().fireTableDataChanged();
        }
    }


}
