package me.theremixpvp.ckitpvp.kit.abilities;

import me.theremixpvp.ckitpvp.User;
import me.theremixpvp.ckitpvp.kit.Ability;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Florian Hergenhahn at 2020-03-18 <br>
 * Copyright © Flouet 2020
 *
 * @author Florian Hergenhahn
 */
public class NinjaAbility extends Ability {

    public NinjaAbility() {
        super("ninja", 25);
    }

    @Override
    protected boolean canActivate(Event event) {
        if (event instanceof PlayerDropItemEvent) {
            PlayerDropItemEvent e = (PlayerDropItemEvent) event;
            boolean flag = e.getItemDrop().getItemStack().getType() == Material.REDSTONE
                    || e.getItemDrop().getItemStack().getType() == Material.SUGAR;

            if (flag)
                return true;

        }

        if (!(event instanceof PlayerInteractEvent))
            return false;

        PlayerInteractEvent e = (PlayerInteractEvent) event;

        return e.getItem().getType() == Material.REDSTONE;
    }

    @Override
    protected void activate(Player player, User user, Event event) {
        if (event instanceof PlayerDropItemEvent) {
            ((PlayerDropItemEvent) event).setCancelled(true);
            return;
        }

        player.getInventory().setArmorContents(new ItemStack[]{
                new ItemStack(Material.AIR),
                new ItemStack(Material.AIR),
                new ItemStack(Material.AIR),
                new ItemStack(Material.DIAMOND_HELMET),
        });
        player.updateInventory();
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 400, 4));
    }

    @Override
    protected void onCoolDownEnd(Player player, Event event) {
        player.getInventory().setArmorContents(new ItemStack[]{
                new ItemStack(Material.CHAINMAIL_BOOTS),
                new ItemStack(Material.CHAINMAIL_LEGGINGS),
                new ItemStack(Material.CHAINMAIL_CHESTPLATE),
                new ItemStack(Material.DIAMOND_HELMET),
        });
        player.updateInventory();
    }
}
