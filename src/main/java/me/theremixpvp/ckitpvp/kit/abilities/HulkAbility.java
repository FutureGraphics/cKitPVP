package me.theremixpvp.ckitpvp.kit.abilities;

import me.theremixpvp.ckitpvp.kit.Ability;
import me.theremixpvp.ckitpvp.kit.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Florian Hergenhahn at 2020-03-18 <br>
 * Copyright © Flouet 2020
 *
 * @author Florian Hergenhahn
 */
public class HulkAbility extends Ability {

    public HulkAbility() {
        super("hulk", 30);
    }

    @Override
    protected boolean canActivate(Event event) {
        if(!(event instanceof PlayerInteractEvent))
            return false;

        PlayerInteractEvent e = (PlayerInteractEvent) event;

        return e.getItem().getType() == Material.FIREWORK_CHARGE;
    }

    @Override
    protected void activate(Player player, Event event) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 300, 1));
        player.sendMessage(ChatColor.GREEN + "You have activated the power of hulk!");
    }

    @Override
    protected void onCoolDownEnd(Player player, Event event) {
        player.sendMessage("§dYour hulk ability is recharged!");
    }
}
