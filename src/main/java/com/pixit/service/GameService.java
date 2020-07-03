package com.pixit.service;

import com.pixit.model.Card;
import com.pixit.model.Game;
import com.pixit.model.PixitException;
import com.pixit.model.Player;

import java.util.List;
import java.util.Set;

public class GameService {

    private final GameRepository gameRepository;
    private final CardService cardService;
    private final IdService idService;
    private final GameNameService gameNameService;

    public GameService(GameRepository gameRepository, CardService cardService, IdService idService, GameNameService gameNameService) {
        this.gameRepository = gameRepository;
        this.cardService = cardService;
        this.idService = idService;
        this.gameNameService = gameNameService;
    }

    public Game createGame(Player creator) {
        Set<Card> cards = cardService.getCards();
        Game game = new Game(idService.getID(), gameNameService.getName(), creator, cards);
        gameRepository.save(game);
        return game;
    }

    public Player createPlayer(String name) {
        Player player = new Player(idService.getID(), name);
        return player;
    }

    public void addPlayerToGame(String gameId, Player player) {
        Game game = getGame(gameId);
        game.addPlayer(player);
        gameRepository.save(game);
    }

    private Game getGame(String gameId) {
        return gameRepository.getGame(gameId).orElseThrow(() -> new PixitException("no game with id " + gameId));
    }

    public Game nextRound(String gameId) {
        Game game = getGame(gameId);
        game.nextRound();
        gameRepository.save(game);
        return game;

    }


    public void setCall(String gameId, String cardId, String call, String playerId) {
        Game game = getGame(gameId);
        Player caller = getPlayer(playerId, game);
        Card card = getCard(cardId, caller);
        game.setCall(card, call, caller);
        gameRepository.save(game);

    }

    private Card getCard(String cardId, Player player) {
        return player.getCards().stream().filter(c -> cardId.equals(c.getId())).findFirst().orElseThrow(() -> new PixitException("could not find card " + cardId));

    }

    private Player getPlayer(String playerId, Game game) {
        Player player = game.getPlayers().stream()
                .filter(p -> playerId.equals(p.getId()))
                .findFirst()
                .orElseThrow(() -> new PixitException("could not find player " + playerId));
        return player;
    }

    public void setResponse(String gameId, String cardId, String playerId) {
        Game game = getGame(gameId);
        Player responder = getPlayer(playerId, game);
        Card card = getCard(cardId, responder);
        game.setResponse(card, responder);
    }

    public List<Card> getOptions(String gameId) {
        Game game = getGame(gameId);
        return game.getOptions();
    }

    public void setGuess(String gameId, String cardId, String playerId) {
        Game game = getGame(gameId);
        Player guesser = getPlayer(playerId, game);
        Card card = game.getCurrentRound().getCard(cardId);
        game.setGuess(card, guesser);
    }
}
