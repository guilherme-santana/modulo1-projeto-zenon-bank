package br.com.zenon.fraud;

import java.util.List;

public class Main {
    static void main() {
        long startTime = System.currentTimeMillis();

        TransactionIngestor transactionIngestor = new TransactionIngestor();
        List<Transaction> ingest = transactionIngestor.ingest("data/PS_20174392719_1491204439457_log.csv", 1000);

        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);

        ingest.stream()
                .limit(10)
                .forEach(IO::println);

        System.out.println("Processamento concluído em: " + duration + "ms");
        System.out.println("Total de registros: " + ingest.size());
    }
}
