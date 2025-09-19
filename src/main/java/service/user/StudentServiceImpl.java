package service.user;

import model.domain.classification.StudentStatus;
import model.domain.user.Student;
import repository.user.StudentRepository;
import repository.user.StudentRepositoryImpl;

import java.util.List;
import java.util.Scanner;

public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository = new StudentRepositoryImpl();
    private final Scanner sc = new Scanner(System.in);

    @Override
    public void createStudent(Student student) {
        System.out.println("\nRegistrar un nuevo estudiante");

        System.out.print("Ingres el ID: ");
        student.setId(sc.nextInt()); sc.nextLine();

        System.out.print("Ingrese el Nombre: ");
        student.setFirstName(sc.nextLine());

        System.out.print("Ingrese el Apellido: ");
        student.setLastName(sc.nextLine());

        System.out.print("Ingrese el Username: ");
        student.setUsername(sc.nextLine());

        System.out.print("Ingrese la Contraseña: ");
        student.setPassword(sc.nextLine());

        System.out.print("Ingrese el Email: ");
        student.setEmail(sc.nextLine());

        System.out.print("Seleccione el Estado (ACTIVE / INACTIVE / GRADUATED): ");
        String statusInput = sc.nextLine().toUpperCase();
        student.setStatus(StudentStatus.valueOf(statusInput));

        studentRepository.create(student);
        System.out.println("Estudiante creado con éxito: " + student);
    }

    @Override
    public void updateStudent(Student student) {
        System.out.println("\nActualizar un estudiante");
        System.out.print("Ingrese el ID del estudiante a actualizar: ");
        int idIngresado = sc.nextInt(); sc.nextLine();

        Student existing = studentRepository.readById(idIngresado);
        if (existing == null) {
            System.out.println("Estudiante no encontrado.");
            return;
        }

        System.out.println("Datos actuales: " + existing);

        System.out.print("Nuevo nombre (enter para mantener): ");
        String firstName = sc.nextLine();
        if (!firstName.isEmpty()) existing.setFirstName(firstName);

        System.out.print("Nuevo apellido (enter para mantener): ");
        String lastName = sc.nextLine();
        if (!lastName.isEmpty()) existing.setLastName(lastName);

        System.out.print("Nuevo username (enter para mantener): ");
        String username = sc.nextLine();
        if (!username.isEmpty()) existing.setUsername(username);

        System.out.print("Nueva password (enter para mantener): ");
        String password = sc.nextLine();
        if (!password.isEmpty()) existing.setPassword(password);

        System.out.print("Nuevo email (enter para mantener): ");
        String email = sc.nextLine();
        if (!email.isEmpty()) existing.setEmail(email);

        System.out.print("Nuevo estado (ACTIVE / INACTIVE / GRADUATED, enter para mantener): ");
        String statusInput = sc.nextLine();
        if (!statusInput.isEmpty()) {
            existing.setStatus(StudentStatus.valueOf(statusInput.toUpperCase()));
        }

        studentRepository.update(existing);
        System.out.println("Estudiante actualizado: " + existing);
    }

    @Override
    public void deleteStudent(int id) {
        System.out.println("\nEliminar estudiante");
        System.out.print("Ingrese el ID del estudiante a eliminar: ");
        id = sc.nextInt(); sc.nextLine();

        Student student = studentRepository.readById(id);
        if (student == null) {
            System.out.println("No se encontró un estudiante con ID " + id);
        } else {
            studentRepository.delete(id);
            System.out.println("Estudiante eliminado: " + student);
        }
    }

    @Override
    public Student findStudentById(int id) {
        System.out.println("\nBuscar estudiante por ID");
        System.out.print("Ingrese el ID a buscar: ");
        id = sc.nextInt(); sc.nextLine();

        Student student = studentRepository.readById(id);
        if (student != null) {
            System.out.println("Estudiante encontrado: " + student);
        } else {
            System.out.println("Estudiante con ID " + id + " no existe.");
        }
        return student;
    }

    @Override
    public List<Student> findStudentsByStatus(StudentStatus status) {
        System.out.println("\nBuscar estudiantes por estado");
        System.out.print("Ingrese el estado del estudiante que desea encontrar (ACTIVE / INACTIVE / GRADUATED): ");
        String statusInput = sc.nextLine().toUpperCase();

        status = StudentStatus.valueOf(statusInput);
        List<Student> students = studentRepository.readByStatus(status);

        if (students.isEmpty()) {
            System.out.println("No hay estudiantes con estado el " + status);
        } else {
            System.out.println("Estudiantes con el estado " + status + ":");
            students.forEach(System.out::println);
        }
        return students;
    }

    @Override
    public List<Student> listAllStudents() {
        System.out.println("\nListado de todos los estudiantes registrados en el sistema");
        List<Student> students = studentRepository.readAll();

        if (students.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
        } else {
            students.forEach(System.out::println);
            System.out.println("Total de estudiantes: " + students.size());
        }
        return students;
    }
}
