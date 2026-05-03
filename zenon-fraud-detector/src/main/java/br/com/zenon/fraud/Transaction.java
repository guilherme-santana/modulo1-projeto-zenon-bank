package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.util.Optional;

public record Transaction(
        int step,
        TransactioType type,
        BigDecimal amount,
        OriginCustomer origin,
        DestCustomer destination,
        Integer isFraud,
        Integer isFlaggedFraud
) {

    public Transaction {

        if (step < 1) {
            throw new IllegalArgumentException("step should be greater than 0");
        }
        Optional.ofNullable(type).orElseThrow(() -> new IllegalArgumentException("Invalid or non-existent type"));
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("amount should be positive");
        }
        Optional.ofNullable(origin.getName()).orElseThrow(() -> new IllegalArgumentException("name origin should not be null or empty"));
        if (origin.getName().isBlank()) {
            throw new IllegalArgumentException("name origin should not be null or empty");
        }
        if (origin.getNewBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("new balance origin should be positive");
        }
        if (origin.getOldBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("old balance origin should be positive");
        }
        Optional.ofNullable(destination.getName()).orElseThrow(() -> new IllegalArgumentException("name destination should not be null or empty"));
        if (destination.getName().isBlank()) {
            throw new IllegalArgumentException("name destination should not be null or empty");
        }
        if (destination.getNewBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("new balance destination should be positive");
        }
        if (destination.getOldBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("old balance destination should be positive");
        }
        Optional.ofNullable(isFraud).orElseThrow(() -> new IllegalArgumentException("isFraud should not be null"));
        Optional.ofNullable(isFlaggedFraud).orElseThrow(() -> new IllegalArgumentException("isFlaggedFraud should not be null"));
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
