package br.com.zenon.fraud;

import java.io.IOException;
import java.util.List;

public class DBMain {

    void main() throws IOException {
        TransactionSQLRepository repository = new TransactionSQLRepository();
        var ingestor = new TransactionIngestor();
        long start = System.currentTimeMillis();

        List<Transaction> transactions = ingestor.ingest("data/PS_20174392719_1491204439457_log.csv", 10000);
        IO.println("Total de transações: " + transactions.size());
        transactions.forEach(repository::save);
        long end = System.currentTimeMillis();
        long totalTime = (end - start);

        IO.println("Tempo de processamento: " + totalTime);
    }
}
