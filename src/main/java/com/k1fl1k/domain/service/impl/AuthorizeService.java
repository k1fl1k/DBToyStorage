package com.k1fl1k.domain.service.impl;

import com.k1fl1k.persistence.context.factory.PersistenceContext;
import com.k1fl1k.persistence.entity.Toy;
import com.k1fl1k.persistence.entity.Users;
import com.k1fl1k.persistence.entity.Users.UsersRole;
import com.k1fl1k.persistence.exception.EntityNotFoundException;
import com.k1fl1k.persistence.repository.contract.UserRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AuthorizeService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    public AuthorizeService(PersistenceContext persistenceContext,
        AuthenticationService authenticationService) {
        this.userRepository = persistenceContext.users.repository;
        this.authenticationService = authenticationService;
    }

    public boolean canCreate(UUID userId, DtoTypes dtoTypes) {
        Users user = getUser(userId);

        return switch (dtoTypes) {
            case CART, CLIENT -> !user.role().equals(UsersRole.ADMIN);
            case USERS -> true;
        };
    }

    public boolean canUpdate(Toy toy, UUID userId) {
        Users user = getUser(userId);
        return user.id() == userId && !user.role().equals(UsersRole.CLIENT);
    }

    public boolean canDelete(Toy toy, UUID userId) {
        Users user = getUser(userId);
        return user.id() == userId && !user.role().equals(UsersRole.CLIENT);
    }

    // оновлювати можуть дані лише власник акаунту (аутентифікований користувач) або адміністратори
    public boolean canUpdate(Users user) {
        if (user.role() == UsersRole.ADMIN) {
            return true;
        } else {
            return user.id() == authenticationService.getUser().id()
                && !user.role().equals(UsersRole.CLIENT);
        }
    }

    private Users getUser(UUID userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("Користувача не знайдено"));
    }


    public enum DtoTypes {
        CART,
        CLIENT,
        USERS
    }
}