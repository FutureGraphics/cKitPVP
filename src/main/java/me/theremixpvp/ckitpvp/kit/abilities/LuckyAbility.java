package me.theremixpvp.ckitpvp.kit.abilities;

import me.theremixpvp.ckitpvp.kit.Ability;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Random;

/**
 * Created by Florian Hergenhahn at 2020-03-18 <br>
 * Copyright © Flouet 2020
 *
 * @author Florian Hergenhahn
 */
public class LuckyAbility extends Ability {

    private final Random random;

    public LuckyAbility() {
        super("lucky", 0);
        random = new Random();
    }

    @Override
    protected boolean canActivate(Event event) {
        return event instanceof EntityDamageByEntityEvent;
    }

    @Override
    protected void activate(Player player, Event event) {
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;

        int takeDamage = random.nextInt(100);
        if (takeDamage <= 20) {
            int damage = random.nextInt(10) + 1;
            double originalDamage = e.getDamage();
            double absorbedDamage = originalDamage / (damage / 2f);
            e.setDamage(absorbedDamage);
            player.sendMessage("§a" + (originalDamage - absorbedDamage) + " hits got absorbed");
        }
    }
}
