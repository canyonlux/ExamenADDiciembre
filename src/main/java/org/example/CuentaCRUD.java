package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CuentaCRUD {

    private final PostgresSQLConnection postgresSQLConnection = new PostgresSQLConnection();

    public void createAccount(Cliente client, Cuenta account) {
        String sql = "INSERT INTO accounts (iban, balance, clientid) VALUES (?, ?, ?)";
        executeUpdate(sql, statement -> {
            statement.setString(1, account.getIban());
            statement.setDouble(2, account.getBalance());
            statement.setInt(3, client.getClientid());
        });
    }

    public void deleteAccount(Cliente client) {
        String delete = "DELETE FROM accounts WHERE clientid = ?";
        executeUpdate(delete, statement -> statement.setInt(1, client.getClientid()));
    }

    public void updateAccount(Cuenta account) {
        String update = "UPDATE accounts SET balance = ? WHERE iban = ?";
        executeUpdate(update, statement -> {
            statement.setDouble(1, account.getBalance());
            statement.setString(2, account.getIban());
        });
    }

    public void mostrarAcconts() {
        String select = "SELECT * FROM accounts";
        executeQuery(select, statement -> {}, resultSet -> {
            while (resultSet.next()) {
                System.out.println("Account ID: " + resultSet.getInt("accountid"));
                System.out.println("IBAN: " + resultSet.getString("iban"));
                System.out.println("Balance: " + resultSet.getDouble("balance"));
                System.out.println("Client ID: " + resultSet.getInt("clientid"));
                System.out.println();
            }
        });
    }
    private void executeUpdate(String sql, SQLConsumer<PreparedStatement> consumer) {
        try (Connection connection = postgresSQLConnection.postgresSQLConect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            consumer.accept(statement);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void executeQuery(String sql, SQLConsumer<PreparedStatement> statementConsumer, SQLConsumer<ResultSet> resultSetConsumer) {
        try (Connection connection = postgresSQLConnection.postgresSQLConect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statementConsumer.accept(statement);
            ResultSet resultSet = statement.executeQuery();
            resultSetConsumer.accept(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean findAccount(Cuenta account) {
        String select = "SELECT * FROM accounts WHERE iban = ?";
        try (Connection connection = postgresSQLConnection.postgresSQLConect();
             PreparedStatement statement = connection.prepareStatement(select)) {

            statement.setString(1, account.getIban());
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return false;
    }


    public void transferencia(Cuenta envia, Cuenta recibe, double cantidad) {
        Connection connection = null;
        try {
            connection = postgresSQLConnection.postgresSQLConect();
            connection.setAutoCommit(false);

            // Verificar si hay suficiente dinero en la cuenta de origen
            if (envia.getBalance() - cantidad < 0 || findAccount(recibe)) {
                throw new Exception("No hay suficiente dinero en la cuenta de origen");
            }

            // Actualizar la cuenta de origen
            String updateEnvia = "UPDATE accounts SET balance = ? WHERE iban = ?";
            try (PreparedStatement enviando = connection.prepareStatement(updateEnvia)) {
                enviando.setDouble(1, envia.getBalance() - cantidad);
                enviando.setString(2, envia.getIban());
                enviando.executeUpdate();
            }

            // Actualizar la cuenta de destino
            String updateRecibe = "UPDATE accounts SET balance = ? WHERE iban = ?";
            try (PreparedStatement recibiendo = connection.prepareStatement(updateRecibe)) {
                recibiendo.setDouble(1, recibe.getBalance() + cantidad);
                recibiendo.setString(2, recibe.getIban());
                recibiendo.executeUpdate();
            }

            connection.commit();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            try {
                if (connection != null) {
                    connection.rollback();
                    System.err.println("ROLLBACK ejecutado");
                }
            } catch (SQLException e3) {
                System.err.println("Error en rollback: " + e3.getMessage());
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @FunctionalInterface
    private interface SQLConsumer<T> {
        void accept(T t) throws SQLException;
    }
}
