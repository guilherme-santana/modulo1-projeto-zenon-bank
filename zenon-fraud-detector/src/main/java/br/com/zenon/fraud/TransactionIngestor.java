package br.com.zenon.fraud;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransactionIngestor {

    public static final int LINE_BATCH_SIZE = 2_500;
    public static final int FRAUD_LIMIT = 10_000;

    public void readAsBatch(String fileName, Consumer<List<Transaction>> batchConsumer) {
        Path path = Path.of(fileName);
        try (ExecutorService executor = Executors.newFixedThreadPool(10);
             Stream<String> lines = Files.lines(path).skip(1)) {

            Iterator<String> iterator = lines.iterator();

            List<String> lineBatch = new ArrayList<>(LINE_BATCH_SIZE);
            while (iterator.hasNext()) {
                String line = iterator.next();
                lineBatch.add(line);

                if (lineBatch.size() >= LINE_BATCH_SIZE) {
                    List<String> currentLineBatch = List.copyOf(lineBatch);
                    executor.submit(() -> executeBatch(currentLineBatch, batchConsumer));
                    lineBatch.clear();
                }
            }
            if (!lineBatch.isEmpty()) {
                List<String> currentLineBatch = List.copyOf(lineBatch);
                executor.submit(() -> executeBatch(currentLineBatch, batchConsumer));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void executeBatch(List<String> lineBatch, Consumer<List<Transaction>> batchConsumer) {
        List<Transaction> transactionBatch = lineBatch
                .stream()
                .map(line -> {
                    try {
                        String[] parts = line.split(",");
                        return new Transaction(
                                Integer.parseInt(parts[0].trim()),
                                TransactioType.fromString(parts[1].trim()),
                                new BigDecimal(parts[2].trim()),
                                new OriginCustomer(parts[3].trim(), new BigDecimal(parts[4].trim()), new BigDecimal(parts[5].trim())),
                                new DestCustomer(parts[6].trim(), new BigDecimal(parts[7].trim()), new BigDecimal(parts[8].trim())),
                                Integer.parseInt(parts[9].trim()),
                                Integer.parseInt(parts[10].trim())
                        );
                    } catch (IllegalArgumentException e) {
                        System.err.printf("ERROR: [%s] | REASON: %s%n", line, e.getMessage());
                        return null;
                    }
                })
                .toList();
        batchConsumer.accept(transactionBatch);
    }


    public List<Transaction> ingest(String fileName, int limit) {
        try (Stream<String> lines = Files.lines(Path.of(fileName))) {
            return lines.skip(1)
                    .limit(limit)
                    .map(line -> {
                        try {
                            String[] parts = line.split(",");
                            return new Transaction(
                                    Integer.parseInt(parts[0].trim()),
                                    TransactioType.fromString(parts[1].trim()),
                                    new BigDecimal(parts[2].trim()),
                                    new OriginCustomer(parts[3].trim(), new BigDecimal(parts[4].trim()), new BigDecimal(parts[5].trim())),
                                    new DestCustomer(parts[6].trim(), new BigDecimal(parts[7].trim()), new BigDecimal(parts[8].trim())),
                                    Integer.parseInt(parts[9].trim()),
                                    Integer.parseInt(parts[10].trim())
                            );
                        } catch (IllegalArgumentException e) {
                            System.err.printf("ERROR: [%s] | REASON: %s%n", line, e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
