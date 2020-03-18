package me.theremixpvp.ckitpvp.kit;

import me.theremixpvp.ckitpvp.KitPvP;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.*;

/**
 * Created by Florian Hergenhahn at 2020-03-18 <br>
 * Copyright © Flouet 2020
 *
 * @author Florian Hergenhahn
 */
public abstract class Ability {

    private final static Map<Player, Long> coolDownMap = new HashMap<>();

    protected final List<Kit> kits = new ArrayList<>();
    protected final String name;
    protected final int coolDown;

    public Ability(String name, int coolDown) {
        this.name = name;
        this.coolDown = coolDown;
    }

    public boolean isApplicable(Kit kit, Event event) {
        return kits.contains(kit) && canActivate(event);
    }

    protected abstract boolean canActivate(Event event);

    public void addKit(Kit kit) {
        kits.add(kit);
    }

    public void activateAbility(Player player, Event event) {
        if (coolDown > 0  && coolDownMap.containsKey(player) && coolDownMap.get(player) > System.currentTimeMillis()) {
            onNotifyCoolDown(player, event);
            return;
        }

        activate(player, event);

        if(coolDown > 0) {
            coolDownMap.put(player, System.currentTimeMillis() + coolDown * 1000);
            startCoolDownScheduler(player, event);
        }

    }

    protected void startCoolDownScheduler(final Player player, final Event event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(KitPvP.instance, () -> {
            coolDownMap.remove(player);
            onCoolDownEnd(player, event);
        }, 20 * coolDown);
    }

    protected void onCoolDownEnd(Player player, Event event) {

    }

    /**
     * Will be called when the user selects the kit with added ability
     */
    public void onKitSelection(Player player, Kit kit) {

    }

    /**
     * Will be activated when the user tries to activate the ability but the cool down is still active
     */
    protected void onNotifyCoolDown(Player player, Event event) {
        float seconds = System.currentTimeMillis() / 1000f;
        player.sendMessage("§cYou have to wait " + Math.round(seconds) + " seconds");

    }

    protected abstract void activate(Player player, Event event);

}
