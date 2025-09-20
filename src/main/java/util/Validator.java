package util;

public class Validator {
    // Validar email
    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        String emailRegex = "^[^@]+@[^@]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    // Validar contraseña (mínimo 8 caracteres, letra y número)
    public static boolean isValidPassword(String password) {
        if (password == null) return false;
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        return password.matches(passwordRegex);
    }

    // Validar nombre/apellido (solo letras y espacios)
    public static boolean isValidName(String name) {
        if (name == null) return false;
        String nameRegex = "^[a-zA-Z\\s]+$";
        return name.matches(nameRegex) && !name.trim().isEmpty();
    }

    // Validar username (alfanumérico y guiones bajos)
    public static boolean isValidUsername(String username) {
        if (username == null) return false;
        String usernameRegex = "^[a-zA-Z0-9_]+$";
        return username.matches(usernameRegex) && !username.trim().isEmpty();
    }
}