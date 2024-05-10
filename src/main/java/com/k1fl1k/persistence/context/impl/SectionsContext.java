package com.k1fl1k.persistence.context.impl;

import com.k1fl1k.persistence.context.GenericUnitOfWork;
import com.k1fl1k.persistence.entity.Sections;
import com.k1fl1k.persistence.repository.contract.SectionsRepository;
import org.springframework.stereotype.Component;

@Component
public class SectionsContext extends GenericUnitOfWork<Sections> {

    public final SectionsRepository repository;

    public SectionsContext(SectionsRepository repository){
        super(repository);
        this.repository = repository;
    }
}
