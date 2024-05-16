package com.k1fl1k.persistence.context.impl;

import com.k1fl1k.persistence.context.GenericUnitOfWork;
import com.k1fl1k.persistence.entity.Users;
import com.k1fl1k.persistence.repository.contract.UserRepository;
import org.springframework.stereotype.Component;

/**
 * A context class for managing unit of work operations related to Users entities.
 */
@Component
public class UserContext extends GenericUnitOfWork<Users> {

    /** The UserRepository instance used for interacting with the underlying data storage. */
    public final UserRepository repository;

    /**
     * Constructs a new UserContext instance.
     *
     * @param repository The UserRepository instance used for interacting with the underlying data storage.
     */
    protected UserContext(UserRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
