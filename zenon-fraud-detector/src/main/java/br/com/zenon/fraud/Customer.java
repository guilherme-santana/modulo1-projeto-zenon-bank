package br.com.zenon.fraud;

import java.math.BigDecimal;

public abstract class Customer {
    private final String name;
    private final BigDecimal oldBalance;
    private final BigDecimal newBalance;

    public Customer(String name, BigDecimal oldBalance, BigDecimal newBalance) {
        this.name = name;
        this.oldBalance = oldBalance;
        this.newBalance = newBalance;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getOldBalance() {
        return oldBalance;
    }

    public BigDecimal getNewBalance() {
        return newBalance;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", oldBalance=" + oldBalance +
                ", newBalance=" + newBalance +
                '}';
    }
}
