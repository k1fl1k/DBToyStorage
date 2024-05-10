package com.k1fl1k.persistence.entity;

import java.util.UUID;

public record Manufacture(UUID id,
                          String name,
                          String description) implements Entity, Comparable<Manufacture> {

    @Override
    public int compareTo(Manufacture o) {
        return this.name.compareTo(o.name);
    }
}
