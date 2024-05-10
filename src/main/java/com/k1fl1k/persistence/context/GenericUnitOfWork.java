package com.k1fl1k.persistence.context;


import com.k1fl1k.persistence.entity.Entity;
import com.k1fl1k.persistence.repository.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class GenericUnitOfWork<T extends Entity> implements UnitOfWork<T> {
    final Logger LOGGER = LoggerFactory.getLogger(GenericUnitOfWork.class);
    private final Map<UnitActions, List<T>> context;
    private final Repository<T> repository;

    protected GenericUnitOfWork(Repository<T> repository) {
        this.repository = repository;
        context = new HashMap<>();
    }

    @Override
    public void registerNew(T entity) {
        LOGGER.info("Registering {} for insert in context.", entity.id());
        register(entity, UnitActions.INSERT);
    }

    @Override
    public void registerModified(T entity) {
        LOGGER.info("Registering {} for modify in context.", entity.id());
        register(entity, UnitActions.MODIFY);
    }

    @Override
    public void registerDeleted(T entity) {
        LOGGER.info("Registering {} for delete in context.", entity.id());
        register(entity, UnitActions.DELETE);
    }

    @Override
    public void registerDeleted(UUID id) {
        LOGGER.info("Registering {} for delete in context.", id);
        Entity entity = () -> id;
        register((T)entity, UnitActions.DELETE);
    }

    private void register(T entity, UnitActions operation) {
        var entitiesToOperate = context.get(operation);

        if (entitiesToOperate == null) {
            entitiesToOperate = new ArrayList<>();
        }

        entitiesToOperate.add(entity);
        context.put(operation, entitiesToOperate);
    }

    /** All UnitOfWork operations are batched and executed together on commit only. */
    @Override
    public void commit() {
        if (context.isEmpty()) {
            return;
        }
        LOGGER.info("Commit started");
        if (context.containsKey(UnitActions.INSERT)) {
            commitInsert();
        }
        if (context.containsKey(UnitActions.MODIFY)) {
            commitModify();
        }
        if (context.containsKey(UnitActions.DELETE)) {
            commitDelete();
        }
        LOGGER.info("Commit finished.");
        context.clear();
    }

    // TODO: refactor - batch insert!
    private void commitInsert() {
        var entitiesToBeInserted = context.get(UnitActions.INSERT);
        repository.save(entitiesToBeInserted);
        for (var entity : entitiesToBeInserted) {
            LOGGER.info("Inserting a new entity to table.");
            repository.save(entity);
        }
    }

    private void commitModify() {
        var modifiedEntities = context.get(UnitActions.MODIFY);
        repository.save(modifiedEntities);
        for (var entity : modifiedEntities) {
            LOGGER.info("Modifying {} in table.", entity.id());
            repository.save(entity);
        }
    }

    private void commitDelete() {
        var deletedEntities = context.get(UnitActions.DELETE);
        repository.delete(deletedEntities.stream().map(Entity::id).toList());
        for (var entity : deletedEntities) {
            LOGGER.info("Deleting {} from table.", entity.id());
            repository.delete(entity.id());
        }
    }
}
