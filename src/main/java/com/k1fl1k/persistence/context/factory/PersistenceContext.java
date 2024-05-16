package com.k1fl1k.persistence.context.factory;

import com.k1fl1k.persistence.context.impl.CartContext;
import com.k1fl1k.persistence.context.impl.CategoryContext;
import com.k1fl1k.persistence.context.impl.ClientContext;
import com.k1fl1k.persistence.context.impl.ManufactureContext;
import com.k1fl1k.persistence.context.impl.SectionsContext;
import com.k1fl1k.persistence.context.impl.ToyContext;
import com.k1fl1k.persistence.context.impl.UserContext;
import org.springframework.stereotype.Component;

/**
 * A factory class for creating and managing instances of various persistence contexts.
 */
@Component
public class PersistenceContext {

    /** The CartContext instance for managing cart-related operations. */
    public final CartContext carts;

    /** The CategoryContext instance for managing category-related operations. */
    public final CategoryContext categories;

    /** The ClientContext instance for managing client-related operations. */
    public final ClientContext clients;

    /** The ManufactureContext instance for managing manufacture-related operations. */
    public final ManufactureContext manufacturers;

    /** The SectionsContext instance for managing sections-related operations. */
    public final SectionsContext sections;

    /** The ToyContext instance for managing toy-related operations. */
    public final ToyContext toys;

    /** The UserContext instance for managing user-related operations. */
    public final UserContext users;

    /**
     * Constructs a new PersistenceContext instance.
     *
     * @param cartContext        The CartContext instance for managing cart-related operations.
     * @param categoryContext    The CategoryContext instance for managing category-related operations.
     * @param clientContext      The ClientContext instance for managing client-related operations.
     * @param manufactureContext The ManufactureContext instance for managing manufacture-related operations.
     * @param sectionsContext    The SectionsContext instance for managing sections-related operations.
     * @param toyContext         The ToyContext instance for managing toy-related operations.
     * @param userContext        The UserContext instance for managing user-related operations.
     */
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
