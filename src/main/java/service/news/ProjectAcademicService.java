package service.news;

import model.domain.news.ProjectAcademic;

import java.util.List;

public interface ProjectAcademicService {
    void createProject(ProjectAcademic project);
    ProjectAcademic findProjectById(int id);
    List<ProjectAcademic> listAllProjects();
    void updateProject(ProjectAcademic project);
    void deleteProject(int id);
    ProjectAcademic getProjectById(int projectId);
}
