package view.dashboard;

import model.domain.classification.StudentStatus;
import model.domain.user.Administrator;
import model.domain.user.Student;
import model.domain.user.User;
import service.user.UserServiceImpl;

import java.util.List;
import java.util.Scanner;

public class DashboardAdminView {
    private final UserServiceImpl userService;
    private final User currentUser;
    private final Scanner scanner;

    public DashboardAdminView(UserServiceImpl userService, User currentUser) {
        this.userService = userService;
        this.currentUser = currentUser;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        if (!(currentUser instanceof Administrator)) {
            System.out.println("Error: Solo administradores pueden acceder a este dashboard.");
            scanner.close();
            return;
        }
        System.out.println("\n¡Bienvenido al Dashboard de Administración, " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (choice) {
                case 1:
                    createUser();
                    break;
                case 2:
                    getAllUsers();
                    break;
                case 3:
                    getUserById();
                    break;
                case 4:
                    getUserByEmail();
                    break;
                case 5:
                    assignRole();
                    break;
                case 6:
                    updateUser();
                    break;
                case 7:
                    deleteUser();
                    break;
                case 8:
                    System.out.println("Saliendo del Dashboard...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n=== Menú de Gestión de Usuarios ===");
        System.out.println("1. Crear usuario");
        System.out.println("2. Ver todos los usuarios");
        System.out.println("3. Buscar usuario por ID");
        System.out.println("4. Buscar usuario por email");
        System.out.println("5. Asignar rol a usuario");
        System.out.println("6. Actualizar usuario");
        System.out.println("7. Eliminar usuario");
        System.out.println("8. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private void createUser() {
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
        } else {
            Administrator admin = new Administrator(0, firstName, lastName, username, password, email, "manage");
            userService.createUser(admin);
        }
    }

    private void getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            users.forEach(System.out::println);
        }
    }

    private void getUserById() {
        System.out.print("Ingrese el ID del usuario: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        User user = userService.getUserById(id);
        System.out.println(user != null ? user : "Usuario no encontrado.");
    }

    private void getUserByEmail() {
        System.out.print("Ingrese el email del usuario: ");
        String email = scanner.nextLine();
        User user = userService.getUserByEmail(email);
        System.out.println(user != null ? user : "Usuario no encontrado.");
    }

    private void assignRole() {
        System.out.print("Ingrese el ID del usuario: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        System.out.print("Ingrese el nombre del rol: ");
        String roleName = scanner.nextLine();
        userService.assignRoleToUser(userId, roleName);
    }

    private void updateUser() {
        System.out.print("Ingrese el ID del usuario: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        User user = userService.getUserById(id);
        if (user == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }
        System.out.print("Ingrese el campo a actualizar (first_name, last_name, username, password, email, status): ");
        String field = scanner.nextLine();
        System.out.print("Ingrese el nuevo valor: ");
        String value = scanner.nextLine();
        userService.updateUser(user, field, value);
    }

    private void deleteUser() {
        System.out.print("Ingrese el ID del usuario a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        userService.deleteUser(id);
    }
}