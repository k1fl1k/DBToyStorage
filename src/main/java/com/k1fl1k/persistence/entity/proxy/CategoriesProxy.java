package com.k1fl1k.persistence.entity.proxy;

import com.k1fl1k.persistence.entity.Category;
import com.k1fl1k.persistence.repository.contract.CategoryRepository;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import org.springframework.context.ApplicationContext;

public class CategoriesProxy implements Categories {
    private final ApplicationContext applicationContext;

    public CategoriesProxy(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Set<Category> get(UUID toyId) {
        var categoryRepository = applicationContext.getBean(CategoryRepository.class);
        return Collections.unmodifiableSet(categoryRepository.findAllByToyId(toyId));
    }
}
