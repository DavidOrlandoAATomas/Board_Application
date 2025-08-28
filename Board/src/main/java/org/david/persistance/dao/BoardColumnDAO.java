package org.david.persistance.dao;


import com.mysql.cj.jdbc.StatementImpl;
import lombok.RequiredArgsConstructor;
import org.david.dto.BoardColumnDTO;
import org.david.persistance.entity.BoardColumnKindEnum;
import org.david.persistance.entity.BoardEntity;
import org.david.persistance.entity.Board_ColumnEntity;
import org.david.persistance.entity.CardEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.david.persistance.entity.BoardColumnKindEnum.findByName;

@RequiredArgsConstructor
public class BoardColumnDAO {
    private final Connection connection;
    public Board_ColumnEntity insert(final Board_ColumnEntity entity) throws SQLException {
        var sql = "INSERT INTO BOARDS_COLUMN (name, 'order', kind, board_id) VALUES (?, ?, ?, ?);";
        try(var statement = connection.prepareStatement(sql)){
            var i = 1;
            statement.setString(i++, entity.getName());
            statement.setInt(i++, entity.getOrder());
            statement.setString(i++,entity.getKind().name());
            statement.setLong(i,entity.getBoard().getId());
            statement.executeUpdate();
            if (statement instanceof StatementImpl impl){
                entity.setId(impl.getLastInsertID());
            }
        }return entity;

    }

    public List<Board_ColumnEntity> findByBoardId(final long boardId) throws SQLException {
        List<Board_ColumnEntity> entities = new ArrayList<>();
        var sql = "SELECT id, name, 'order', kind FROM BOARD_COLUMN WHERE board_id = ? ORDER BY 'order' ";
        try (var statement = connection.prepareStatement(sql)){
            statement.setLong(1, boardId);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            while(resultSet.next()){
                var entity = new Board_ColumnEntity();
                entity.setId(resultSet.getLong("id"));
                entity.setName(resultSet.getString("name"));
                entity.setOrder(resultSet.getInt("order"));
                entity.setKind(findByName(resultSet.getString("kind")));
                entities.add(entity);
            }
            return entities;

        }
    }
    public List<BoardColumnDTO> findByBoardIdWithDetails(final long boardId) throws SQLException {
        List<BoardColumnDTO> dtos = new ArrayList<>();
        var sql =
                """
                SELECT bc.id, 
                       bc.name, 
                       bc.kind 
                       COUNT(SELECT c.id 
                             FROM CARDS 
                             WHERE c.Board_column_id = bc.id) cards_amount
                FROM BOARD_COLUMN bc
                WHERE board_id = ? 
                ORDER BY 'order' 
                """;
        try (var statement = connection.prepareStatement(sql)){
            statement.setLong(1, boardId);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            while(resultSet.next()){
                var dto = new BoardColumnDTO(resultSet.getLong("bc.id"),
                                             resultSet.getString("bc.name"),
                                             findByName(resultSet.getString("bc.kind")),
                                             resultSet.getInt("bc.cards_amount")
                );
                dtos.add(dto);
            }
            return dtos;

        }
    }
    public Optional<Board_ColumnEntity> findById(final long boardId) throws SQLException {
        List<Board_ColumnEntity> entities = new ArrayList<>();
        var sql = """
                SELECT bc.name, 
                       bc.kind,
                       c.id,
                       c.title,
                       c.description
                FROM BOARD_COLUMN 
                INNER JOIN CARDS c
                ON c.Board_column_id = bc.id
                WHERE bc.id = ? 
                 """;
        try (var statement = connection.prepareStatement(sql)){
            statement.setLong(1, boardId);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            if (resultSet.next()) {
                var entity = new Board_ColumnEntity();
                entity.setName(resultSet.getString("bc.name"));
                entity.setKind(findByName(resultSet.getString("bc.kind")));
                entities.add(entity);
                do {
                    var card = new CardEntity();
                    card.setId(resultSet.getLong("c.id"));
                    card.setTitle(resultSet.getString("c.title"));
                    card.setDescription(resultSet.getString("c.description"));
                    entity.getCards().add(card);
                } while (resultSet.next());

            }
            return Optional.empty();
        }
    }
}
