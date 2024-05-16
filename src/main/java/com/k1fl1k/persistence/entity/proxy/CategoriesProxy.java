package com.k1fl1k.persistence.entity.proxy;

import com.k1fl1k.persistence.entity.Category;
import com.k1fl1k.persistence.repository.contract.CategoryRepository;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import org.springframework.context.ApplicationContext;

/**
 * A proxy class to retrieve categories related to a specific toy.
 */
public class CategoriesProxy implements Categories {
    private final ApplicationContext applicationContext;

    /**
     * Constructs a new CategoriesProxy instance.
     *
     * @param applicationContext The Spring application context.
     */
    public CategoriesProxy(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Retrieves a set of categories related to the specified toy ID.
     *
     * @param toyId The ID of the toy to retrieve related categories.
     * @return A set of categories related to the specified toy.
     */
    @Override
    public Set<Category> get(UUID toyId) {
        // Get the CategoryRepository bean from the application context
        var categoryRepository = applicationContext.getBean(CategoryRepository.class);
        // Retrieve categories related to the specified toy ID from the repository
        return Collections.unmodifiableSet(categoryRepository.findAllByToyId(toyId));
    }
}
