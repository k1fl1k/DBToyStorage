package com.k1fl1k.persistence.context.impl;

import com.k1fl1k.persistence.context.GenericUnitOfWork;
import com.k1fl1k.persistence.entity.Sections;
import com.k1fl1k.persistence.repository.contract.SectionsRepository;
import org.springframework.stereotype.Component;

/**
 * A context class for managing unit of work operations related to Sections entities.
 */
@Component
public class SectionsContext extends GenericUnitOfWork<Sections> {

    /** The SectionsRepository instance used for interacting with the underlying data storage. */
    public final SectionsRepository repository;

    /**
     * Constructs a new SectionsContext instance.
     *
     * @param repository The SectionsRepository instance used for interacting with the underlying data storage.
     */
    public SectionsContext(SectionsRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
