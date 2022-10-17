package com.frame.model;

public class InvoiceItem {
    private final int count;
    private final InvoiceLine invoice;

    private final String itemName;
    private double itemTotal;
    private final double itemPrice;



    // Constructor
    public InvoiceItem(InvoiceLine invoice, String itemName, double itemPrice, int count) {
        this.invoice = invoice;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.count = count;
    }


    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }
    public int getCount() {
        return count;
    }

    public double getItemTotal() {
        return itemPrice * count;
    }



    @Override
    public String toString() {
        return  invoice.getInvNu() +
                ","  + itemName +  ","  + itemPrice +
                "," + count  + "," +
                itemTotal
                ;
    }

}
