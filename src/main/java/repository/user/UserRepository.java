package repository.user;

import model.domain.user.User;

import java.util.List;

public interface UserRepository {
    void create(User user);
    List<User> readAll();
    User readById(int id);
    User findByEmail(String email);
    void assignRole(int userId, String roleName);
    void updateUser(User user, String field, String value);
    void deleteUser(int id);
}