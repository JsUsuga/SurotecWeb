package service.user;

import model.domain.classification.StudentStatus;
import model.domain.user.Student;

import java.util.List;

public interface StudentService {
    void createStudent(Student student);
    void updateStudent(Student student);
    void deleteStudent(int id);
    Student findStudentById(int id);
    List<Student> findStudentsByStatus(StudentStatus status);
    List<Student> listAllStudents();
}
