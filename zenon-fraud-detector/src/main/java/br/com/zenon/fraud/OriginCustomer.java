package br.com.zenon.fraud;

import java.math.BigDecimal;

public class OriginCustomer extends Customer{
    public OriginCustomer(String name, BigDecimal oldBalance, BigDecimal newBalance) {
        super(name, oldBalance, newBalance);
    }
}
