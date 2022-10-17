package com.frame.model;
import com.frame.controller.Listener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InvoiceLine {

    private final String InvoiceCustomer;

   private double InvoiceTotal;
    private final Date InvoiceDate;
    private final int InvoiceNo;
   private ArrayList <InvoiceItem> items;
   private final DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
// Constructor


    // getter and setter


    public double getInvT() {
        double invT = 0.0;
        for (int i = 0 ; i < getInvItems().size(); i++){
            invT += getInvItems().get(i).getItemTotal();
        }
        return invT;
    }
    public int getInvNu() {
        return InvoiceNo;
    }

    public Date getInvD() {
        return InvoiceDate;
    }

    public ArrayList<InvoiceItem> getInvItems(){if (items == null){items = new ArrayList<>();}
        return items;
    }
    public String getInvCus() {
        return InvoiceCustomer;
    }
    public InvoiceLine(int invNu, Date invD, String invCus){
        this.InvoiceNo = invNu;
        this.InvoiceDate = invD;
        this.InvoiceCustomer = invCus;


    }





    @Override
    public String toString() {
        return
                InvoiceNo + ","
                 + Listener.dataformate.format(InvoiceDate) +","
                + InvoiceCustomer +","
                + InvoiceTotal
                ;
    }


    public void setInvT(double invT) {
        this.InvoiceTotal = invT;
    }
}

