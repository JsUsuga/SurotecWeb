package repository.user;

import model.domain.classification.StudentStatus;
import model.domain.user.Student;

import java.util.List;

public interface StudentRepository {
    //Create
    void create(Student student);

    //Read
    List<Student> readAll();
    Student readById(int id);
    List<Student> readByStatus(StudentStatus status);

    //Update
    void update(Student student);

    //Delete
    void delete(int id);
}
