package service.application;

import model.domain.application.Inscription;
import model.domain.news.ProjectAcademic;

import java.util.List;

public interface InscriptionService {
    void enrollStudentToProject(Inscription inscription);
    List<Inscription> listAllInscriptions();
    List<Inscription> findInscriptionsByStudent(int studentId);
    List<Inscription> findInscriptionsByProject(int projectId);
}
