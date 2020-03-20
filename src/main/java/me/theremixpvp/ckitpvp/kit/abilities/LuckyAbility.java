package me.theremixpvp.ckitpvp.kit.abilities;

import me.theremixpvp.ckitpvp.User;
import me.theremixpvp.ckitpvp.kit.Ability;
import org.bukkit.ChatColor;
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
        addAdditionalEventListener(EntityDamageByEntityEvent.class, this::onEntityDamage);
        random = new Random();
    }

    private void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player))
            return;

        Player player = (Player) event.getDamager();
        User user = User.byPlayer(player);

        if (user.getKit() == null || user.getKit().getAbility() == null
                || !(user.getKit().getAbility() instanceof LuckyAbility))
            return;


        int yn = random.nextInt(100);
        if (yn <= 20) {
            Random rand2 = new Random();
            int ri = rand2.nextInt(10);

            event.setDamage(event.getDamage() + ri);
            player.sendMessage(ChatColor.DARK_AQUA + "You " + ri + "x hit " + event.getEntity().getType() + "!");
        }

    }

    @Override
    protected boolean canActivate(Event event) {
        if (!(event instanceof EntityDamageByEntityEvent))
            return false;

        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;

        return e.getEntity() instanceof Player && e.getDamager() instanceof Player;
    }

    @Override
    protected void activate(Player player, User user, Event event) {
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;

        int yn = random.nextInt(100);
        if (yn <= 20) {
            Player damager = (Player) e.getDamager();
            Random rand2 = new Random();
            int ri = rand2.nextInt(10);
            e.setDamage(e.getDamage() + ri / 2f);

            damager.sendMessage(ChatColor.DARK_AQUA + "You were " + ri + "x hit by " + player.getName());
            player.sendMessage(ChatColor.DARK_AQUA + "You " + ri + "x hit " + player.getName() + "!");
        }
    }
}
