package service.user;

import model.domain.user.Administrator;
import model.domain.classification.Role;

import java.util.List;

public interface AdminService {
    void createAdmin(Administrator admin);
    List<Administrator> getAllAdmins();
    Administrator getAdminById(int id);
    Administrator getAdminByUserId(int userId);
    void updateAdmin(Administrator admin, String field, String value);
    void deleteAdmin(int id);
    void assignRoleToAdmin(int userId, Role roleName);
    void assignAdminStatus(int userId, boolean isActive);
    Administrator findByEmail(String email);
    Administrator findByUsername(String username);
}