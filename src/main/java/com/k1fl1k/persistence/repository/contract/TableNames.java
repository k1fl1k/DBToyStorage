package com.k1fl1k.persistence.repository.contract;

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

    public String getName() {
        return name;
    }
}
