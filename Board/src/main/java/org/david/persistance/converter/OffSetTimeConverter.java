package org.david.persistance.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

import static java.time.ZoneOffset.UTC;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class OffSetTimeConverter {

    public static OffsetDateTime toOffsetDateTime(final Timestamp value){
        return OffsetDateTime.ofInstant(value.toInstant(), UTC);
    }
}
