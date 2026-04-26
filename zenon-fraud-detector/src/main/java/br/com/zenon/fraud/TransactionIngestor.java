package br.com.zenon.fraud;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class TransactionIngestor {

    public List<Transaction> ingest(String fileName, int limit) {
        try {
            Stream<String> lines = Files.lines(Path.of(fileName));
            return lines.skip(1)
                    .limit(limit)
                    .map(line -> {
                        String[] parts = line.split(",");
                        return new Transaction(
                                Integer.parseInt(parts[0].trim()),
                                TransactioType.valueOf(parts[1].trim()),
                                new BigDecimal(parts[2].trim()),
                                new OriginCustomer(parts[3].trim(), new BigDecimal(parts[4].trim()), new BigDecimal(parts[5].trim())),
                                new DestCustomer(parts[6].trim(), new BigDecimal(parts[7].trim()), new BigDecimal(parts[8].trim())),
                                Integer.parseInt(parts[9].trim()),
                                Integer.parseInt(parts[10].trim())
                        );
                    }).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
