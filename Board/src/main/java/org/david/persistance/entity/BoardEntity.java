package org.david.persistance.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
public class BoardEntity {
    private String name;
    private long id;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Board_ColumnEntity> boardColumns = new ArrayList<>();
}
