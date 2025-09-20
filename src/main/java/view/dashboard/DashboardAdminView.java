package view.dashboard;

import model.domain.classification.Role;
import model.domain.user.Administrator;
import service.user.AdminServiceImpl;
import view.LoginView;

import java.util.List;
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
            System.out.println("\n=== Dashboard de Administrador ===");
            System.out.println("Bienvenido, " + loginView.getLoggedInAdmin().getFirstName() + "!");
            System.out.println("1. Ver todos los administradores (Consulta con JOIN)");
            System.out.println("2. Crear un nuevo administrador");
            System.out.println("3. Actualizar un administrador");
            System.out.println("4. Eliminar un administrador");
            System.out.println("5. Asignar rol a un administrador (Inserción con JOIN implícito)");
            System.out.println("6. Cerrar sesión");
            System.out.print("Elige una opción: ");

            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    viewAllAdmins();
                    break;
                case 2:
                    createAdmin();
                    break;
                case 3:
                    updateAdmin();
                    break;
                case 4:
                    deleteAdmin();
                    break;
                case 5:
                    assignRole();
                    break;
                case 6:
                    logout();
                    return;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }
    }

    private int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Valor inválido
        }
    }

    private void viewAllAdmins() {
        List<Administrator> admins = adminService.getAllAdmins();
        if (admins.isEmpty()) {
            System.out.println("No hay administradores registrados.");
        } else {
            for (Administrator admin : admins) {
                System.out.println("ID: " + admin.getId() + ", Nombre: " + admin.getFirstName() + " " + admin.getLastName() + ", Rol: " + admin.getPermissions());
            }
        }
    }

    private void createAdmin() {
        System.out.print("ID (documento único): ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea
        System.out.print("Nombre: ");
        String firstName = scanner.nextLine();
        System.out.print("Apellido: ");
        String lastName = scanner.nextLine();
        System.out.print("Usuario: ");
        String username = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        Administrator admin = new Administrator(id, firstName, lastName, username, password, email, Role.PUBLISHER_OF_ACADEMIC_OFFERS);
        try {
            adminService.createAdmin(admin);
            System.out.println("Administrador creado con ID: " + id);
        } catch (Exception e) {
            System.out.println("Error al crear administrador: " + e.getMessage());
        }
    }

    private void updateAdmin() {
        System.out.print("ID del administrador a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea

        Administrator admin = adminService.getAdminById(id);
        if (admin == null) {
            System.out.println("Administrador no encontrado.");
            return;
        }

        System.out.print("Campo a actualizar (first_name, last_name, username, password, email, permissions): ");
        String field = scanner.nextLine();
        System.out.print("Nuevo valor: ");
        String value = scanner.nextLine();

        adminService.updateAdmin(admin, field, value);
        System.out.println("Administrador actualizado.");
    }

    private void deleteAdmin() {
        System.out.print("ID del administrador a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea

        adminService.deleteAdmin(id);
        System.out.println("Administrador eliminado.");
    }

    private void assignRole() {
        System.out.print("ID del administrador: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea

        System.out.println("Roles disponibles: ");
        for (Role role : Role.values()) {
            System.out.println(role);
        }
        System.out.print("Role a asignar: ");
        String roleInput = scanner.nextLine().toUpperCase();
        Role role = Role.valueOf(roleInput);

        adminService.assignRoleToAdmin(userId, role);
        System.out.println("Rol asignado.");
    }

    private void logout() {
        System.out.println("Cerrando sesión...");
        loginView.logout();
        System.out.println("Sesión cerrada. ¡Hasta pronto!");
    }
}