package view;

import model.domain.user.Administrator;
import model.domain.user.Student;
import model.domain.classification.StudentStatus;
import model.domain.user.User;
import service.user.UserServiceImpl;
import view.dashboard.DashboardAdminView;

import java.util.Scanner;

public class LoginView {
    private final UserServiceImpl userService;
    private final Scanner scanner;
    private User currentUser;

    public LoginView() {
        this.userService = new UserServiceImpl();
        this.scanner = new Scanner(System.in);
        this.currentUser = null;
        // Crear usuario administrador predeterminado si no existe
        User defaultAdmin = userService.getUserByEmail("admin@surotec.com");
        if (defaultAdmin == null) {
            // Crear sin validaciones estrictas para el predeterminado
            Administrator admin = new Administrator(0, "Admin", "Admin", "admin", "admin123", "admin@surotec.com", "manage");
            userService.createUser(admin); // Asumimos que las validaciones permiten esto inicialmente
            System.out.println("Usuario administrador predeterminado creado: admin/admin123 con email admin@surotec.com");
        }
    }

    public void start() {
        System.out.println("\n=== Bienvenido al Sistema Surotec ===");
        while (currentUser == null) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }

        // Redirigir al dashboard según el tipo de usuario
        if (currentUser instanceof Administrator) {
            DashboardAdminView dashboard = new DashboardAdminView(userService, currentUser);
            dashboard.start();
        }
        /* else if (currentUser instanceof Student) {
            DashboardStudentView studentDashboard = new DashboardStudentView(userService, currentUser);
            studentDashboard.start();
        } else if (currentUser instanceof Foundation) {
            DashboardFoundationView foundationDashboard = new DashboardFoundationView(userService, currentUser);
            foundationDashboard.start();
        } */
        else {
            System.out.println("Tipo de usuario no soportado para dashboard. Solo administradores pueden acceder.");
            currentUser = null; // Reiniciar para nueva autenticación
        }
    }

    private void displayMenu() {
        System.out.println("\n=== Menú Inicial ===");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Registrarse");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private void login() {
        System.out.print("Ingrese su email o usuario: ");
        String identifier = scanner.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String password = scanner.nextLine();

        // Intentar buscar por email
        User user = userService.getUserByEmail(identifier);
        if (user == null) {
            // Si no se encuentra por email, buscar por username (simplificado)
            user = findUserByUsername(identifier); // Método auxiliar
        }
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            System.out.println("Inicio de sesión exitoso. Bienvenido, " + user.getFirstName() + " " + user.getLastName() + "!");
        } else {
            System.out.println("Error: Identificador o contraseña incorrectos.");
        }
    }

    private void register() {
        System.out.print("Tipo de usuario (1 para Administrator, 2 para Student): ");
        int type = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

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

        if (type == 2) {
            System.out.print("Estado (ACTIVO/INACTIVO): ");
            String statusStr = scanner.nextLine().toUpperCase();
            StudentStatus status = StudentStatus.valueOf(statusStr);
            Student student = new Student(0, firstName, lastName, username, password, email, status);
            userService.createUser(student);
            currentUser = student;
        } else {
            Administrator admin = new Administrator(0, firstName, lastName, username, password, email, "manage");
            userService.createUser(admin);
            currentUser = admin;
        }
        System.out.println("Registro exitoso. Bienvenido, " + firstName + " " + lastName + "!");
    }

    // Método auxiliar para buscar por username (simplificado, requiere ajuste en UserService)
    private User findUserByUsername(String username) {
        // Esto es un placeholder; necesitarías implementar una búsqueda por username en UserService
        // Por ahora, iteramos sobre todos los usuarios (no óptimo, solo para demo)
        for (User user : userService.getAllUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        LoginView loginView = new LoginView();
        loginView.start();
    }
}