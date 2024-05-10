package com.k1fl1k.persistence.context.impl;

import com.k1fl1k.persistence.context.GenericUnitOfWork;
import com.k1fl1k.persistence.entity.Manufacture;
import com.k1fl1k.persistence.repository.contract.ManufactureRepository;
import org.springframework.stereotype.Component;

@Component
public class ManufactureContext extends GenericUnitOfWork<Manufacture> {

    public final ManufactureRepository repository;

    public ManufactureContext(ManufactureRepository repository){
        super(repository);
        this.repository = repository;
    }
}
