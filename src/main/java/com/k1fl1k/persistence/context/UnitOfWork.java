package com.k1fl1k.persistence.context;

import com.k1fl1k.persistence.entity.Entity;
import java.util.UUID;

public interface UnitOfWork<T extends Entity> {

    String INSERT = "INSERT";
    String DELETE = "DELETE";
    String MODIFY = "MODIFY";

    void registerNew(T entity);

    void registerModified(T entity);

    void registerDeleted(T entity);
    void registerDeleted(UUID id);

    void commit();
}
