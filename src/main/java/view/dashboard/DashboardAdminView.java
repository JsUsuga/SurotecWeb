package view.dashboard;

import service.user.AdminServiceImpl;
import model.domain.user.Administrator;
import model.domain.classification.Role;
import view.LoginView;

import java.util.Scanner;

public class DashboardAdminView {
    private final LoginView loginView;
    private final AdminServiceImpl adminService;
    private final Scanner scanner;

    public DashboardAdminView(LoginView loginView, AdminServiceImpl adminService) {
        this.loginView = loginView;
        this.adminService = adminService;
        this.scanner = new Scanner(System.in);
    }

    public void show() {
        while (true) {
            Administrator loggedInAdmin = loginView.getLoggedInAdmin();
            if (loggedInAdmin == null) {
                System.out.println("Sesión cerrada. Regresando al login...");
                break;
            }

            System.out.println("\n=== Panel de Administración ===");
            System.out.println("Bienvenido, " + loggedInAdmin.getFirstName() + " " + loggedInAdmin.getLastName());
            System.out.println("1. Listar todos los administradores");
            System.out.println("2. Asignar rol a un administrador");
            System.out.println("3. Actualizar datos de un administrador");
            System.out.println("4. Cerrar sesión");
            System.out.print("Elige una opción: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea

            switch (option) {
                case 1:
                    listAllAdmins();
                    break;
                case 2:
                    assignRole();
                    break;
                case 3:
                    updateAdmin();
                    break;
                case 4:
                    loginView.logout();
                    break;
                default:
                    System.out.println("Opción inválida. Intenta de nuevo.");
            }
        }
    }

    private void listAllAdmins() {
        System.out.println("\n=== Lista de Administradores ===");
        for (Administrator admin : adminService.getAllAdmins()) {
            System.out.println(admin);
        }
    }

    private void assignRole() {
        System.out.println("\n=== Asignar Rol ===");
        System.out.print("Ingresa el ID del usuario al que deseas asignar un rol: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea

        System.out.println("Roles disponibles:");
        for (Role role : Role.values()) {
            System.out.println(role);
        }
        System.out.print("Ingresa el rol: ");
        String roleInput = scanner.nextLine().toUpperCase();
        Role role = null;
        try {
            role = Role.valueOf(roleInput);
            adminService.assignRoleToAdmin(userId, role);
            System.out.println("Rol asignado exitosamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Rol inválido. Por favor, usa uno de los roles listados.");
        } catch (Exception e) {
            System.out.println("Error al asignar rol: " + e.getMessage());
        }
    }

    private void updateAdmin() {
        System.out.println("\n=== Actualizar Administrador ===");
        System.out.print("Ingresa el ID del administrador a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea

        Administrator admin = adminService.getAdminById(id);
        if (admin == null) {
            System.out.println("Administrador no encontrado.");
            return;
        }

        System.out.println("Selecciona el campo a actualizar: 1. Nombre, 2. Apellido, 3. Usuario, 4. Contraseña, 5. Email");
        int fieldOption = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea
        System.out.print("Ingresa el nuevo valor: ");
        String value = scanner.nextLine();

        String field = "";
        switch (fieldOption) {
            case 1: field = "firstname"; break;
            case 2: field = "lastname"; break;
            case 3: field = "username"; break;
            case 4: field = "password"; break;
            case 5: field = "email"; break;
            default:
                System.out.println("Opción de campo inválida.");
                return;
        }

        try {
            adminService.updateAdmin(admin, field, value);
            System.out.println("Administrador actualizado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al actualizar administrador: " + e.getMessage());
        }
    }
}