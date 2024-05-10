package com.k1fl1k.persistence.context.impl;

import com.k1fl1k.persistence.context.GenericUnitOfWork;
import com.k1fl1k.persistence.entity.Client;
import com.k1fl1k.persistence.repository.contract.ClientRepository;
import org.springframework.stereotype.Component;

@Component
public class ClientContext extends GenericUnitOfWork<Client> {

    public final ClientRepository repository;

    public ClientContext(ClientRepository repository){
        super(repository);
        this.repository = repository;
    }
}
