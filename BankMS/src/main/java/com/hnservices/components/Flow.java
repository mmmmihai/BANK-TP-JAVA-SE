package com.hnservices.components;

//1.3.2 Creation of the Flow class
import java.sql.Date;

public abstract class Flow {

    private String comment;
    private int identifier;
    private double amount;
    private int targetAccountNumber;
    private boolean effect;
    private Date dateOfFlow;

    private static int f = 0;

    public Flow(String comment, double amount, int targetAccountNumber, boolean effect, Date dateOfFlow) {

        this.comment = comment;
        this.identifier = f++;
        this.amount = amount;
        this.targetAccountNumber = targetAccountNumber;
        this.effect = effect;
        this.dateOfFlow = dateOfFlow;

    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getTargetAccountNumber() {
        return targetAccountNumber;
    }

    public void setTargetAccountNumber(int targetAccountNumber) {
        this.targetAccountNumber = targetAccountNumber;
    }

    public boolean isEffect() {
        return effect;
    }

    public void setEffect(boolean effect) {
        this.effect = effect;
    }

    public Date getDateOfFlow() {
        return dateOfFlow;
    }

    public void setDateOfFlow(Date dateOfFlow) {
        this.dateOfFlow = dateOfFlow;
    }
}