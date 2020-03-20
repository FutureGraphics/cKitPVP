package me.theremixpvp.ckitpvp.kit.abilities;

import me.theremixpvp.ckitpvp.User;
import me.theremixpvp.ckitpvp.kit.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Florian Hergenhahn at 2020-03-20 <br>
 * Copyright © Flouet 2020
 *
 * @author Florian Hergenhahn
 */
public class RusherAbility extends Ability {

    public RusherAbility() {
        super("rusher", 30);
    }

    @Override
    protected boolean canActivate(Event event) {
        if(!(event instanceof PlayerInteractEvent))
            return false;

        PlayerInteractEvent e = (PlayerInteractEvent) event;

        return e.getItem().getType() == Material.FEATHER;
    }

    @Override
    protected void activate(Player player, User user, Event event) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 160, 2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 160, 1));
    }

    @Override
    protected void onCoolDownEnd(Player player, Event event) {
        player.sendMessage(ChatColor.DARK_AQUA + "Your Rush ability is recharged!");
    }
}
