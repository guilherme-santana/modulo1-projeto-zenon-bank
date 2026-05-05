package br.com.zenon.fraud;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

public class TransactionReport {


    public void processFileEfficiently() throws IOException {
        record Accumulator(long lines, long frauds, BigDecimal sum) {
        }

        try (var lines = Files.lines(Path.of("data/PS_20174392719_1491204439457_log.csv"))) {
            Accumulator result = lines.skip(1)
                    .map(line -> line.split(",", 11))
                    .reduce(
                            new Accumulator(0, 0, BigDecimal.ZERO),
                            (accumulator, parts) -> {
                                try {
                                    BigDecimal amount = new BigDecimal(parts[2].trim());
                                    boolean isFraud = "1".equals(parts[9].trim());
                                    return new Accumulator(
                                            accumulator.lines() + 1,
                                            isFraud ? accumulator.frauds() + 1 : accumulator.frauds(),
                                            accumulator.sum().add(amount)
                                    );
                                } catch (Exception e) {
                                    return accumulator;
                                }
                            },
                            (accumulator, accumulator2) -> null
                    );
            System.out.println("Total Linhas: " + result.lines());
            System.out.println("Total Fraudes: " + result.frauds());
            System.out.println("Valor total transacionado: " + result.sum().toPlainString());

        } catch (Exception e) {
            throw new RuntimeException("Error processing file: ", e);
        }

    }
}
