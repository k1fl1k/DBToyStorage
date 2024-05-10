package com.k1fl1k.persistence.entity;

import java.util.UUID;

public record Sections(UUID id,
                       String name,
                       UUID categoryId) implements Entity, Comparable<Sections> {

    @Override
    public int compareTo(Sections o) {
        return this.name.compareTo(o.name);
    }
}
