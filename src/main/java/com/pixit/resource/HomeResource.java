package com.pixit.resource;

import com.pixit.view.GameView;
import com.pixit.view.HomeView;
import io.dropwizard.views.View;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class HomeResource {

    private static final Logger LOG = Logger.getLogger(HomeResource.class.getName());

    @GET
    public View home(@CookieParam("_gid") Optional<Cookie> cookie) {

        if (cookie.isPresent()) {
            return new GameView(cookie.get().getValue());
        } else {
            return new HomeView();

        }
    }

    @POST
    public Response newGame() {
        UUID uuid = UUID.randomUUID();
        LOG.info("created new game with ");
        return Response
                .ok(new GameView(uuid.toString()))
                .cookie(new NewCookie("_gid", uuid.toString()))
                .build();
    }

}
