package com.pixit.view;

import io.dropwizard.views.View;

public class HomeView extends View {
    public HomeView() {
        super("home.mustache");
    }
}
