package org.david.service;

import lombok.AllArgsConstructor;
import org.david.persistance.dao.BoardColumnDAO;
import org.david.persistance.entity.Board_ColumnEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class BoardColumnQueryService {

    private final Connection connection;

    public Optional<Board_ColumnEntity> findById(final long id) throws SQLException {
        var dao = new BoardColumnDAO(connection);
        return dao.findById(id);
    }
}
