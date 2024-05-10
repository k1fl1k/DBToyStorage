package com.k1fl1k.persistence.entity;

import java.util.UUID;

public record Users(UUID id,
                   String login,
                   String password,
                   UsersRole role, // Перейменовано Role на UsersRole
                   String name) implements Entity, Comparable<Users>  {

    @Override
    public int compareTo(Users Users) {
        return this.login.compareTo(Users.login);
    }

    public enum UsersRole { // Перейменовано Role на UsersRole
        ADMIN("адміністратор"),
        MODER("менеджер"),
        CLIENT("клієнт");

        String roleName;

        UsersRole(String roleName) {
            this.roleName = roleName;
        }

        public String getName(){
            return roleName;
        }
    }
}
