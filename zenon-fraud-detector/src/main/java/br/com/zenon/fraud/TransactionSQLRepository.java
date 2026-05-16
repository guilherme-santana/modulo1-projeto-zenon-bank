package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TransactionSQLRepository {

    public static final int JDBC_BATCH_SIZE = 1_000;

    public void save(Transaction transaction) {
        String sql = """
                INSERT INTO transactions
                (step, `type`, amount, name_origin, old_balance_orgin, new_balance_origin, name_recipient, old_balance_recipient, new_balance_recipient, is_fraud, is_flagged_fraud)
                VALUES(?,?,?,?,?,?,?,?,?,?,?);
                """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, transaction.step());
            ps.setString(2, transaction.type().name());
            ps.setBigDecimal(3, transaction.amount());
            ps.setString(4, transaction.origin().getName());
            ps.setBigDecimal(5, transaction.origin().getOldBalance());
            ps.setBigDecimal(6, transaction.origin().getNewBalance());
            ps.setString(7, transaction.destination().getName());
            ps.setBigDecimal(8, transaction.destination().getOldBalance());
            ps.setBigDecimal(9, transaction.destination().getNewBalance());
            ps.setInt(10, transaction.isFraud());
            ps.setInt(11, transaction.isFlaggedFraud());
            ps.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar transação ", e);
        }
    }

    public Optional<Transaction> findByOriginName(String originName) {

        String sql = """
                SELECT id, step, `type`, amount, name_origin, old_balance_orgin, new_balance_origin, name_recipient, old_balance_recipient, new_balance_recipient, is_fraud, is_flagged_fraud
                FROM zenon_frauds.transactions
                WHERE name_origin = ?
                LIMIT 1
                """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, originName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Transaction transaction = mapResultSetToTransaction(resultSet);
                    return Optional.of(transaction);
                } else {
                    IO.println("Transação não encontrada para " + originName);
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Transaction mapResultSetToTransaction(ResultSet rs) {
        try {
            long step = rs.getInt("step");
            TransactioType type = TransactioType.fromString(rs.getString("type"));
            BigDecimal amount = rs.getBigDecimal("amount");
            String nameOrigin = rs.getString("name_origin");
            BigDecimal oldBalanceOrgin = rs.getBigDecimal("old_balance_orgin");
            BigDecimal newBalanceOrigin = rs.getBigDecimal("new_balance_origin");
            String nameRecipient = rs.getString("name_recipient");
            BigDecimal oldBalanceRecipient = rs.getBigDecimal("old_balance_recipient");
            BigDecimal newBalanceRecipient = rs.getBigDecimal("new_balance_recipient");
            int isFraud = rs.getInt("is_fraud");
            int isFlaggedFraud = rs.getInt("is_flagged_fraud");

            return new Transaction(
                    (int) step,
                    type,
                    amount,
                    new OriginCustomer(nameOrigin, oldBalanceOrgin, newBalanceOrigin),
                    new DestCustomer(nameRecipient, oldBalanceRecipient, newBalanceRecipient),
                    isFraud,
                    isFlaggedFraud
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveAll(List<Transaction> transactions) {
        String sql = """
                INSERT INTO transactions
                (step, `type`, amount, name_origin, old_balance_orgin, new_balance_origin, name_recipient, old_balance_recipient, new_balance_recipient, is_fraud, is_flagged_fraud)
                VALUES(?,?,?,?,?,?,?,?,?,?,?);
                """;

        try (Connection connection = ConnectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            int count = 0;

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                for (Transaction transaction : transactions) {
                    ps.setInt(1, transaction.step());
                    ps.setString(2, transaction.type().name());
                    ps.setBigDecimal(3, transaction.amount());
                    ps.setString(4, transaction.origin().getName());
                    ps.setBigDecimal(5, transaction.origin().getOldBalance());
                    ps.setBigDecimal(6, transaction.origin().getNewBalance());
                    ps.setString(7, transaction.destination().getName());
                    ps.setBigDecimal(8, transaction.destination().getOldBalance());
                    ps.setBigDecimal(9, transaction.destination().getNewBalance());
                    ps.setInt(10, transaction.isFraud());
                    ps.setInt(11, transaction.isFlaggedFraud());
                    IO.println("Adicionando nova transação no batch...");
                    ps.addBatch();
                    count++;

                    if (count % JDBC_BATCH_SIZE == 0) {
                        IO.println("Executando batch...");
                        ps.executeBatch();
                        connection.commit();
                    }
                }
                IO.println("Executando batch final...");
                ps.executeBatch();
                connection.commit();
                connection.setAutoCommit(true);

            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException("Erro ao executar rollback ", e);
                }
                throw new RuntimeException("Erro ao salvar transação ", e);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com DB ", e);
        }

    }
}
