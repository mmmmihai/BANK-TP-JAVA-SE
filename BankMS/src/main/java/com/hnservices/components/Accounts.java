package com.hnservices.components;


import jakarta.xml.bind.annotation.XmlRootElement;

// 1.2.1 Creation of the account class
@XmlRootElement
public abstract class Accounts {

    protected String label;
    protected double balance;
    protected int accountNumber;
    protected Clients client;
    private static int a = 1;

    public Accounts(String label, Clients client) {
        this.label = label;
        this.client = client;
        this.accountNumber = a++;
        this.balance = 0.0;
    }

    public Accounts() {
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getBalance() {
        return balance;
    }



    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Clients getClient() {
        return client;
    }

    public void setClient(Clients client) {
        this.client = client;
    }

//    public void setBalance(double amount) {
//        this.balance += amount;
//    }

// 1.3.5 Updating accounts

    public void setBalance(Flow flow) {
        double amount = flow.getAmount();
        boolean isEffect = flow.isEffect();

        if(flow instanceof Credit) {
            this.balance += amount;
        } else if (flow instanceof Debit){
            this.balance -= amount;
        } else if(flow instanceof Transfer) {
            Transfer transfer = (Transfer) flow;
            int targetAccountNumber = transfer.getTargetAccountNumber();
            int issuingAccountNumber = transfer.getAccountNumber();

            if (this.accountNumber == targetAccountNumber) {
                this.balance += amount;
            } else if (this.accountNumber == issuingAccountNumber) {
                this.balance -= amount;
            }
        }
    }

    @Override
    public String toString() {
        return client + " AccountNumber: " + accountNumber + " Type - " + label  +  " Balance: " + balance;
    }
}
