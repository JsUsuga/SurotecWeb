package repository.application;

import model.domain.application.Inscription;

import java.util.List;

public interface InscriptionRepository {
    void create(Inscription inscription);
    List<Inscription> readAll();
    Inscription readById(int id);
    List<Inscription> readByStudentId(int studentId);
    List<Inscription> readByProjectId(int projectId);
    void delete(int id);
}
