package com.k1fl1k.domain.service.impl;

import com.k1fl1k.domain.dto.UserStoreDto;
import com.k1fl1k.domain.exception.ValidationException;
import com.k1fl1k.persistence.context.factory.PersistenceContext;
import com.k1fl1k.persistence.context.impl.UserContext;
import com.k1fl1k.persistence.entity.Users;
import com.k1fl1k.persistence.entity.Users.UsersRole;
import jakarta.validation.Validator;
import java.util.Objects;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for creating users based on UserStoreDto instances.
 */
@Service
public class UserService {

    private final UserContext userContext;
    private final Validator validator;

    /**
     * Constructs a UserService with the provided PersistenceContext and Validator.
     *
     * @param persistenceContext The PersistenceContext containing the UserContext for managing user entities.
     * @param validator          The Validator used for validating UserStoreDto instances.
     */
    public UserService(PersistenceContext persistenceContext, Validator validator) {
        this.userContext = persistenceContext.users;
        this.validator = validator;
    }

    /**
     * Creates a new user based on the provided UserStoreDto.
     *
     * @param userStoreDto The UserStoreDto containing user data.
     * @return The created Users object.
     * @throws ValidationException if the provided UserStoreDto fails validation.
     */
    public Users create(UserStoreDto userStoreDto) {
        var violations = validator.validate(userStoreDto);
        if (!violations.isEmpty()) {
            throw ValidationException.create("збереженні користувача", violations);
        }

        Users user = new Users(
            userStoreDto.id(),
            userStoreDto.login(),
            userStoreDto.password(),
            Objects.nonNull(userStoreDto.role()) ? userStoreDto.role() : UsersRole.CLIENT,
            userStoreDto.name()
        );

        userContext.registerNew(user);
        userContext.commit();
        return null;
    }
}