package repository.config;

import java.sql.Connection;

public class DatabaseTest {

    public static void main(String[] args) {
        DatabaseConnection dbConnection = new DatabaseConnection();

        try (Connection connection = dbConnection.connect()) {

        } catch (Exception e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
    }
}
