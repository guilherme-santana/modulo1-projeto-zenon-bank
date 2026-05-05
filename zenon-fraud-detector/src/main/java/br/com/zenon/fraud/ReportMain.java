package br.com.zenon.fraud;

import java.io.IOException;

public class ReportMain {

    static void main() throws IOException {
        TransactionReport transactionReport = new TransactionReport();

        long start = System.currentTimeMillis();
        transactionReport.processFileEfficiently();
        long end = System.currentTimeMillis();
        long duration = end - start;
        IO.println("Tempo de processamento: " + duration + " ms");
    }
}
