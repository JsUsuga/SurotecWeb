package model.domain.user;

import model.domain.classification.StudentStatus;

public class Student extends User {
    //Declaración de atributos
    private StudentStatus status;

    //Constructores de clase
    public Student() {
    }

    public Student(int id, String firstName, String lastName, String username, String password, String email, StudentStatus status) {
        super(id, firstName, lastName, username, password, email);
        this.status = status;
    }

    // Getters y setters
    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
    }

    //Método toString
    @Override
    public String toString() {
        return super.toString() + " Student{" +
                "status=" + status +
                '}';
    }
}