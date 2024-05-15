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

@Service
public class UserService {

    private final UserContext userContext;
    private final Validator validator;

    public UserService(PersistenceContext persistenceContext, Validator validator) {
        this.userContext = persistenceContext.users;
        this.validator = validator;
    }


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