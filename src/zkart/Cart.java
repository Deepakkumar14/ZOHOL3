package zkart;

import java.util.ArrayList;
import java.util.List;

public class Cart {
        private long InvoiceNumber;

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    private double totalAmount;

    public long getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(long invoiceNumber) {
        InvoiceNumber = invoiceNumber;
    }
    public ArrayList<Products> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(ArrayList<Products> invoiceList) {
        this.invoiceList = invoiceList;
    }

    private ArrayList<Products> invoiceList;


}
