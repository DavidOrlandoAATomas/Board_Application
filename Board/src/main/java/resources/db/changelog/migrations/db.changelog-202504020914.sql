--liquibase formatted sql
--changeset junior: 202504010942
--comment: boards_column table crate

CREATE TABLE BOARDS_COLUMN(
    id BIGINT AUTO_INCREMENT primary key,
    name varchar(255) NOT NULL,
    'order' int NOT NULL,
    kind char(7) NOT NULL,
    Board_id BIGINT NOT NULL,
    CONSTRAINT boards_boards_column_fk FOREIGN KEY(Board_id) REFERENCES BOARDS(id) ON DELETE CASCADE,
    CONSTRAINT id_order_uk UNIQUE KEY unique_board_id_order (board, 'order')

)engine=InnDb;


--rollback DROP TABLE BOARDS