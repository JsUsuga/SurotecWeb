package service.application;

import model.domain.application.Inscription;
import model.domain.news.ProjectAcademic;
import repository.application.InscriptionRepository;
import repository.application.InscriptionRepositoryImpl;
import repository.news.ProjectAcademicRepository;
import repository.news.ProjectAcademicRepositoryImpl;
import service.news.ProjectAcademicService;
import service.news.ProjectAcademicServiceImpl;

import java.util.List;
import java.util.Scanner;

public class InscriptionServiceImpl implements InscriptionService {

    private final InscriptionRepository inscriptionRepository = new InscriptionRepositoryImpl();
    private final ProjectAcademicService projectService = new ProjectAcademicServiceImpl();
    private final Scanner sc = new Scanner(System.in);

    @Override
    public void enrollStudentToProject(Inscription inscription) {
        System.out.println("\nINSCRIPCIÓN DE ESTUDIANTE A PROYECTO");
        System.out.print("ID del estudiante: ");
        inscription.setStudentId(sc.nextInt());
        sc.nextLine();

        System.out.print("ID del proyecto: ");
        inscription.setProjectId(sc.nextInt());
        sc.nextLine();

        inscriptionRepository.create(inscription);
        System.out.println("Estudiante inscrito con éxito en el proyecto.");
    }

    @Override
    public List<Inscription> listAllInscriptions() {
        System.out.println("\nLISTADO DE INSCRIPCIONES");
        List<Inscription> inscriptions = inscriptionRepository.readAll();

        if (inscriptions.isEmpty()) {
            System.out.println("No existen inscripciones registradas.");
        } else {
            int count = 1;
            for (Inscription ins : inscriptions) {
                System.out.println("🔹 Inscripción #" + count++);
                System.out.println("   ID: " + ins.getInscriptionId());
                System.out.println("   Estudiante: " + ins.getStudentId());
                System.out.println("   Proyecto: " + ins.getProjectId());
                System.out.println("-----------------------------------");
            }
            System.out.println("Total de inscripciones: " + inscriptions.size());
        }

        return inscriptions;
    }

    @Override
    public List<Inscription> findInscriptionsByStudent(int studentId) {
        System.out.println("Buscando inscripciones del estudiante con ID: " + studentId);

        List<Inscription> inscriptions = inscriptionRepository.readByStudentId(studentId);

        if (inscriptions.isEmpty()) {
            System.out.println("El estudiante con ID " + studentId + " no tiene inscripciones registradas.");
        } else {
            System.out.println("Se encontraron " + inscriptions.size() + " inscripciones para el estudiante.");
            for (Inscription ins : inscriptions) {
                ProjectAcademic project = projectService.getProjectById((int) ins.getProjectId());
                if (project != null) {
                    System.out.println("🔹 Inscripción ID: " + ins.getInscriptionId());
                    System.out.println("   Proyecto: " + project.getProjectTitle());
                    System.out.println("   Descripción: " + project.getProjectDescription());
                    System.out.println("   Estado: " + project.getProjectStatus());
                } else {
                    System.out.println("Proyecto no encontrado para esta inscripción");
                }
                System.out.println("-----------------------------------");
            }
        }

        return inscriptions;
    }

    @Override
    public List<Inscription> findInscriptionsByProject(int projectId) {
        System.out.println("🔍 Buscando inscripciones para el proyecto con ID: " + projectId);

        List<Inscription> inscriptions = inscriptionRepository.readByProjectId(projectId);

        if (inscriptions.isEmpty()) {
            System.out.println("El proyecto con ID " + projectId + " no tiene inscripciones registradas.");
        } else {
            System.out.println("Se encontraron " + inscriptions.size() + " inscripciones en este proyecto.");
        }

        return inscriptions;
    }
}
