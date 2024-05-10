package com.k1fl1k.persistence.entity;

import java.util.UUID;

public record Toy(UUID id,
                  String name,
                  String description,
                  float price,
                  UUID categoryId,
                  UUID manufactureId) implements Entity, Comparable<Toy> {

    @Override
    public int compareTo(Toy toy){
        return this.name.compareTo(toy.name);
    }
}
