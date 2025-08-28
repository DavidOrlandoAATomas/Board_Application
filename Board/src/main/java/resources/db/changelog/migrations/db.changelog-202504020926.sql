--liquibase formatted sql
--changeset junior: 202504010942
--comment: cards table crate

CREATE TABLE CARDS(
    id BIGINT AUTO_INCREMENT primary key,
    title varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    Board_column_id BIGINT NOT NULL,
    CONSTRAINT boards_columns_cards_fk FOREIGN KEY(Board_column_id) REFERENCES BOARDS_COLUMN(id) ON DELETE CASCADE

)engine=InnDb;


--rollback DROP TABLE BOARDS