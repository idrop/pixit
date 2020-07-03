package com.pixit.service;

import com.pixit.model.Card;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CardService {
    public Set<Card> getCards() {

        return IntStream.rangeClosed(1, 30)
                .mapToObj(i -> new Card(String.valueOf(i)))
                .collect(Collectors.toSet());
    }
}
