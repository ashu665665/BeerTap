package com.backend.dispenser.beertap.util;

import com.fasterxml.uuid.Generators;

public class UUIDGenerator {
    public static String generateUUID() {
        return Generators.timeBasedGenerator().generate().toString();
    }
}
