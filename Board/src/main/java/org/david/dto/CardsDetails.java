package org.david.dto;

import java.time.OffsetDateTime;

public record CardsDetails(long id,
                           String title,
                           String description,
                           boolean blocked,
                           OffsetDateTime blockedAt,
                           String blockReason,
                           int blocksAmount,
                           long columnId,
                           String columnName) {

}
