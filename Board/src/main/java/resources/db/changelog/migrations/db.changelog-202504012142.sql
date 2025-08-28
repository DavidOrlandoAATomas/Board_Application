--liquibase formatted sql
--changeset junior: 202504010942
--comment: boards table crate

CREATE TABLE BOARDS(
    id BIGINT AUTO_INCREMENT primary key,
    name varchar(255) NOT NULL

)engine=InnDb;


--rollback DROP TABLE BOARDS