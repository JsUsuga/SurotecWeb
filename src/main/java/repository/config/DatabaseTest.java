package repository.config;

import model.domain.classification.StudentStatus;
import model.domain.user.Student;
import repository.user.StudentRepository;
import repository.user.StudentRepositoryImpl;

import java.sql.Connection;

public class DatabaseTest {

    public static void main(String[] args) {


        StudentRepository repositoryStudentTest = new StudentRepositoryImpl();

        Student studentTest = new Student(
                1026746533,
                "Samuel",
                "Alvarez",
                "samuel01",
                "01112100",
                "samuel@gmail.com",
                StudentStatus.ACTIVE
        );

        repositoryStudentTest.create(studentTest);





    }
}
