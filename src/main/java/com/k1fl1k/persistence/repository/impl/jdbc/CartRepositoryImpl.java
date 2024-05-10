package com.k1fl1k.persistence.repository.impl.jdbc;

import static java.lang.StringTemplate.STR;

import com.k1fl1k.persistence.entity.Cart;
import com.k1fl1k.persistence.repository.GenericJdbcRepository;
import com.k1fl1k.persistence.repository.contract.CartRepository;
import com.k1fl1k.persistence.repository.contract.TableNames;
import com.k1fl1k.persistence.repository.mapper.impl.CartRowMapper;
import com.k1fl1k.persistence.util.ConnectionManager;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepositoryImpl extends GenericJdbcRepository<Cart> implements CartRepository {

    private final ConnectionManager connectionManager;
    private final CartRowMapper cartRowMapper;
    private final JdbcManyToMany<Cart> jdbcManyToMany;

    public CartRepositoryImpl(
        ConnectionManager connectionManager,
        CartRowMapper cartRowMapper,
        JdbcManyToMany<Cart> jdbcManyToMany) {
        super(connectionManager, cartRowMapper, TableNames.CART.getName());
        this.connectionManager = connectionManager;
        this.cartRowMapper = cartRowMapper;
        this.jdbcManyToMany = jdbcManyToMany;
    }

    @Override
    protected List<String> tableAttributes() {
        return List.of(
            "client_id",
            "toy_id"
        );
    }

    @Override
    protected List<Object> tableValues(Cart cart) {
        return List.of(
            cart.clientId(),
            cart.toyId()
        );
    }

    @Override
    public Set<Cart> findAllid(UUID clientId, UUID toyId) {
        String sql = "SELECT * FROM cart WHERE client_id = ? AND toy_id = ?";
        return jdbcManyToMany.getByPivot(
            clientId,
            sql,
            cartRowMapper,
            STR."Помилка при отриманні всіх іграшок категорії по id: \{clientId}"); // Adjust the error message accordingly
    }

    @Override
    public boolean attach(UUID toyId, UUID clientId) {
        final String sql =
            """
            UPDATE cart
            SET client_id = ?
            WHERE toy_id = ?;
            """;
        return jdbcManyToMany.executeUpdate(
            clientId, toyId, sql, "Помилка при додаванні кошика до іграшки");
    }

    @Override
    public boolean detach(UUID toyId, UUID clientId) {
        final String sql =
            """
            UPDATE cart
            SET client_id = NULL
            WHERE toy_id = ? AND client_id = ?;
            """;
        return jdbcManyToMany.executeUpdate(
            toyId, clientId, sql, "Помилка при видаленні кошика з іграшки");
    }

}
