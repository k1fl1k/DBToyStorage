package com.k1fl1k.domain.service.impl;

import com.k1fl1k.domain.exception.UserAlreadyAuthenticatedException;
import com.k1fl1k.domain.exception.AuthenticationException;
import com.k1fl1k.persistence.context.factory.PersistenceContext;
import com.k1fl1k.persistence.entity.Users;
import com.k1fl1k.persistence.repository.contract.UserRepository;
import com.password4j.Password;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private Users user;

    AuthenticationService(PersistenceContext persistenceContext) {
        this.userRepository = persistenceContext.users.repository;
    }

    public boolean authenticate(String login, String password) {
        if (user != null) {
            throw new UserAlreadyAuthenticatedException(
                STR."Ви вже авторизувалися як: \{user.login()}");
        }

        Users foundedUser = userRepository.findByLogin(login)
            .orElseThrow(AuthenticationException::new);

        if (!Password.check(password, foundedUser.password()).withBcrypt()) {
            return false;
        }

        user = foundedUser;
        return true;
    }

    public boolean isAuthenticated() {
        return user != null;
    }

    public Users getUser() {
        return user;
    }

    public void logout() {
        if (user == null) {
            throw new UserAlreadyAuthenticatedException("Ви ще не автентифікавані.");
        }
        user = null;
    }

}
