// view/LoginView.java
package view;

import model.domain.user.Administrator;
import service.admin.AdminServiceImpl;

import java.util.Scanner;

public class LoginView {
    private final AdminServiceImpl adminService;
    private Administrator loggedInAdmin;

    public LoginView() {
        this.adminService = new AdminServiceImpl();
        this.loggedInAdmin = null;
    }

    public void showLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Login de Administrador ===");

        while (loggedInAdmin == null) {
            System.out.print("Usuario: ");
            String username = scanner.nextLine();
            System.out.print("Contraseña: ");
            String password = scanner.nextLine();

            try {
                // Simulamos búsqueda por username y validación de password
                Administrator admin = findAdminByUsername(username);
                if (admin != null && admin.getPassword().equals(password)) {
                    loggedInAdmin = admin;
                    System.out.println("¡Login exitoso! Bienvenido, " + loggedInAdmin.getFirstName() + "!");
                } else {
                    System.out.println("Usuario o contraseña incorrectos. Intenta de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("Error al intentar logear: " + e.getMessage());
            }
        }
        showDashboard();
    }

    private Administrator findAdminByUsername(String username) {
        // Simulación: Busca en la lista de todos los admins
        for (Administrator admin : adminService.getAllAdmins()) {
            if (admin.getUsername().equals(username)) {
                return admin;
            }
        }
        return null;
    }

    public void showDashboard() {
        // Llamada al dashboard renombrado
        DashboardAdminView dashboard = new DashboardAdminView(this);
        dashboard.show();
    }

    public Administrator getLoggedInAdmin() {
        return loggedInAdmin;
    }
}