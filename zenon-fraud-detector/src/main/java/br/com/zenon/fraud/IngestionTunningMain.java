package br.com.zenon.fraud;

import java.io.IOException;

public class IngestionTunningMain {

    void main() throws IOException {
        TransactionSQLRepository repository = new TransactionSQLRepository();
        var ingestor = new TransactionIngestor();
        long start = System.currentTimeMillis();

        ingestor.readAsBatch("data/PS_20174392719_1491204439457_log.csv", repository::saveAll);

        long end = System.currentTimeMillis();
        long totalTime = (end - start);

        IO.println("Tempo de processamento: " + totalTime);
    }
}
