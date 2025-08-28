package org.david.UI;

import org.david.persistance.entity.BoardColumnKindEnum;
import org.david.persistance.entity.BoardEntity;
import org.david.persistance.entity.Board_ColumnEntity;
import org.david.service.BoardQueryService;
import org.david.service.BoardService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.david.persistance.config.ConnectorConfig.getConnection;
import static org.david.persistance.entity.BoardColumnKindEnum.*;

public class MainMenu {
    private final Scanner scanner = new Scanner(System.in);

    public void execute() throws SQLException {
        System.out.println("Bem-vindo ao Gestor de Boards, escolha ao seu desejo");
        var option = -1;
        while(true){
            System.out.println(" 1 - crie um novo board");
            System.out.println(" 2 - Selecione um board existente");
            System.out.println(" 3 - Excluir um board");
            System.out.println(" Sair");
            option=scanner.nextInt();
            switch(option){
                case 1-> createBoard();
                case 2-> selectBoard();
                case 3-> deleteBoard();
                case 4-> System.exit(0);
                default -> System.out.println("Opcao invalida, selecione uma opcao do menu");
            }
        }
    }

    private void deleteBoard() throws SQLException {
        System.out.println("Digite o id do board: ");
        var id = scanner.nextLong();
        try (var connection= getConnection() ){
            var service = new BoardService(connection);
            if(service.delete(id)){
                System.out.printf("O board %s foi removido\n",id);

            } else {
                System.out.printf("Nao foi encontrado um board com id %s\n", id);

            }

        }
    }

    private void selectBoard() throws SQLException {
        System.out.println("Informe o id do seu board: ");
        var id = scanner.nextLong();
        try (var connection = getConnection()){
            var queryService = new BoardQueryService(connection);
            var optional = queryService.findById(id);
            optional.ifPresentOrElse(
                    b -> new BoardMenu(b).execute(),
                    () -> System.out.printf("Nao foi encontrado o board com id %s\n", id));
            if (optional.isPresent()){
                var boardMenu = new BoardMenu(optional.get());
                boardMenu.execute();

            } else {
                System.out.printf("Nao foi encontrado o board com id %s\n", id);
            }
        }
    }

    private void createBoard() throws SQLException {
        var entity = new BoardEntity();
        System.out.println("Informe o nome do seu board: ");
        entity.setName(scanner.next());

        System.out.println("O board tera colunas alem das 3 padroes? Se sim, informe quantas, se nao digite 0");
        var addtionalColumns = scanner.nextInt();

        List<Board_ColumnEntity> columns = new ArrayList<>();

        System.out.println("Informe o nome da coluna inicial do board");
        var initialColumnName = scanner.next();
        var initialColumn = createColumn(initialColumnName, INITIAL,0);
        columns.add(initialColumn);

        for (var i = 0; i < addtionalColumns; i++){
            System.out.println("Informe o nome da coluna de tarefa pendente");
            var pendingColumnName = scanner.next();
            var pendingColumn = createColumn(pendingColumnName, PENDING,i+1);
            columns.add(initialColumn);
        }
        System.out.println("Informe o nome da coluna final");
        var finalColumnName = scanner.next();
        var finalColumn = createColumn(finalColumnName, FINAL,addtionalColumns+1);
        columns.add(initialColumn);

        System.out.println("Informe o nome da coluna de cancelamento do board");
        var cancelColumnName = scanner.nextLine();
        var cancelColumn = createColumn(cancelColumnName, CANCEL,addtionalColumns+2);
        columns.add(initialColumn);

        entity.setBoardColumns(columns);
        try (var connection = getConnection()){
            var service = new BoardService(connection);
            service.insert(entity);
        }

    }

    private Board_ColumnEntity createColumn (final String name, final BoardColumnKindEnum kind, final int order){
        var boardcolumn = new Board_ColumnEntity();
        boardcolumn.setName(name);
        boardcolumn.setKind(kind);
        boardcolumn.setOrder(order);
        return boardcolumn;
    }
}
