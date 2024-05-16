package com.k1fl1k.domain.service.impl;

import com.k1fl1k.domain.exception.UserAlreadyAuthenticatedException;
import com.k1fl1k.persistence.context.factory.PersistenceContext;
import com.k1fl1k.persistence.entity.Users;
import com.k1fl1k.persistence.repository.contract.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
/**
 * Service class responsible for user authentication and managing authentication state.
 */
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private Users user;

    /**
     * Constructs an AuthenticationService with the provided PersistenceContext.
     *
     * @param persistenceContext The PersistenceContext containing the UserRepository for user data access.
     */
    AuthenticationService(PersistenceContext persistenceContext) {
        this.userRepository = persistenceContext.users.repository;
    }

    /**
     * Authenticates a user with the provided login and password.
     *
     * @param login    The user's login.
     * @param password The user's password.
     * @return true if authentication is successful, false otherwise.
     * @throws UserAlreadyAuthenticatedException if a user is already authenticated.
     */
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

    /**
     * Checks if a user is authenticated.
     *
     * @return true if a user is authenticated, false otherwise.
     */
    public boolean isAuthenticated() {
        return user != null;
    }

    /**
     * Retrieves the authenticated user.
     *
     * @return The authenticated Users object, or null if no user is authenticated.
     */
    public Users getUser() {
        return user;
    }

    /**
     * Logs out the currently authenticated user.
     *
     * @throws UserAlreadyAuthenticatedException if no user is currently authenticated.
     */
    public void logout() {
        if (user == null) {
            throw new UserAlreadyAuthenticatedException("Ви ще не автентифікавані.");
        }
        user = null;
    }

}
