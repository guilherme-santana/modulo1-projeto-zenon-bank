package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class FraudAnalyzer {
    NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));

    public List<Transaction> transactionsIsFraud(List<Transaction> transactions) {
        return transactions.stream()
                .filter(f -> f.isFraud() == 1)
                .toList();
    }

    public void fraudsGreaterAmount(List<Transaction> transactions) {

        transactionsIsFraud(transactions)
                .stream()
                .sorted(Comparator.comparing(Transaction::amount).reversed())
                .limit(3)
                .map(t -> numberFormat.format(t.amount()))
                .toList()
                .forEach(System.out::println);
    }

    public void suspiciousCustomers(List<Transaction> transactions) {
        transactionsIsFraud(transactions)
                .stream()
                .sorted(Comparator.comparing(Transaction::amount).reversed())
                .map(t -> t.origin().getName())
                .distinct()
                .limit(5)
                .forEach(System.out::println);
    }

    public String sumAmountTotalFrauds(List<Transaction> transactions) {
        return numberFormat.format(
                transactionsIsFraud(transactions)
                        .stream()
                        .map(Transaction::amount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }

    public void quantityFraudByType(List<Transaction> transactions) {
        transactionsIsFraud(transactions)
                .stream()
                .collect(Collectors.groupingBy(
                        Transaction::type,
                        Collectors.counting()
                )).forEach((type, count) ->
                        System.out.printf("- %s: %d ocorrências%n", type, count)
                );
    }

    public void printQuantity(String msg, List<Transaction> transactions) {
        IO.println(msg + transactions.stream()
                .toList()
                .size()
        );
    }


}
