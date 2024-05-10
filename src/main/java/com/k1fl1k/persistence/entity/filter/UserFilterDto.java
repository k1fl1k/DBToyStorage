package com.k1fl1k.persistence.entity.filter;

import com.k1fl1k.persistence.entity.Users;

public record UserFilterDto(String login, String password, Users.UsersRole role, String name) { }

