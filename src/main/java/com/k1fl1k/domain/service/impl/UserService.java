package com.k1fl1k.domain.service.impl;

import com.k1fl1k.domain.dto.UserStoreDto;
import com.k1fl1k.domain.dto.UserUpdateDto;
import com.k1fl1k.domain.exception.AccessDeniedException;
import com.k1fl1k.domain.exception.ValidationException;
import com.k1fl1k.persistence.context.factory.PersistenceContext;
import com.k1fl1k.persistence.context.impl.UserContext;
import com.k1fl1k.persistence.entity.Users;
import com.k1fl1k.persistence.entity.Users.UsersRole;
import com.k1fl1k.persistence.exception.EntityNotFoundException;
import com.k1fl1k.persistence.repository.contract.UserRepository;
import jakarta.validation.Validator;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserContext userContext;
    private final UserRepository userRepository;
    private final AuthorizeService authorizeService;
    private final FileService fileService;
    private final Validator validator;

    public UserService(PersistenceContext persistenceContext, AuthorizeService authorizeService,
        FileService fileService, Validator validator) {
        this.userContext = persistenceContext.users;
        this.userRepository = persistenceContext.users.repository;
        this.authorizeService = authorizeService;
        this.fileService = fileService;
        this.validator = validator;
    }

    public Users findById(UUID id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Не вдалось знайти користувача"));
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
        return userContext.getEntity();
    }

    public Users update(UserUpdateDto userUpdateDto) {
        var violations = validator.validate(userUpdateDto);
        if (!violations.isEmpty()) {
            throw ValidationException.create("оновленні користувача", violations);
        }

        Users oldUser = findById(userUpdateDto.id());

        if (!authorizeService.canUpdate(oldUser)) {
            throw AccessDeniedException.notAuthorOrBannedUser("оновлювати користувача");
        }
        Users user = new Users(
            userUpdateDto.id(),
            userUpdateDto.login(),
            userUpdateDto.password(),
            userUpdateDto.role(),
            userUpdateDto.name()
        );

        userContext.registerModified(user);
        userContext.commit();
        return userContext.getEntity();
    }
}