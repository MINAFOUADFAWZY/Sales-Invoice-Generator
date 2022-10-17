package com.frame.model;
import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;



public class InvoicesTable extends AbstractTableModel {

    private final DateFormat dataformat = new SimpleDateFormat("dd-MM-yyyy");
   private ArrayList<InvoiceLine> invoiceList ;

   public InvoicesTable(ArrayList<InvoiceLine> invoiceList){
        this.invoiceList = invoiceList;
    }

    public InvoicesTable(){}
    @Override
    public String getColumnName(int col) {
        switch (col) {
            case 0 -> {
                return "No.";
            }
            case 1 -> {
                return "Date";
            }
            case 2 -> {
                return "Customer";
            }
            case 3 -> {
                return "Total";
            }
        }
        return "";
    }
    @Override
    public int getColumnCount() {
        return 4;
    }
    public boolean isCellEditable(int row, int col)
    { return true; }
    @Override
    public Object getValueAt(int data, int col) {
        InvoiceLine invoice;
        invoice = invoiceList.get(data);
        fireTableCellUpdated(data, col);
        switch (col) {
            case 0 -> {
                return invoice.getInvNu();
            }
            case 1 -> {
                return dataformat.format(invoice.getInvD());
            }
            case 2 -> {
                return invoice.getInvCus();
            }
            case 3 -> {
                return invoice.getInvT();
            }

        }
        return "";
    }
    @Override
    public int getRowCount() {
        return invoiceList.size();
    }


}