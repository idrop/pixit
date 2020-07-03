package com.pixit.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class Player {
    private final String id;
    private final String name;
    private List<Card> cards;

    public Player(String id, String name) {
        this.id = id;
        this.name = name;
        this.cards = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    public boolean hasCard(Card card) {
        return cards.contains(card);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Player.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("name='" + name + "'")
                .toString();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }
}
