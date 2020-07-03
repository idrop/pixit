package com.pixit.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RoundScore {

    Map<Player, Integer> scores = new HashMap<>();

    public void add(Player player, int i) {
        Integer score = scores.getOrDefault(player, 0);
        scores.put(player, i + score);
    }

    public Map<Player, Integer> getScores() {
        return Collections.unmodifiableMap(scores);
    }
}
