package com.k1fl1k.persistence.entity.proxy;

import com.k1fl1k.persistence.entity.Toy;
import java.util.Set;
import java.util.UUID;

public interface Toys {
    Set<Toy> get(UUID id);
}
