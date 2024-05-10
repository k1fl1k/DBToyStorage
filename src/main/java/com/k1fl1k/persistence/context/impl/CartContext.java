package com.k1fl1k.persistence.context.impl;

import com.k1fl1k.persistence.context.GenericUnitOfWork;
import com.k1fl1k.persistence.entity.Cart;
import com.k1fl1k.persistence.repository.contract.CartRepository;
import org.springframework.stereotype.Component;

@Component
public class CartContext extends GenericUnitOfWork<Cart> {

    public final CartRepository repository;

    public CartContext(CartRepository repository){
        super(repository);
        this.repository = repository;
    }
}
