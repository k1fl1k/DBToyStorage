package com.k1fl1k.presentation.viewmodel;

import com.k1fl1k.persistence.entity.Users.UsersRole;
import javafx.beans.property.*;

import java.util.UUID;
import java.util.StringJoiner;

/**
 * The {@code UserViewModel} class represents the ViewModel for a user entity in the presentation layer.
 * It encapsulates user properties such as ID, login, password, role, name, phone, and address.
 */
public class UserViewModel {

    private final ObjectProperty<UUID> id = new SimpleObjectProperty<>();
    private final StringProperty login = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final ObjectProperty<UsersRole> role = new SimpleObjectProperty<>();
    private final StringProperty name = new SimpleStringProperty();

    /**
     * Constructs a new {@code UserViewModel} with the specified parameters.
     *
     * @param id       The ID of the user.
     * @param login    The login of the user.
     * @param password The password of the user.
     * @param role     The role of the user.
     * @param name     The name of the user.
     */
    public UserViewModel(UUID id, String login, String password, UsersRole role, String name) {
        this.id.set(id);
        this.login.set(login);
        this.password.set(password);
        this.role.set(role);
        this.name.set(name);
    }

    public String getLogin() {
        return login.get();
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public UUID getId() {
        return id.get();
    }
    public void setId(UUID id) {
        this.id.set(id);
    }

    public String getlogin() {
        return login.get();
    }

    public StringProperty loginProperty() {
        return login;
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public UsersRole getRole() {
        return role.get();
    }

    public ObjectProperty<UsersRole> roleProperty() {
        return role;
    }

    public void setRole(UsersRole role) {
        this.role.set(role);
    }
    public String getname() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserViewModel.class.getSimpleName() + "[", "]")
            .add("id=" + id.get())
            .add("login=" + login.get())
            .add("password=" + password.get())
            .add("Role=" + role.get())
            .add("name=" + name.get())
            .toString();
    }
}
