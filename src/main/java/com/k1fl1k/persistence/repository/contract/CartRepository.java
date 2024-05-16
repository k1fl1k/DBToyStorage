package com.k1fl1k.persistence.repository.contract;

import com.k1fl1k.persistence.entity.Cart;
import com.k1fl1k.persistence.repository.Repository;
import java.util.Set;
import java.util.UUID;
/**
 * Interface for managing Cart entities.
 */
public interface CartRepository extends Repository<Cart>, ManyToMany {
    /**
     * Finds all cart items by client ID and toy ID.
     *
     * @param clientId The ID of the client.
     * @param toyId    The ID of the toy.
     * @return A set of cart items matching the specified client ID and toy ID.
     */
    Set<Cart> findAllid(UUID clientId, UUID toyId);
}
