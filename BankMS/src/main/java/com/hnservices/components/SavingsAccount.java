package com.hnservices.components;

import jakarta.xml.bind.annotation.XmlRootElement;

// 1.2.2 Creation of the SavingsAccount
@XmlRootElement
public class SavingsAccount extends Accounts {

    public SavingsAccount(Clients client) {
        super("Savings Account", client);
    }

}