package com.k1fl1k.persistence.context.impl;

import com.k1fl1k.persistence.context.GenericUnitOfWork;
import com.k1fl1k.persistence.entity.Users;
import com.k1fl1k.persistence.repository.contract.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserContext extends GenericUnitOfWork<Users> {

    public final UserRepository repository;

    protected UserContext(UserRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
