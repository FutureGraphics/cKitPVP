package me.theremixpvp.ckitpvp.kit;

import me.theremixpvp.ckitpvp.KitPvP;
import me.theremixpvp.ckitpvp.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final Map<Class<? extends Event>, AbilityEventListener<? extends Event>> additionalEventListener;

    public Ability(String name, int coolDown) {
        this.name = name;
        this.coolDown = coolDown;
        this.additionalEventListener = new HashMap<>();
    }

    public boolean isApplicableAdditionalEvent(Event event) {
        for (Class<? extends Event> clazz : additionalEventListener.keySet()) {
            if (event.getClass().isAssignableFrom(clazz))
                return true;
        }

        return false;
    }

    public boolean isApplicableAbilityEvent(Kit kit, Event event) {
        return kits.contains(kit) && canActivate(event);
    }

    protected <T extends Event> void addAdditionalEventListener(Class<T> clazz, AbilityEventListener<T> listener) {
        additionalEventListener.put(clazz, listener);
    }

    protected abstract boolean canActivate(Event event);

    public void addKit(Kit kit) {
        kits.add(kit);
    }

    public void activateAbility(Player player, User user, Event event) {
        if (coolDown > 0 && coolDownMap.containsKey(player) && coolDownMap.get(player) > System.currentTimeMillis()) {
            onNotifyCoolDown(player, event);
            return;
        }

        activate(player, user, event);

        if (coolDown > 0) {
            coolDownMap.put(player, System.currentTimeMillis() + (coolDown * 1000));
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
        float seconds = (coolDownMap.get(player) - System.currentTimeMillis()) / 1000f;
        player.sendMessage("§cYou have to wait " + Math.round(seconds) + " seconds");

    }

    protected abstract void activate(Player player, User user, Event event);

    public List<Kit> getKits() {
        return this.kits;
    }

    public Map<Class<? extends Event>, AbilityEventListener<? extends Event>> getAdditionalEventListener() {
        return additionalEventListener;
    }

    protected interface AbilityEventListener<T extends Event> {

        void onEvent(T event);

        default void trigger(Event event) {
            onEvent((T) event);
        }
    }

    public String getName() {
        return name;
    }
}
