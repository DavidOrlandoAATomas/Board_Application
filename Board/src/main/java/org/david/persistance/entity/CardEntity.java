package org.david.persistance.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CardEntity {
    private long id;
    private String title;
    private String description;
    private Board_ColumnEntity boardColumn = new Board_ColumnEntity();

}
