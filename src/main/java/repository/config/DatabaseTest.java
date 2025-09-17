package repository.config;

import java.sql.Connection;



public class DatabaseTest {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                    System.out.println("✅ Conexión verificada correctamente");
            } else {
                    System.out.println("❌ No se pudo establecer la conexión");
            }
        } catch (Exception e) {
                System.out.println("Error al conectar: " + e.getMessage());
        }
    }
}

