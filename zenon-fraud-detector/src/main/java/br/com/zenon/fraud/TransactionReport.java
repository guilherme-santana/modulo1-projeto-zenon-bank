package br.com.zenon.fraud;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class TransactionReport {


    public void processFileEfficiently(String local) throws IOException {
        record Accumulator(long lines, long frauds, BigDecimal sum) {
        }
        var locale = Locale.of(local);
        NumberFormat integerFormatter = NumberFormat.getIntegerInstance(locale);
        NumberFormat currencyInstance = DecimalFormat.getCurrencyInstance(locale);
        currencyInstance.setCurrency(Currency.getInstance("USD"));

        var resourceBundle = ResourceBundle.getBundle("report", locale);
        String msgTotalTransactions = resourceBundle.getString("label.total.transactions");
        String msgTotalFrauds = resourceBundle.getString("label.total.frauds");
        String msgTotalAmount = resourceBundle.getString("label.total.amount");

        try (Stream<String> lines = Files.lines(Path.of("data/PS_20174392719_1491204439457_log.csv"))) {
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

            System.out.println(msgTotalTransactions.concat(integerFormatter.format(result.lines())));
            System.out.println(msgTotalFrauds.concat(integerFormatter.format(result.frauds())));
            System.out.println(msgTotalAmount.concat(currencyInstance.format(result.sum())));

        } catch (Exception e) {
            throw new RuntimeException("Error processing file: ", e);
        }

    }
}
