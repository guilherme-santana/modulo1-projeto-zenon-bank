package br.com.zenon.fraud;

import java.math.BigDecimal;

public record Transaction(
        int step,
        TransactioType type,
        BigDecimal amount,
        OriginCustomer origin,
        DestCustomer destination,
        int isFraud,
        int isFlaggedFraud
) {

    public static Transaction fromCsv(
            int step,
            TransactioType type,
            BigDecimal amount,
            String nameOrig,
            BigDecimal oldbalanceOrg,
            BigDecimal newbalanceOrig,
            String nameDest,
            BigDecimal oldbalanceDest,
            BigDecimal newbalanceDest,
            int isFraud,
            int isFlaggedFraud
    ) {
        OriginCustomer originCustomer = new OriginCustomer(nameOrig, oldbalanceOrg, newbalanceOrig);
        DestCustomer destCustomer = new DestCustomer(nameDest, oldbalanceDest, newbalanceDest);
        return new Transaction(step, type, amount, originCustomer, destCustomer, isFraud, isFlaggedFraud);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "step=" + step +
                ", type=" + type +
                ", amount=" + amount +
                ", origin=" + origin +
                ", destination=" + destination +
                ", isFraud=" + isFraud +
                ", isFlaggedFraud=" + isFlaggedFraud +
                '}';
    }
}
