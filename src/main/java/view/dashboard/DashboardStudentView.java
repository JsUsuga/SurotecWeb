package view.dashboard;

import model.domain.application.Inscription;
import model.domain.news.ProjectAcademic;
import service.application.InscriptionService;
import service.application.InscriptionServiceImpl;
import service.news.ProjectAcademicService;
import service.news.ProjectAcademicServiceImpl;

import javax.sound.midi.Soundbank;
import java.util.List;
import java.util.Scanner;

public class DashboardStudentView {
    private final Scanner sc = new Scanner(System.in);
    private final ProjectAcademicService projectService = new ProjectAcademicServiceImpl();
    private final InscriptionService inscriptionService = new InscriptionServiceImpl();

    public void showDashboard() {
        int option = -1;

        while (option != 0) {
            System.out.println("\n¡Bienvenido Estudiante!");
            System.out.println("Ingrese la acción que desea realizar:");
            System.out.println("1. Crear un nuevo proyecto académico");
            System.out.println("2. Listar todos los proyectos");
            System.out.println("3. Inscribirse a un proyecto");
            System.out.println("4. Listar mis inscripciones por estudiante");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1: projectService.createProject(new ProjectAcademic());
                break;
                case 2: projectService.listAllProjects();
                break;
                case 3: inscriptionService.enrollStudentToProject(new Inscription());
                break;
                case 4:  {
                    System.out.print("Ingrese su ID de estudiante: ");
                    int studentId = sc.nextInt(); sc.nextLine();
                    List<Inscription> inscriptions = inscriptionService.findInscriptionsByStudent(studentId);
                    if(inscriptions.isEmpty()){
                        System.out.println("No tienes inscripciones todavía.");
                    } else {
                        System.out.println("Tus inscripciones se mostraron arriba...");
                    }
                }
                break;
                case 0: System.out.println("Saliendo del Dashboard...");
                break;
                default: System.out.println("Opción inválida, intente de nuevo.");
            }
        }
    }

    public static void main(String[] args) {
        DashboardStudentView dashboard = new DashboardStudentView();
        dashboard.showDashboard();
    }
}
