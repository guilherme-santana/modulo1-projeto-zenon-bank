package br.com.zenon.fraud;

import java.math.BigDecimal;

public class DestCustomer extends Customer{
    public DestCustomer(String name, BigDecimal oldBalance, BigDecimal newBalance) {
        super(name, oldBalance, newBalance);
    }
}
