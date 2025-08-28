package org.david.persistance.dao;

import com.mysql.cj.jdbc.StatementImpl;
import lombok.AllArgsConstructor;
import org.david.dto.CardsDetails;
import org.david.persistance.entity.CardEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.david.persistance.converter.OffSetTimeConverter.toOffsetDateTime;

@AllArgsConstructor
public class CardDAO {
    private Connection connection;

    public CardEntity insert(final CardEntity entity) throws SQLException {
        var sql = "INSERT INTO CARDS (title, description, board_column_id) values (?, ?, ?);";
        try(var statement = connection.prepareStatement(sql)){
            var i = 1;
            statement.setString(i ++, entity.getTitle());
            statement.setString(i ++, entity.getDescription());
            statement.setLong(i, entity.getBoardColumn().getId());
            statement.executeUpdate();
            if (statement instanceof StatementImpl impl){
                entity.setId(impl.getLastInsertID());
            }
        }
        return entity;
    }

    public void moveToColumn(final Long columnId, final Long cardId) throws SQLException{
        var sql = "UPDATE CARDS SET board_column_id = ? WHERE id = ?;";
        try(var statement = connection.prepareStatement(sql)){
            var i = 1;
            statement.setLong(i ++, columnId);
            statement.setLong(i, cardId);
            statement.executeUpdate();
        }
    }

    public Optional<CardsDetails> findById(long id){
        var sql =
                """
                SELECT c.id, 
                c.title, 
                c.description
                b.blocked_at
                b.block_reason
                b.Board_column_id
                bc.name,
                COUNT(SELECT sub_b.id 
                      FROM BLOCKS 
                      WHERE sub_b.card_id = c.id) blocks_amount
                FROM CARDS
                LEFT JOIN BLOCKS b
                ON c.id = b.card_id
                AND b.unblocked_at IS NULL
                INNER JOIN BOARDS_COLUMNS bc
                ON bc.id = c.board_column_id
                WHERE id = ?
                        """;
        try(var statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            if(resultSet.next()){
                var dto = new CardsDetails(
                        resultSet.getLong("c.id"),
                        resultSet.getString("c.title"),
                        resultSet.getString("c.descripton"),
                        resultSet.getString("b.block_reason").isEmpty(),
                        toOffsetDateTime(resultSet.getTimestamp("b.blocked_at")),
                        resultSet.getString("b.block_reason"),
                        resultSet.getInt("blocks_amount"),
                        resultSet.getLong("c.board_column_id"),
                        resultSet.getString("bc.name")
                );
                return Optional.of(dto);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
