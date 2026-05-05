package br.com.zenon.fraud;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TransactionListRepository {

    public Optional<Transaction> findTransactionByCustomerOrigen(List<Transaction> transaction, String customer) {
        return Optional.of(transaction.stream()
                .filter(t -> t.origin().getName().equals(customer))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Transação não encontrada para o cliente" + customer)));
    }
}
