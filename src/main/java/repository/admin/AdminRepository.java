package repository.admin;

import model.domain.user.Administrator;

import java.util.List;

public interface AdminRepository {
    void create(Administrator admin);
    List<Administrator> readAll();
    Administrator readById(int id);
    Administrator findByUserId(int userId);
    void update(Administrator admin, String field, String value);
    void delete(int id);
    void assignRole(int userId, String roleName);
    void assignAdminStatus(int userId, boolean isActive);
    Administrator findByEmail(String email);
}