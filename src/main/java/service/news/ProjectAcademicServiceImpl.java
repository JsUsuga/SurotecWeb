package service.news;

import model.domain.news.ProjectAcademic;
import repository.news.ProjectAcademicRepository;
import repository.news.ProjectAcademicRepositoryImpl;

import java.util.List;
import java.util.Scanner;

public class ProjectAcademicServiceImpl implements ProjectAcademicService {
    private final ProjectAcademicRepository projectRepository = new ProjectAcademicRepositoryImpl();
    private final Scanner sc = new Scanner(System.in);

    @Override
    public void createProject(ProjectAcademic project) {
        System.out.println("\nCREAR NUEVO PROYECTO ACADÉMICO");
        System.out.print("Ingrese el título del proyecto: ");
        project.setProjectTitle(sc.nextLine());
        System.out.print("Ingrese la descripción del proyecto: ");
        project.setProjectDescription(sc.nextLine());
        System.out.print("Estado del proyecto (ej: OPEN / CLOSED): ");
        project.setProjectStatus(sc.nextLine());

        projectRepository.create(project);
        System.out.println("Proyecto creado con éxito: " + project.getProjectTitle());
    }

    @Override
    public ProjectAcademic findProjectById(int id) {
        System.out.println("\nBUSCAR PROYECTO POR ID ");
        System.out.print("Ingrese el ID del proyecto: ");
        id = sc.nextInt();
        sc.nextLine();

        ProjectAcademic project = projectRepository.readById(id);
        if (project != null) {
            System.out.println("Proyecto encontrado:");
            System.out.println(project);
        } else {
            System.out.println("No se encontró ningún proyecto con ID " + id);
        }
        return project;
    }

    @Override
    public List<ProjectAcademic> listAllProjects() {
        System.out.println("\nLISTADO DE TODOS LOS PROYECTOS ACADÉMICOS");
        List<ProjectAcademic> projects = projectRepository.readAll();

        if (projects.isEmpty()) {
            System.out.println("No hay proyectos registrados en el sistema.");
        } else {
            int count = 1;
            for (ProjectAcademic project : projects) {
                System.out.println("🔹 Proyecto #" + count++);
                System.out.println("   ID: " + project.getProjectId());
                System.out.println("   Título: " + project.getProjectTitle());
                System.out.println("   Descripción: " + project.getProjectDescription());
                System.out.println("   Estado: " + project.getProjectStatus());
                System.out.println("-----------------------------------");
            }
            System.out.println("Total de proyectos: " + projects.size());
        }

        return projects;
    }

    @Override
    public void updateProject(ProjectAcademic project) {
        System.out.println("\nACTUALIZAR PROYECTO");
        System.out.print("Ingrese el ID del proyecto a actualizar: ");
        int id = sc.nextInt();
        sc.nextLine();

        ProjectAcademic existing = projectRepository.readById(id);
        if (existing == null) {
            System.out.println("No existe un proyecto con ID " + id);
            return;
        }

        System.out.println("Datos actuales: " + existing);

        System.out.print("Nuevo título (enter para mantener): ");
        String title = sc.nextLine();
        if (!title.isEmpty()) existing.setProjectTitle(title);

        System.out.print("Nueva descripción (enter para mantener): ");
        String desc = sc.nextLine();
        if (!desc.isEmpty()) existing.setProjectDescription(desc);

        System.out.print("Nuevo estado (enter para mantener): ");
        String status = sc.nextLine();
        if (!status.isEmpty()) existing.setProjectStatus(status);

        projectRepository.update(existing);
        System.out.println("Proyecto actualizado correctamente.");
    }

    @Override
    public void deleteProject(int id) {
        System.out.println("\nELIMINAR PROYECTO");
        System.out.print("Ingrese el ID del proyecto a eliminar: ");
        id = sc.nextInt();
        sc.nextLine();

        ProjectAcademic project = projectRepository.readById(id);
        if (project == null) {
            System.out.println("No se encontró un proyecto con ese ID.");
        } else {
            projectRepository.delete(id);
            System.out.println("Proyecto eliminado: " + project.getProjectTitle());
        }
    }

    @Override
    public ProjectAcademic getProjectById(int projectId) {
        return projectRepository.readById(projectId);
    }

}
