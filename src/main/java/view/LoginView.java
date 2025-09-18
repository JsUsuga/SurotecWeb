package view;

import model.domain.user.Administrator;
import service.user.AdminServiceImpl;
import view.dashboard.DashboardAdminView;

import java.util.Scanner;

public class LoginView {
    private final AdminServiceImpl adminService;
    private Administrator loggedInAdmin;

    public LoginView() {
        this.adminService = new AdminServiceImpl();
        this.loggedInAdmin = null;
    }

    public static void main(String[] args) {
        LoginView loginView = new LoginView();
        loginView.showLogin();
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
                Administrator admin = adminService.findAdminByUsername(username);
                if (admin != null && admin.getPassword().equals(password)) { // Nota: En producción, usa hashing
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

    public void showDashboard() {
        DashboardAdminView dashboard = new DashboardAdminView(this, adminService);
        dashboard.show();
    }

    public void logout() {
        loggedInAdmin = null;
    }

    public Administrator getLoggedInAdmin() {
        return loggedInAdmin;
    }
}