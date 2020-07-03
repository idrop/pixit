package com.pixit.service;

import java.util.UUID;

public class IdService {
    public String getID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
