package view;

import model.domain.user.Administrator;
import model.domain.user.Foundation;
import model.domain.user.Student;
import service.user.AdminServiceImpl;
/*import service.user.FoundationServiceImpl;
import service.user.StudentServiceImpl;*/
import view.dashboard.DashboardAdminView;
import view.dashboard.DashboardFoundationView;
import view.dashboard.DashboardStudentView;
import repository.user.AdminRepositoryImpl;
/*import repository.user.FoundationRepositoryImpl;*/
import repository.user.StudentRepositoryImpl;

import java.util.Scanner;

public class LoginView {
    private final AdminServiceImpl adminService;
    /*private final FoundationServiceImpl foundationService;
    private final StudentServiceImpl studentService;*/

    private Administrator loggedInAdmin;
    private Foundation loggedInFoundation;
    private Student loggedInStudent;

    public LoginView() {
        this.adminService = new AdminServiceImpl(new AdminRepositoryImpl());
        // this.foundationService = new FoundationServiceImpl(new FoundationRepositoryImpl());
        // this.studentService = new StudentServiceImpl(new StudentRepositoryImpl());
    }

    public static void main(String[] args) {
        LoginView loginView = new LoginView();
        loginView.showLogin();
    }

    public void showLogin() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("=== General Login ===");
            System.out.println("1. Admin");
            System.out.println("2. Foundation");
            System.out.println("3. Student");
            System.out.println("4. Register (Foundation/Student)");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1:
                    loginAdmin(scanner);
                    break;
                case 2:
                    //loginFoundation(scanner);
                    break;
                case 3:
                    //loginStudent(scanner);
                    break;
                case 4:
                    // registerUser(scanner);
                    break;
                case 5:
                    System.out.println("Cerrando sesión... ¡Hasta pronto!");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void loginAdmin(Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        Administrator admin = adminService.findByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            loggedInAdmin = admin;
            System.out.println("¡Login exitoso! Bienvenido, " + loggedInAdmin.getFirstName() + "!");
            new DashboardAdminView(this, adminService).show();
        } else {
            System.out.println("Usuario o contraseña incorrectos.");
        }
    }

    /*private void loginFoundation(Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        Foundation foundation = foundationService.findByUsername(username);
        if (foundation != null && foundation.getPassword().equals(password)) {
            loggedInFoundation = foundation;
            System.out.println("¡Login exitoso! Bienvenido, " + loggedInFoundation.getFirstName() + "!");
            new DashboardFoundationView(this, foundationService).show();
        } else {
            System.out.println("Usuario o contraseña incorrectos.");
        }
    }*/

    /*private void loginStudent(Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        Student student = studentService.findByUsername(username);
        if (student != null && student.getPassword().equals(password)) {
            loggedInStudent = student;
            System.out.println("¡Login exitoso! Bienvenido, " + loggedInStudent.getFirstName() + "!");
            new DashboardStudentView(this, studentService).show();
        } else {
            System.out.println("Usuario o contraseña incorrectos.");
        }
    }

    private void registerUser(Scanner scanner) {
        System.out.println("Register as:\n1. Foundation\n2. Student");
        int type = Integer.parseInt(scanner.nextLine());
        // Registration logic for Foundation or Student
        // Example: ask for username, password, email, etc. and call the respective service
    }*/

    public void logout() {
        loggedInAdmin = null;
        loggedInFoundation = null;
        loggedInStudent = null;
    }

    public Administrator getLoggedInAdmin() {
        return loggedInAdmin;
    }
    public Foundation getLoggedInFoundation() {
        return loggedInFoundation;
    }
    public Student getLoggedInStudent() {
        return loggedInStudent;
    }
}