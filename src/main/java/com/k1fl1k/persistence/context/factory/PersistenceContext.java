package com.k1fl1k.persistence.context.factory;


import com.k1fl1k.persistence.context.impl.CartContext;
import com.k1fl1k.persistence.context.impl.CategoryContext;
import com.k1fl1k.persistence.context.impl.ClientContext;
import com.k1fl1k.persistence.context.impl.ManufactureContext;
import com.k1fl1k.persistence.context.impl.SectionsContext;
import com.k1fl1k.persistence.context.impl.ToyContext;
import com.k1fl1k.persistence.context.impl.UserContext;
import org.springframework.stereotype.Component;

@Component
public class PersistenceContext {

    public final CartContext carts;
    public final CategoryContext categories;
    public final ClientContext clients;
    public final ManufactureContext manufacturers;
    public final SectionsContext sections;
    public final ToyContext toys;
    public final UserContext users;

    public PersistenceContext(
        CartContext cartContext,
        CategoryContext categoryContext,
        ClientContext clientContext,
        ManufactureContext manufactureContext,
        SectionsContext sectionsContext,
        ToyContext toyContext,
        UserContext userContext) {
        this.carts = cartContext;
        this.categories = categoryContext;
        this.clients = clientContext;
        this.manufacturers = manufactureContext;
        this.sections = sectionsContext;
        this.toys = toyContext;
        this.users = userContext;
    }
}
