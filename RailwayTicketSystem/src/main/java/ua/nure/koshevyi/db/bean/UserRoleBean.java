package ua.nure.koshevyi.db.bean;

import java.io.Serializable;
import java.util.Objects;

public class UserRoleBean implements Serializable {

    private static final long serialVersionUID = 8842634201511424980L;

    private int id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String  role;

    public UserRoleBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleBean userRole = (UserRoleBean) o;
        return id == userRole.id &&
                Objects.equals(login, userRole.login) &&
                Objects.equals(password, userRole.password) &&
                Objects.equals(firstName, userRole.firstName) &&
                Objects.equals(lastName, userRole.lastName) &&
                Objects.equals(role, userRole.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, firstName, lastName, role);
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}