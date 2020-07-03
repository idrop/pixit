package com.pixit.service;

import com.pixit.model.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class GameRepository {
    private Map<String, Game> games = new HashMap<>();

    public void save(Game game) {
        games.put(game.getId(), game);
    }

    public Optional<Game> getGame(String gameId) {
        return Optional.ofNullable(games.get(gameId));
    }
}
