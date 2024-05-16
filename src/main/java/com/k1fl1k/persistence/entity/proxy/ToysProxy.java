package com.k1fl1k.persistence.entity.proxy;

import com.k1fl1k.persistence.entity.Toy;
import com.k1fl1k.persistence.repository.contract.ToyRepository;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import org.springframework.context.ApplicationContext;

/**
 * A proxy class to retrieve toys from the repository.
 */
public class ToysProxy implements Toys {
    private final ApplicationContext applicationContext;

    /**
     * Constructs a new ToysProxy instance.
     *
     * @param applicationContext The Spring application context.
     */
    public ToysProxy(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Retrieves a set of toys by category ID.
     *
     * @param categoryId The ID of the category to filter toys.
     * @return A set of toys belonging to the specified category.
     */
    @Override
    public Set<Toy> get(UUID categoryId) {
        var toyRepository = applicationContext.getBean(ToyRepository.class);
        return Collections.unmodifiableSet(toyRepository.findAllToysCategory(categoryId));
    }
}
