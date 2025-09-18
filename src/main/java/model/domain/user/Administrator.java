package model.domain.user;

public class Administrator extends User {
    private String permissions;

    public Administrator() {
    }

    public Administrator(int id, String firstName, String lastName, String username, String password, String email, String permissions) {
        super(id, firstName, lastName, username, password, email);
        this.permissions = permissions;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "permissions='" + permissions + '\'' +
                "} " + super.toString();
    }
}