package model.domain.user;

import model.domain.classification.Role;

public class Administrator extends User {
    private String permissions;

    public Administrator() {
    }

    public Administrator(int id, String firstName, String lastName, String username, String password, String email, String permissions) {
        super(id, firstName, lastName, username, password, email);
        this.permissions = permissions;
    }

    public Administrator(int id, String firstName, String lastName, String username, String password, String email, Role role) {
        super(id, firstName, lastName, username, password, email);
        this.permissions = (role != null) ? role.name() : "ADMIN";
    }

    public String getPermissions() { return permissions; }
    public void setPermissions(String permissions) { this.permissions = permissions; }

    @Override
    public String toString() {
        return "Administrator{" +
                "permissions='" + permissions + '\'' +
                "} " + super.toString();
    }
}