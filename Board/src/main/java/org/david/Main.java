package org.david;

import org.david.persistance.migration.MigrationStrategy;

import java.sql.SQLException;

import static org.david.persistance.config.ConnectorConfig.getConnection;


public class Main {
    public static void main(String[] args) throws SQLException {
        try(var connection = getConnection()){
            new MigrationStrategy(connection).executeMigration();
        }

    }
}