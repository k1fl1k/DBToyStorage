package com.k1fl1k.persistence.repository.contract;

import com.k1fl1k.persistence.entity.Users;
import com.k1fl1k.persistence.repository.Repository;
import java.util.Optional;

public interface UserRepository extends Repository<Users> {

    Optional<Users> findByLogin(String login);

    Optional<Users> findByName(String name);
}
