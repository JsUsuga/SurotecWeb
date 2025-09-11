package service.user;

import model.domain.user.Administrator;
import model.domain.user.Student;
import model.domain.user.User;
import model.domain.classification.StudentStatus;
import repository.user.UserRepositoryImpl;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepositoryImpl userRepository;

    public UserServiceImpl() {
        this.userRepository = new UserRepositoryImpl();
    }

    @Override
    public void createUser(User user) {
        if (user == null) {
            System.out.println("Error: El usuario no puede ser nulo.");
            return;
        }
        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty() ||
                user.getLastName() == null || user.getLastName().trim().isEmpty() ||
                user.getUsername() == null || user.getUsername().trim().isEmpty() ||
                user.getPassword() == null || user.getPassword().trim().isEmpty() ||
                user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            System.out.println("Error: Todos los campos obligatorios deben estar llenos.");
            return;
        }
        if (user instanceof Student && ((Student) user).getStatus() == null) {
            System.out.println("Error: El estado del estudiante es obligatorio.");
            return;
        }
        User existingUser = getUserByEmail(user.getEmail());
        if (existingUser != null) {
            System.out.println("Error: El email " + user.getEmail() + " ya está registrado.");
            return;
        }
        userRepository.create(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.readAll();
    }

    @Override
    public User getUserById(int id) {
        User user = userRepository.readById(id);
        if (user == null) {
            System.out.println("Usuario con ID " + id + " no encontrado.");
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            System.out.println("Error: El email no puede estar vacío.");
            return null;
        }
        User user = userRepository.findByEmail(email);
        if (user == null) {
            System.out.println("Usuario con email " + email + " no encontrado.");
        }
        return user;
    }

    @Override
    public void assignRoleToUser(int userId, String roleName) {
        if (roleName == null || roleName.trim().isEmpty()) {
            System.out.println("Error: El nombre del rol no puede estar vacío.");
            return;
        }
        User user = getUserById(userId);
        if (user == null) {
            System.out.println("Error: No se puede asignar un rol a un usuario inexistente.");
            return;
        }
        userRepository.assignRole(userId, roleName);
    }

    @Override
    public void updateUser(User user, String field, String value) {
        if (user == null || field == null || field.trim().isEmpty() || value == null || value.trim().isEmpty()) {
            System.out.println("Error: El usuario, campo o valor no pueden estar vacíos.");
            return;
        }
        if (!field.equals("first_name") && !field.equals("last_name") && !field.equals("username") &&
                !field.equals("password") && !field.equals("email") && !field.equals("status")) {
            System.out.println("Error: Campo no válido para actualización.");
            return;
        }
        userRepository.updateUser(user, field, value);
    }

    @Override
    public void deleteUser(int id) {
        User user = getUserById(id);
        if (user == null) {
            System.out.println("Error: No se puede eliminar un usuario inexistente.");
            return;
        }
        userRepository.deleteUser(id);
    }
}