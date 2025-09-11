// model/domain/user/Student.java
package model.domain.user;

import model.domain.classification.StudentStatus;

public class Student extends User {
    private StudentStatus status;

    public Student(int id, String firstName, String lastName, String username, String password, String email, StudentStatus status) {
        super(id, firstName, lastName, username, password, email); // Llama al constructor de User
        this.status = status;
    }

    // Getters y setters
    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Student{id=" + getId() + ", firstName='" + getFirstName() + "', lastName='" + getLastName() +
                "', username='" + getUsername() + "', email='" + getEmail() + "', status=" + status + "}";
    }
}