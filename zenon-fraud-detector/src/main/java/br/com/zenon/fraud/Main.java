package br.com.zenon.fraud;

import java.util.List;

public class Main {
    static void main() {
        long startTime = System.currentTimeMillis();

        TransactionIngestor transactionIngestor = new TransactionIngestor();
        List<Transaction> ingest = transactionIngestor.ingest("data/paysim_with_bad_data.csv", 1000);

        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);

        ingest.stream()
                .limit(10)
                .forEach(IO::println);

        System.out.println("Processamento concluído em: " + duration + "ms");
        System.out.println("Total de registros com sucesso: " + ingest.size());
    }
}
