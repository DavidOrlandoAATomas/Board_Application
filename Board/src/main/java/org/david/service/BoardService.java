package org.david.service;

import lombok.AllArgsConstructor;
import org.david.persistance.dao.BoardColumnDAO;
import org.david.persistance.dao.BoardDAO;
import org.david.persistance.entity.BoardEntity;

import java.sql.Connection;
import java.sql.SQLException;

@AllArgsConstructor
public class BoardService {

    private final Connection connection;

    public BoardEntity insert(final BoardEntity entity) throws SQLException{
        var dao = new BoardDAO(connection);
        var boardcolumnDAO = new BoardColumnDAO(connection);

        try {
            dao.insert(entity);
            var columns = entity.getBoardColumns().stream().map(c ->{
                c.setBoard(entity);
                return c;
            }).toList();
            for(var column : columns){
                boardcolumnDAO.insert(column);
            }
            connection.commit();

        }catch (SQLException e){
            connection.rollback();
            throw e;
        }return entity;

    }

    public boolean delete(final long id) throws SQLException {

        var dao = new BoardDAO(connection);
        try {
            if (!dao.exists(id)){
                return false;
            }
            dao.delete(id);
            connection.commit();
            return true;
        }catch(SQLException e){
            connection.rollback();
            throw e;
        }

    }
}
