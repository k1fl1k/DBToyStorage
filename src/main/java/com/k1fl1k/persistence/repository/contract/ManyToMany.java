package com.k1fl1k.persistence.repository.contract;

import java.util.UUID;

public interface ManyToMany {
    boolean attach(UUID firstId, UUID secondId);

    boolean detach(UUID firstId, UUID secondId);

}
