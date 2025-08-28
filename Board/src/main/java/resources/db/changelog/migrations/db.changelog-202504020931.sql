--liquibase formatted sql
--changeset junior: 202504010942
--comment: blocks table crate

CREATE TABLE BLOCKS(
    id BIGINT AUTO_INCREMENT primary key,
    blocked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    block_reason varchar(255) NOT NULL,
    unblocked_at TIMESTAMP NULL,
    unblock_at varchar(255) NOT NULL,
    card_id BIGINT NOT NULL,
    CONSTRAINT cards_blocks_fk FOREIGN KEY(card_id) REFERENCES CARDS(id) ON DELETE CASCADE

)engine=InnDb;


--rollback DROP TABLE BOARDS