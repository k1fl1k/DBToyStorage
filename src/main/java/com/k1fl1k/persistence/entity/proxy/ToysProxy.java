package com.k1fl1k.persistence.entity.proxy;

import com.k1fl1k.persistence.entity.Toy;
import com.k1fl1k.persistence.repository.contract.ToyRepository;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import org.springframework.context.ApplicationContext;

public class ToysProxy implements Toys {
    private final ApplicationContext applicationContext;

    public ToysProxy(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Set<Toy> get(UUID categoryId) {
        var toyRepository = applicationContext.getBean(ToyRepository.class);
        return Collections.unmodifiableSet(toyRepository.findAllToysCategory(categoryId));
    }
}
