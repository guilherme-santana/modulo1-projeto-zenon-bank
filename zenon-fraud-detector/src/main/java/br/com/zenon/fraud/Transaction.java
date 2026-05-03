package br.com.zenon.fraud;

import java.math.BigDecimal;

public record Transaction(
        Integer step,
        TransactioType type,
        BigDecimal amount,
        OriginCustomer origin,
        DestCustomer destination,
        Integer isFraud,
        Integer isFlaggedFraud
) {

    public Transaction {
        if (step == null || step < 1) {
            throw new IllegalArgumentException("step should be greater than 0");
        }
        if (type == null) {
            throw new IllegalArgumentException("Invalid or non-existent type");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("amount should be positive");
        }
        if (origin.getName().isBlank() || origin.getName().isEmpty() || origin.getName() == null) {
            throw new IllegalArgumentException("name origin should not be null or empty");
        }
        if (origin.getNewBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("new balance origin should be positive");
        }
        if (origin.getOldBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("old balance origin should be positive");
        }
        if (destination.getName().isBlank() || destination.getName().isEmpty() || destination.getName() == null) {
            throw new IllegalArgumentException("name destination should not be null or empty");
        }
        if (destination.getNewBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("new balance destination should be positive");
        }
        if (destination.getOldBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("old balance destination should be positive");
        }
        if (isFraud == null) {
            throw new IllegalArgumentException("isFraud should not be null");
        }
        if (isFlaggedFraud == null) {
            throw new IllegalArgumentException("isFlaggedFraud should not be null");
        }

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
