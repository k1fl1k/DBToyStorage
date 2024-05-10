package com.k1fl1k.persistence.entity;

import java.util.UUID;

public record Client(UUID id,
                     String name,
                     String phone,
                     String address,
                     UUID sectionId) implements Entity, Comparable<Client> {

    @Override
    public int compareTo(Client o) {
        return this.name.compareTo(o.name);
    }
}
