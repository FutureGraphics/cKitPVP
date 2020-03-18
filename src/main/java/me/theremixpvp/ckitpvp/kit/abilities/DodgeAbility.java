package me.theremixpvp.ckitpvp.kit.abilities;

import me.theremixpvp.ckitpvp.kit.Ability;
import me.theremixpvp.ckitpvp.kit.Kit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by Florian Hergenhahn at 2020-03-18 <br>
 * Copyright © Flouet 2020
 *
 * @author Florian Hergenhahn
 */
public class DodgeAbility extends Ability {

    public DodgeAbility() {
        super("dodge", 0);
    }

    @Override
    protected boolean canActivate(Event event) {
        if (!(event instanceof EntityDamageByEntityEvent))
            return false;

        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        return e.getCause() == EntityDamageByEntityEvent.DamageCause.PROJECTILE
                && e.getEntity() instanceof Player;
    }

    @Override
    protected void activate(Player player, Event event) {
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        e.setCancelled(true);

        Arrow arrow = player.launchProjectile(Arrow.class);
        arrow.setShooter(player);
        arrow.setVelocity(e.getDamager().getVelocity().multiply(-1));
    }
}
