//1.3.2 Creation of the Credit class

package com.hnservices.components;

import java.sql.Date;

public class Credit extends Flow {

    public Credit(String comment, double amount, int targetAccountNumber, boolean effect,
                  Date dateFlow) {
        super(comment, amount, targetAccountNumber, effect, dateFlow);
    }

    @Override
    public String toString() {
        return "Credit: " +
                "Comment = '" + getComment() +
                "' Identifier = " + getIdentifier() +
                " Amount = " + getAmount() +
                " TargetAccountNumber = " + getTargetAccountNumber() +
                " Effect = " + isEffect() +
                " DateFlow = " + getDateOfFlow();
    }

}