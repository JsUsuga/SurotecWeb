package model.domain.user;

import model.domain.classification.Role;

public class UserRole {
    private int userId;
    private Role role;

    public UserRole() {
    }

    public UserRole(int userId, Role role) {
        this.userId = userId;
        this.role = role;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    @Override
    public String toString() {
        return "UserRole{" +
                "userId=" + userId +
                ", role=" + role +
                '}';
    }
}