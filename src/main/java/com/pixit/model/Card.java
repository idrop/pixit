package com.pixit.model;

import java.util.Objects;
import java.util.StringJoiner;

public class Card {

    private final String id;

    public Card(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Card.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .toString();
    }

}
