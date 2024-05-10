package com.k1fl1k.persistence.entity;

import java.util.UUID;

public record Category(UUID id,
                       String name,
                       String description) implements Entity, Comparable<Category> {
    @Override
    public int compareTo(Category o) {
        return this.name.compareTo(o.name);
    }
}
