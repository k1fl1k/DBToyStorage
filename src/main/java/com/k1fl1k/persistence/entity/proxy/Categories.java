package com.k1fl1k.persistence.entity.proxy;

import com.k1fl1k.persistence.entity.Category;
import java.util.Set;
import java.util.UUID;

public interface Categories {
    Set<Category> get(UUID id);
}
