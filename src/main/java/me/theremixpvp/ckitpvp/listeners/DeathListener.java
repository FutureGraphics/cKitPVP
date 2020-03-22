package me.theremixpvp.ckitpvp.listeners;

import me.theremixpvp.ckitpvp.User;
import me.theremixpvp.ckitpvp.configuration.PluginConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.Random;

public class DeathListener implements Listener {

    private final Random random;

    public DeathListener() {
        random = new Random();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        Player killer = player.getKiller();

        User.byPlayer(player).addDeath();
        User.byPlayer(killer).addKill();

        dropAward(player, killer);


        if (PluginConfiguration.deathMessage)
            e.setDeathMessage(ChatColor.DARK_AQUA + killer.getName() + ChatColor.GRAY + " killed " +
                    ChatColor.DARK_AQUA + player.getName());
        else
            e.setDeathMessage(null);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        User.byPlayer(event.getPlayer()).giveKitItems();
    }

    private void dropAward(Player player, Player killer) {
        int reward = random.nextInt(100) + 1;

        ItemStack rewardItem = new ItemStack(Material.EMERALD);
        ItemMeta im = rewardItem.getItemMeta();
        im.setDisplayName(String.valueOf(reward));
        im.setLore(Collections.singletonList(player.getName()));
        rewardItem.setItemMeta(im);


        Location location = player.getLocation();
        location.setY(location.getBlockY() + 3);
        killer.getWorld().dropItem(location, rewardItem);
    }

}
