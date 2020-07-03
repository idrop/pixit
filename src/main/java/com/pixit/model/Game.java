package com.pixit.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {

    private final String id;
    private final String name;
    private final Player creator;
    private final Queue<Card> cards;
    Stack<Round> rounds = new Stack<>();
    Queue<Player> players = new ArrayDeque<>();

    public Game(String id, String name, Player creator, Set<Card> cards) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        List<Card> toShuffle = new ArrayList<>(cards);
        Collections.shuffle(toShuffle);
        this.cards = new ArrayDeque<>(toShuffle);
        addPlayer(creator);
    }

    public String getId() {
        return id;
    }

    public Set<Player> getPlayers() {
        return players.stream().collect(Collectors.toSet());
    }

    public synchronized void addPlayer(Player player) {
        if (players.contains(player)) {
            throw new PixitException("player already added");
        }
        this.players.add(player);
    }

    public synchronized void nextRound() {

        if (rounds.size() > 0) {
            getCurrentRound().endRound();
            setNextCaller();
        }
        setCards();
        rounds.add(new Round(players.peek()));
    }

    private void setCards() {
        IntStream.range(0, 5).forEach(i -> {
            for (Player player : players) {
                if (player.getCards().size() < 5) {
                    Card top = cards.poll();
                    player.addCard(top);
                }
            }

        });
    }

    public Round getCurrentRound() {
        return rounds.peek();
    }

    private void setNextCaller() {
        players.add(players.remove());
    }

    public synchronized Player getCaller() {
        return getCurrentRound().getCaller();
    }

    public synchronized void setCall(Card card, String call, Player caller) {
        if (!caller.equals(getCaller())) {
            // todo PixitExceptionMapper
            throw new PixitException("not the caller");
        }
        if (!caller.hasCard(card)) {
            throw new PixitException("caller doesn't have this card");
        }
        caller.removeCard(card);
        cards.add(card);
        getCurrentRound().setCall(card, call);

    }

    public void setResponse(Card card, Player responder) {
        if (responder.equals(getCaller())) {
            // todo PixitExceptionMapper
            throw new PixitException("caller is responding");
        }
        if (!responder.hasCard(card)) {
            throw new PixitException("caller doesn't have this card");
        }

        responder.removeCard(card);
        cards.add(card);
        getCurrentRound().setResponse(card, responder);
    }

    public List<Card> getOptions() {
        return getCurrentRound().getOptions();
    }

    public String getCall() {
        return getCurrentRound().getCall();
    }

    public void setGuess(Card card, Player guesser) {
        if (getCaller().equals(guesser)) {
            throw new PixitException("caller can't guess");
        }
        getCurrentRound().setGuess(card, guesser);

    }

    public Integer getScore(Player player) {
        int total = 0;
        for (Round round : rounds) {
            total += round.getRoundScore().getScores().getOrDefault(player, 0);
        }
        return total;
    }
}
