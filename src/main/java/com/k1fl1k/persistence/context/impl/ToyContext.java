package com.k1fl1k.persistence.context.impl;

import com.k1fl1k.persistence.context.GenericUnitOfWork;
import com.k1fl1k.persistence.entity.Toy;
import com.k1fl1k.persistence.repository.contract.ToyRepository;
import org.springframework.stereotype.Component;

@Component
public class ToyContext extends GenericUnitOfWork<Toy> {

    public final ToyRepository repository;

    protected ToyContext(ToyRepository repository){
        super(repository);
        this.repository = repository;
    }
}
