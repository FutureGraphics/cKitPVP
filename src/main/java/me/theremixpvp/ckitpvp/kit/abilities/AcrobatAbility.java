package me.theremixpvp.ckitpvp.kit.abilities;

import me.theremixpvp.ckitpvp.User;
import me.theremixpvp.ckitpvp.kit.Ability;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

/**
 * Created by Florian Hergenhahn at 2020-03-18 <br>
 * Copyright © Flouet 2020
 *
 * @author Florian Hergenhahn
 */
public class AcrobatAbility extends Ability {

    public AcrobatAbility() {
        super("acrobat", 40);
    }


    @Override
    protected boolean canActivate(Event event) {
        if(!(event instanceof PlayerInteractEvent))
            return false;

        PlayerInteractEvent e = (PlayerInteractEvent) event;

        return e.getItem().getType() == Material.STRING;
    }

    @Override
    protected void activate(Player player, User user, Event event) {
        Vector v = player.getVelocity();
        v.add(player.getLocation().getDirection().multiply(2))
                .setY(1.35677);
        player.setVelocity(v);
    }
}
