package org.david.dto;

import org.david.persistance.entity.BoardColumnKindEnum;

public record BoardColumnDTO(long id,
                             String nome,
                             BoardColumnKindEnum kind,
                             int cardsAmount) {
}
