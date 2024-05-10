package com.k1fl1k.persistence.entity;

import java.util.UUID;

public record Cart(UUID id,
                   UUID clientId,
                   UUID toyId) implements Entity, Comparable<Cart> {

    @Override
    public int compareTo(Cart o) {
        return this.toyId.compareTo(o.toyId);
    }
}
