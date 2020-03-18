package me.theremixpvp.ckitpvp.kit.abilities;

import me.theremixpvp.ckitpvp.kit.Ability;
import me.theremixpvp.ckitpvp.kit.Kit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerFishEvent;

/**
 * Created by Florian Hergenhahn at 2020-03-18 <br>
 * Copyright © Flouet 2020
 *
 * @author Florian Hergenhahn
 */
public class FishermanAbility extends Ability {

    public FishermanAbility() {
        super("fisherman", 0);
    }

    @Override
    protected boolean canActivate(Event event) {
        return event instanceof PlayerFishEvent;
    }

    @Override
    protected void activate(Player player, Event event) {
        PlayerFishEvent e = (PlayerFishEvent) event;
        if(e.getCaught() instanceof Player) {
            e.getCaught().teleport(player.getLocation());
        }

    }
}
