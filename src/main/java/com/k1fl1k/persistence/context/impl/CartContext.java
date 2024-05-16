package com.k1fl1k.persistence.context.impl;

import com.k1fl1k.persistence.context.GenericUnitOfWork;
import com.k1fl1k.persistence.entity.Cart;
import com.k1fl1k.persistence.repository.contract.CartRepository;
import org.springframework.stereotype.Component;

/**
 * A context class for managing unit of work operations related to Cart entities.
 */
@Component
public class CartContext extends GenericUnitOfWork<Cart> {

    /** The CartRepository instance used for interacting with the underlying data storage. */
    public final CartRepository repository;

    /**
     * Constructs a new CartContext instance.
     *
     * @param repository The CartRepository instance used for interacting with the underlying data storage.
     */
    public CartContext(CartRepository repository){
        super(repository);
        this.repository = repository;
    }
}
