package com.k1fl1k.persistence.context.impl;

import com.k1fl1k.persistence.context.GenericUnitOfWork;
import com.k1fl1k.persistence.entity.Toy;
import com.k1fl1k.persistence.repository.contract.ToyRepository;
import org.springframework.stereotype.Component;

/**
 * A context class for managing unit of work operations related to Toy entities.
 */
@Component
public class ToyContext extends GenericUnitOfWork<Toy> {

    /** The ToyRepository instance used for interacting with the underlying data storage. */
    public final ToyRepository repository;

    /**
     * Constructs a new ToyContext instance.
     *
     * @param repository The ToyRepository instance used for interacting with the underlying data storage.
     */
    protected ToyContext(ToyRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
