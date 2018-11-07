/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Entity;

/* Database sequence
    #1: paymentID (int 11)
    #2: paymentMode (varchar 100)
    #3: paymentTransaction (varchar 100)
    #4: paymentAmount (double)
 */

public class Payment {

   private int paymentID;
   private String paymentMode;
   private String paymentTransaction;
   private double paymentAmount;

    public Payment(int paymentID, String paymentMode, String paymentTransaction, double paymentAmount) {
        this.paymentID = paymentID;
        this.paymentMode = paymentMode;
        this.paymentTransaction = paymentTransaction;
        this.paymentAmount = paymentAmount;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentTransaction() {
        return paymentTransaction;
    }

    public void setPaymentTransaction(String paymentTransaction) {
        this.paymentTransaction = paymentTransaction;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

}
