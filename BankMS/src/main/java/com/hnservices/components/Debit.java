//1.3.2 Creation of the Debit class

package com.hnservices.components;

import java.sql.Date;

public class Debit extends Flow {

    public Debit(String comment, double amount, int targetAccountNumber, boolean effect,
                 Date dateFlow) {
        super(comment, amount, targetAccountNumber, effect, dateFlow);
    }

    @Override
    public String toString() {
        return "Debit: " +
                "Comment = '" + getComment() +
                "' Identifier = " + getIdentifier() +
                " Amount = " + getAmount() +
                " TargetAccountNumber = " + getTargetAccountNumber() +
                " Effect = " + isEffect() +
                " DateFlow = " + getDateOfFlow();
    }

}