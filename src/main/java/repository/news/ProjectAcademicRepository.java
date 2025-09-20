package repository.news;

import model.domain.news.ProjectAcademic;

import java.util.List;

public interface ProjectAcademicRepository {
    void create(ProjectAcademic project);
    List<ProjectAcademic> readAll();
    ProjectAcademic readById(int id);
    void update(ProjectAcademic project);
    void delete(int id);
}
