package model.domain.news;

public class ProjectAcademic {
    //Declaración de atributos
    private int projectId;
    private String projectTitle;
    private String projectDescription;
    private String projectStatus; //Revisar, se podría hacer un enum

    //Constructores de clase
    public ProjectAcademic (){

    }

    public ProjectAcademic(int projectId, String projectStatus, String projectDescription, String projectTitle) {
        this.projectId = projectId;
        this.projectStatus = projectStatus;
        this.projectDescription = projectDescription;
        this.projectTitle = projectTitle;
    }

    //Getters y Setters
    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    //Método toSring
    @Override
    public String toString() {
        return "ProjectAcademic{" +
                "projectId=" + projectId +
                ", projectTitle='" + projectTitle + '\'' +
                ", projectDescription='" + projectDescription + '\'' +
                ", projectStatus='" + projectStatus + '\'' +
                '}';
    }
}
