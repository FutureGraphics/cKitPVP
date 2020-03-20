package me.theremixpvp.ckitpvp.kit.abilities;

import me.theremixpvp.ckitpvp.KitPvP;
import me.theremixpvp.ckitpvp.User;
import me.theremixpvp.ckitpvp.kit.Ability;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by Florian Hergenhahn at 2020-03-18 <br>
 * Copyright © Flouet 2020
 *
 * @author Florian Hergenhahn
 */
public class BomberAbility extends Ability {

    private static final int EXPLOSION_DELAY = 3;
    private final ItemStack bomb;

    public BomberAbility() {
        super("bomber", 3);
        bomb = new ItemStack(Material.EGG);
        ItemMeta meta = bomb.getItemMeta();
        meta.setDisplayName("§aBomb");
        bomb.setItemMeta(meta);
    }

    @Override
    protected boolean canActivate(Event event) {
        return event instanceof PlayerEggThrowEvent;
    }


    @Override
    protected void activate(Player player, User user, Event event) {
        PlayerEggThrowEvent e = (PlayerEggThrowEvent) event;

        World location = player.getLocation().getWorld();
        Bukkit.getScheduler().scheduleSyncDelayedTask(KitPvP.instance, () -> {
            location.createExplosion(e.getEgg().getLocation(), 3F, false);
            location.createExplosion(e.getEgg().getLocation(), 2F, false);
            location.createExplosion(e.getEgg().getLocation(), 1F, false);
        }, 20 * EXPLOSION_DELAY);
    }

    @Override
    protected void onCoolDownEnd(Player player, Event event) {
        player.getInventory().addItem(bomb.clone());
    }

    @Override
    protected void onNotifyCoolDown(Player player, Event event) {
        super.onNotifyCoolDown(player, event);

        player.getInventory().addItem(bomb.clone());
    }
}
