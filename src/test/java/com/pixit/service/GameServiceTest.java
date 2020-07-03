package com.pixit.service;

import com.pixit.model.Card;
import com.pixit.model.Game;
import com.pixit.model.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GameServiceTest {

    private GameService gameService;

    @Test
    void newGame() {

        gameService = new GameService(new GameRepository(), new CardService(), new IdService(), new GameNameService());
        Player phil = gameService.createPlayer("phil");
        Game game = gameService.createGame(phil);

        Player julie = gameService.createPlayer("julie");
        Player paul = gameService.createPlayer("paul");
        gameService.addPlayerToGame(game.getId(), julie);
        gameService.addPlayerToGame(game.getId(), paul);

        game = gameService.nextRound(game.getId());
        assertThat(game.getCaller()).isEqualTo(phil);

        assertThat(phil.getCards().size()).isEqualTo(5);
        assertThat(julie.getCards().size()).isEqualTo(5);
        assertThat(paul.getCards().size()).isEqualTo(5);

        String callCardId = phil.getCards().get(0).getId();
        gameService.setCall(game.getId(), callCardId, "battle", phil.getId());
        String call = game.getCall();
        assertThat(call).isEqualTo("battle");
        Card juliesCard = julie.getCards().get(0);
        gameService.setResponse(game.getId(), juliesCard.getId(), julie.getId());
        gameService.setResponse(game.getId(), paul.getCards().get(0).getId(), paul.getId());
        List<Card> options = gameService.getOptions(game.getId());
        assertThat(options.size()).isEqualTo(3);
        gameService.setGuess(game.getId(), callCardId, julie.getId());
        gameService.setGuess(game.getId(), juliesCard.getId(), paul.getId());

        game = gameService.nextRound(game.getId());

        assertThat(game.getScore(julie)).isEqualTo(4);
        assertThat(game.getScore(phil)).isEqualTo(3);
        assertThat(game.getScore(paul)).isEqualTo(0);

        assertThat(game.getCaller()).isEqualTo(julie);

    }

}