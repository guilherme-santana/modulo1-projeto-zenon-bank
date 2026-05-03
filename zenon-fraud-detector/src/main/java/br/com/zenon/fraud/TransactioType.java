package br.com.zenon.fraud;

public enum TransactioType {

    CASH_IN,
    CASH_OUT,
    DEBIT,
    PAYMENT,
    TRANSFER;

    public static TransactioType fromString(String type){
        try {
            return TransactioType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException|NullPointerException e) {
            return null;
        }
    }
}
