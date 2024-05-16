package com.k1fl1k.persistence.repository.contract;

/**
 * Enum representing table names in the database.
 */
public enum TableNames {
    CART("cart"),
    CATEGORY("category"),
    CLIENT("client"),
    MANUFACTURE("manufacture"),
    SECTIONS("sections"),
    TOY("toy"),
    USER("users");

    private final String name;

    TableNames(String name) {
        this.name = name;
    }

    /**
     * Get the name of the table.
     *
     * @return The name of the table.
     */
    public String getName() {
        return name;
    }
}
