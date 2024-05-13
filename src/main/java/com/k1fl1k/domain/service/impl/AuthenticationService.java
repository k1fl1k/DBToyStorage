package com.k1fl1k.domain.service.impl;

import com.k1fl1k.domain.exception.UserAlreadyAuthenticatedException;
import com.k1fl1k.persistence.context.factory.PersistenceContext;
import com.k1fl1k.persistence.entity.Users;
import com.k1fl1k.persistence.repository.contract.UserRepository;
import java.util.Optional;
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

        Optional<Users> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isPresent()) {
            Users foundedUser = optionalUser.get();
            if (password.equals(foundedUser.getPassword())) {
                user = foundedUser;
                return true;
            }
        }

        return false;
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
