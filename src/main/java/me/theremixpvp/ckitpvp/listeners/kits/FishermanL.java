package me.theremixpvp.ckitpvp.listeners.kits;

import me.theremixpvp.ckitpvp.KitPvP;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class FishermanL implements Listener {

    KitPvP kitPvP;

    public FishermanL(KitPvP plugin) {
        plugin = kitPvP;
    }

    @EventHandler
    public void onPlayerFished(final PlayerFishEvent event) {
        final Player player = event.getPlayer();
        if (PDUtils.getByName(player.getName()).getKit() != null && PDUtils.getByName(player.getName()).getKit().equalsIgnoreCase("fisherman")) {
            if (event.getCaught() instanceof Player) {
                final Player caught = (Player) event.getCaught();
                if (player.getItemInHand().getType() == Material.FISHING_ROD) {
                    caught.teleport(player.getLocation());
                }
            }
            final Entity caught = event.getCaught();
            if (PDUtils.getByName(player.getName()).getKit() != null && PDUtils.getByName(player.getName()).getKit().equalsIgnoreCase("fisherman")) {
                if (player.getItemInHand().getType() == Material.FISHING_ROD) {
                    if (caught != null) {
                        caught.getLocation().setY(player.getLocation().getY() - 2);
                        caught.teleport(player.getLocation());
                        caught.setVelocity(player.getVelocity());
                    }
                }
            }
        }
    }

}
