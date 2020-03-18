package me.theremixpvp.ckitpvp.listeners;

import me.theremixpvp.ckitpvp.User;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerListener implements Listener {

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent e) {
        ItemStack itemStack = e.getItem().getItemStack();

        if (!itemStack.hasItemMeta()) {
            return;
        }

        ItemMeta meta = itemStack.getItemMeta();

        if (!meta.hasLore())
            return;

        int amount;
        try {
            amount = Integer.parseInt(itemStack.getItemMeta().getDisplayName());
        } catch (Exception ignored) {
            return;
        }

        Player p = e.getPlayer();

        User user = User.byPlayer(p);
        user.setCredits(user.getCredits() + amount);

        p.sendMessage(ChatColor.GREEN + "You received " + amount + " credits for killing "
                + itemStack.getItemMeta().getLore().get(0) + "!");

        e.getItem().remove();
        e.setCancelled(true);
    }

}
