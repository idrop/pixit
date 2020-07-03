package com.pixit.view;

import io.dropwizard.views.View;

public class GameView extends View {


    private final String gid;

    public GameView(String gid) {
        super("game.mustache");
        this.gid = gid;
    }

    public String getGid() {
        return gid;
    }
}
