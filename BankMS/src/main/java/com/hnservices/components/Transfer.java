//1.3.2 Creation of the Transfer class
package com.hnservices.components;

import java.sql.Date;

public class Transfer extends Flow {

    private int accountNumber;

    public Transfer(String comment, double amount,int accountNumber, int targetAccountNumber, boolean effect,
                    Date dateFlow) {
        super(comment, amount, targetAccountNumber, effect, dateFlow);
        this.accountNumber = accountNumber;
    }



    public int getAccountNumber() {
        return accountNumber;
    }



    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }



    @Override
    public String toString() {
        return "Transfer: " +
                "Comment = '" + getComment() +
                "' Identifier = " + getIdentifier() +
                " Amount = " + getAmount() +
                " AccountNumber = " + getAccountNumber() +
                " TargetAccountNumber = " + getTargetAccountNumber() +
                " Effect = " + isEffect() +
                " DateFlow = " + getDateOfFlow();
    }



}