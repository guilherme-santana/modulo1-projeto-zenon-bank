package br.com.zenon.fraud;

import java.util.List;

public class Main {
    static void main() {
        long startTimeLoadFile = System.currentTimeMillis();
        TransactionListRepository transactionListRepository = new TransactionListRepository();

        TransactionIngestor transactionIngestor = new TransactionIngestor();
        List<Transaction> ingest = transactionIngestor.ingest("data/PS_20174392719_1491204439457_log.csv", 100000);

        long endTimeLoadFile = System.currentTimeMillis();
        long durationLoadFile = (endTimeLoadFile - startTimeLoadFile);

        long startTimeFindTransaction = System.currentTimeMillis();
        IO.println(transactionListRepository.findTransactionByCustomerOrigen(ingest, "C1868032458"));

        long endTimeFindTransaction = System.currentTimeMillis();
        long durationFindTransaction = (endTimeFindTransaction - startTimeFindTransaction);

        IO.println("\nProcessamento do carregamento do arquivo concluído em: " + durationLoadFile + "ms");
        IO.println("Total de registros processados com sucesso: " + ingest.size());

        IO.println("\nProcessamento da busca de transações concluído em: " + durationFindTransaction + "ms");

    }
}
