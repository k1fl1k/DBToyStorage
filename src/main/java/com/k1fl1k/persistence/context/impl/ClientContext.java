package com.k1fl1k.persistence.context.impl;

import com.k1fl1k.persistence.context.GenericUnitOfWork;
import com.k1fl1k.persistence.entity.Client;
import com.k1fl1k.persistence.repository.contract.ClientRepository;
import org.springframework.stereotype.Component;

/**
 * A context class for managing unit of work operations related to Client entities.
 */
@Component
public class ClientContext extends GenericUnitOfWork<Client> {

    /** The ClientRepository instance used for interacting with the underlying data storage. */
    public final ClientRepository repository;

    /**
     * Constructs a new ClientContext instance.
     *
     * @param repository The ClientRepository instance used for interacting with the underlying data storage.
     */
    public ClientContext(ClientRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
