package com.project.comento.domain.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UuidGenerator {
    public static long generate() {
        UUID uuid = UUID.randomUUID();
        final long uuidBits = Math.abs(uuid.getLeastSignificantBits());
        return uuidBits;
    }
}
