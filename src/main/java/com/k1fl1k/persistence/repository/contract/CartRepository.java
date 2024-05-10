package com.k1fl1k.persistence.repository.contract;

import com.k1fl1k.persistence.entity.Cart;
import com.k1fl1k.persistence.repository.Repository;
import java.util.Set;
import java.util.UUID;

public interface CartRepository extends Repository<Cart>, ManyToMany {
    Set<Cart> findAllid(UUID clientId, UUID toyId);
}
