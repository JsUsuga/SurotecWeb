package model.domain.application;

public class Inscription {
    //Declaración de atributos
    private int inscriptionId;
    private int studentId;
    private int projectId;

    //Constructores de clase
    public Inscription(){

    }

    public Inscription(int inscriptionId, int studentId, int projectId) {
        this.inscriptionId = inscriptionId;
        this.studentId = studentId;
        this.projectId = projectId;
    }

    //Getters y Setters
    public long getInscriptionId() {
        return inscriptionId;
    }

    public void setInscriptionId(int inscriptionId) {
        this.inscriptionId = inscriptionId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId( int studentId) {
        this.studentId = studentId;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    //Método toString
    @Override
    public String toString() {
        return "Inscription{" +
                "inscriptionId=" + inscriptionId +
                ", studentId=" + studentId +
                ", projectId=" + projectId +
                '}';
    }
}
