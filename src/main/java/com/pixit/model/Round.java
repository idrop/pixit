package com.pixit.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static com.google.common.collect.Streams.concat;

public class Round {

    private final Player caller;
    Map<Player, Card> responses;
    Map<Player, Card> guesses;
    RoundScore roundScore;
    private Card card;
    private String call;

    public Round(Player caller) {
        this.caller = caller;
        roundScore = new RoundScore();
        responses = new HashMap<>();
        guesses = new HashMap<>();
    }

    public Player getCaller() {
        return caller;
    }

    public void setCall(Card card, String call) {
        this.card = card;
        this.call = call;
    }

    public void setResponse(Card card, Player responder) {
        responses.put(responder, card);
    }

    public List<Card> getOptions() {
        List<Card> cards = new ArrayList<>();
        cards.add(card);
        cards.addAll(responses.values());
        Collections.shuffle(cards);
        return Collections.unmodifiableList(cards);
    }

    public String getCall() {
        return call;
    }

    public void setGuess(Card card, Player guesser) {
        guesses.put(guesser, card);
    }

    public void endRound() {
        calculateScore();
    }

    private void calculateScore() {
        var correctGuessers = new ArrayList<Player>();
        for (var guess : guesses.entrySet()) {
            Player guesser = guess.getKey();
            Card guessCard = guess.getValue();
            if (guessCard.equals(card)) {
                correctGuessers.add(guesser);
            } else {
                for (var responses : responses.entrySet()) {
                    if (guessCard.equals(responses.getValue())) {
                        roundScore.add(responses.getKey(), 1);
                    }
                }
            }
        }
        if (!correctGuessers.isEmpty()) {
            roundScore.add(caller, 3);
            for (Player correct : correctGuessers) {
                roundScore.add(correct, 3);
            }
        }
    }

    public RoundScore getRoundScore() {
        return roundScore;
    }

    public Card getCard(String cardId) {
        List<Card> cards = new ArrayList<>();
        cards.add(card);
        cards.addAll(responses.values());


        return cards.stream().filter(c -> cardId.equals(c.getId())).findFirst().orElseThrow(() -> new PixitException("could not find card " + cardId));
    }
}
