package me.theremixpvp.ckitpvp.listeners;

import me.theremixpvp.ckitpvp.kit.AbilityManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Florian Hergenhahn at 2020-03-20 <br>
 * Copyright © Flouet 2020
 *
 * @author Florian Hergenhahn
 */
public class AbilityListener implements Listener {

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        AbilityManager.triggerAdditionalAbilityEvents(event);
        AbilityManager.triggerAbilityEvents(event.getPlayer(), event);
    }

    @EventHandler
    private void onPlayerEggThrow(PlayerEggThrowEvent event) {
        AbilityManager.triggerAdditionalAbilityEvents(event);
        AbilityManager.triggerAbilityEvents(event.getPlayer(), event);
    }

    @EventHandler
    private void onPlayerFishEvent(PlayerFishEvent event) {
        AbilityManager.triggerAdditionalAbilityEvents(event);
        AbilityManager.triggerAbilityEvents(event.getPlayer(), event);
    }

    @EventHandler
    private void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        AbilityManager.triggerAdditionalAbilityEvents(event);

        if (event.getEntity() instanceof Player)
            AbilityManager.triggerAbilityEvents((Player) event.getEntity(), event);
    }

    @EventHandler
    private void onEntityDeathEvent(EntityDeathEvent event) {
        AbilityManager.triggerAdditionalAbilityEvents(event);

        if (event.getEntity() instanceof Player)
            AbilityManager.triggerAbilityEvents((Player) event.getEntity(), event);
    }

    @EventHandler
    private void onEntityShootBowEvent(EntityShootBowEvent event) {
        AbilityManager.triggerAdditionalAbilityEvents(event);

        if (event.getEntity() instanceof Player)
            AbilityManager.triggerAbilityEvents((Player) event.getEntity(), event);
    }
}
