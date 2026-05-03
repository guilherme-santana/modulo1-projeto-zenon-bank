package br.com.zenon.fraud;

import java.util.List;

public class Main {
    static void main() {
        long startTime = System.currentTimeMillis();
        FraudAnalyzer fraudAnalyzer = new FraudAnalyzer();

        TransactionIngestor transactionIngestor = new TransactionIngestor();
        List<Transaction> ingest = transactionIngestor.ingest("data/PS_20174392719_1491204439457_log.csv", 50000);

        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);

        IO.println("### Relatório de fraudes ###\n");
        fraudAnalyzer.printQuantity("1. Quantidade de transações com fraude: ", fraudAnalyzer.transactionsIsFraud(ingest));

        IO.println("2. Fraudes com maior valor:");
        fraudAnalyzer.fraudsGreaterAmount(ingest);

        IO.println("3. Clientes suspeitos:");
        fraudAnalyzer.suspiciousCustomers(ingest);

        IO.println("4. Prejuízo Total:" + fraudAnalyzer.sumAmountTotalFrauds(ingest));

        IO.println("5. Fraudes por tipo:");
        fraudAnalyzer.quantityFraudByType(ingest);

        IO.println("\nProcessamento concluído em: " + duration + "ms");
        IO.println("Total de registros processados com sucesso: " + ingest.size());
    }
}
