package model.domain.user;

import model.domain.classification.StudentStatus;

public class Student extends User{
    //Declaración de atributos
    private String fullName; //Revisar, pues admin y usuario regular también tienen nombre completo, podría pasar este atributo para la clase abstracta
    private StudentStatus status;

    //Constructores de clase
    public Student() {
    }

    public Student(int id, String username, String password, String email, String fullName, StudentStatus status) {
        super(id, username, password, email);
        this.fullName = fullName;
        this.status = status;
    }

    //Getters y Setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
    }

    //Método toString
    @Override
    public String toString() {
        return "Student{" +
                "fullName='" + fullName + '\'' +
                ", status=" + status +
                '}';
    }
}
