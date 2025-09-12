package service.user;

import model.domain.user.User;
import java.util.List;

public interface UserService {
    void createUser(User user);
    List<User> getAllUsers();
    User getUserById(int id);
    User getUserByEmail(String email);
    void assignRoleToUser(int userId, String roleName);
    void updateUser(User user, String field, String value);
    void deleteUser(int id);
}