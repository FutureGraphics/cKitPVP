package me.theremixpvp.ckitpvp.kit.abilities;

import me.theremixpvp.ckitpvp.User;
import me.theremixpvp.ckitpvp.kit.Ability;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.util.Vector;

/**
 * Created by Florian Hergenhahn at 2020-03-18 <br>
 * Copyright © Flouet 2020
 *
 * @author Florian Hergenhahn
 */
public class GrapplerAbility extends Ability {

    public GrapplerAbility() {
        super("grappler", 0);
    }

    @Override
    protected boolean canActivate(Event event) {
        return event instanceof PlayerFishEvent;
    }

    @Override
    protected void activate(Player player, User user, Event event) {
        PlayerFishEvent e = (PlayerFishEvent) event;
        if(e.getState() != PlayerFishEvent.State.IN_GROUND)
            return;

        Location fm = player.getLocation();
        Location to = e.getHook().getLocation();

        player.teleport(fm);

        double g = -0.08;
        double t = to.distance(fm);
        double v_x = (1.0 + 0.07 * t) * (to.getX() - fm.getX()) / t;
        double v_y = (1.0 + 0.03 * t) * (to.getY() - fm.getY()) / t - 0.5 * g * t;
        double v_z = (1.0 + 0.07 * t) * (to.getZ() - fm.getZ()) / t;

        // Set player's velocity to get to destination
        Vector v = player.getVelocity();
        v.setX(v_x);
        v.setY(v_y);
        v.setZ(v_z);
        player.setVelocity(v);
    }
}
