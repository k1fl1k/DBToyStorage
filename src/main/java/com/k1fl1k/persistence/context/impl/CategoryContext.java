package com.k1fl1k.persistence.context.impl;

import com.k1fl1k.persistence.context.GenericUnitOfWork;
import com.k1fl1k.persistence.entity.Category;
import com.k1fl1k.persistence.repository.contract.CategoryRepository;
import org.springframework.stereotype.Component;

@Component
public class CategoryContext extends GenericUnitOfWork<Category> {

    public final CategoryRepository repository;

    public CategoryContext(CategoryRepository repository){
        super(repository);
        this.repository = repository;
    }
}
