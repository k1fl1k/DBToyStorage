package com.k1fl1k.persistence.context.impl;

import com.k1fl1k.persistence.context.GenericUnitOfWork;
import com.k1fl1k.persistence.entity.Category;
import com.k1fl1k.persistence.repository.contract.CategoryRepository;
import org.springframework.stereotype.Component;

/**
 * A context class for managing unit of work operations related to Category entities.
 */
@Component
public class CategoryContext extends GenericUnitOfWork<Category> {

    /** The CategoryRepository instance used for interacting with the underlying data storage. */
    public final CategoryRepository repository;

    /**
     * Constructs a new CategoryContext instance.
     *
     * @param repository The CategoryRepository instance used for interacting with the underlying data storage.
     */
    public CategoryContext(CategoryRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
