package com.k1fl1k.persistence.context.impl;

import com.k1fl1k.persistence.context.GenericUnitOfWork;
import com.k1fl1k.persistence.entity.Manufacture;
import com.k1fl1k.persistence.repository.contract.ManufactureRepository;
import org.springframework.stereotype.Component;

/**
 * A context class for managing unit of work operations related to Manufacture entities.
 */
@Component
public class ManufactureContext extends GenericUnitOfWork<Manufacture> {

    /** The ManufactureRepository instance used for interacting with the underlying data storage. */
    public final ManufactureRepository repository;

    /**
     * Constructs a new ManufactureContext instance.
     *
     * @param repository The ManufactureRepository instance used for interacting with the underlying data storage.
     */
    public ManufactureContext(ManufactureRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
