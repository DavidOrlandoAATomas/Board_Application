package org.david.service;

import lombok.AllArgsConstructor;
import org.david.dto.BoardColumnDTO;
import org.david.dto.BoardDetailsDTO;
import org.david.persistance.dao.BoardColumnDAO;
import org.david.persistance.dao.BoardDAO;
import org.david.persistance.entity.BoardEntity;
import org.david.persistance.entity.Board_ColumnEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class BoardQueryService {

    private final Connection connection;

    public Optional<BoardEntity> findById(final long id) throws SQLException{
        var dao = new BoardDAO(connection);
        var boardColumnDAO = new BoardColumnDAO(connection);
        var optinal = dao.findById(id);
        if(optinal.isPresent()){
            var entity = optinal.get();
            entity.setBoardColumns(boardColumnDAO.findByBoardId(entity.getId()));
            return Optional.of(entity);
        }
        return Optional.empty();
    }
    public Optional<BoardDetailsDTO> showBoardDetails(final long id) throws SQLException{
        var dao = new BoardDAO(connection);
        var boardColumnDAO = new BoardColumnDAO(connection);
        var optinal = dao.findById(id);
        if(optinal.isPresent()){
            var entity = optinal.get();
            var columns = boardColumnDAO.findByBoardIdWithDetails(entity.getId());
            var dto = new BoardDetailsDTO(entity.getId(), entity.getName(), columns);
            entity.setBoardColumns(boardColumnDAO.findByBoardId(entity.getId()));
            return Optional.of(dto);
        }
        return Optional.empty();
    }
}
