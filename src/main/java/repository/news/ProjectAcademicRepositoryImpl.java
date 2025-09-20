package repository.news;

import model.domain.news.ProjectAcademic;
import repository.config.DatabaseConnection;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectAcademicRepositoryImpl implements ProjectAcademicRepository{

    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    @Override
    public void create(ProjectAcademic project) {
        String sql = "INSERT INTO project_academic (project_title, project_description, project_status) VALUES (?, ?, ?)";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, project.getProjectTitle());
            stmt.setString(2, project.getProjectDescription());
            stmt.setString(3, project.getProjectStatus());

            int affected = stmt.executeUpdate();
            if (affected == 0) {
                System.out.println("No se insertó el proyecto (0 filas afectadas).");
                return;
            }
            try (ResultSet gk = stmt.getGeneratedKeys()) {
                if (gk.next()) {
                    int id = gk.getInt(1);
                    project.setProjectId(id);
                    System.out.println("Proyecto creado con id = " + id);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error creando proyecto: " + ex.getMessage());
        }
    }

    @Override
    public List<ProjectAcademic> readAll() {
        List<ProjectAcademic> projects = new ArrayList<>();
        String sql = "SELECT * FROM project_academic";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ProjectAcademic p = new ProjectAcademic(
                        rs.getInt("project_id"),
                        rs.getString("project_status"),
                        rs.getString("project_description"),
                        rs.getString("project_title")
                );
                projects.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("Error leyendo proyectos: " + ex.getMessage());
        }
        return projects;
    }

    @Override
    public ProjectAcademic readById(int id) {
        String sql = "SELECT * FROM project_academic WHERE project_id = ?";
        ProjectAcademic p = null;
        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    p = new ProjectAcademic(
                            rs.getInt("project_id"),
                            rs.getString("project_status"),
                            rs.getString("project_description"),
                            rs.getString("project_title")
                    );
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error leyendo proyecto por id: " + ex.getMessage());
        }
        return p;
    }

    @Override
    public void update(ProjectAcademic project) {
        String sql = "UPDATE project_academic SET project_title = ?, project_description = ?, project_status = ? WHERE project_id = ?";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, project.getProjectTitle());
            stmt.setString(2, project.getProjectDescription());
            stmt.setString(3, project.getProjectStatus());
            stmt.setInt(4, (int) project.getProjectId()); // tu getter puede devolver long, casteo por seguridad

            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "Proyecto actualizado." : "Proyecto no encontrado.");
        } catch (SQLException ex) {
            System.out.println("Error actualizando proyecto: " + ex.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM project_academic WHERE project_id = ?";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "Proyecto eliminado." : "Proyecto no encontrado.");
        } catch (SQLException ex) {
            System.out.println("Error eliminando proyecto: " + ex.getMessage());
        }
    }
}
