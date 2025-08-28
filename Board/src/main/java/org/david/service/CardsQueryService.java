package org.david.service;

import lombok.AllArgsConstructor;
import org.david.dto.CardsDetails;
import org.david.persistance.dao.CardDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;


@AllArgsConstructor
public class CardsQueryService {
    private final Connection connection;

    public Optional<CardsDetails> findById(long id) throws SQLException {
        var dao = new CardDAO(connection);
        return dao.findById(id);
    }
}
