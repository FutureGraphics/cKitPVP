package me.theremixpvp.ckitpvp.listeners;

import me.theremixpvp.ckitpvp.configuration.PluginConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SoupListener implements Listener {

    private final ItemStack bowl;

    public SoupListener() {
        bowl = new ItemStack(Material.BOWL);
        ItemMeta itemMeta = bowl.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "Bowl");
        bowl.setItemMeta(itemMeta);
    }

    @EventHandler
    public void drinkSoup(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack inHand = player.getItemInHand();
        if ((e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
                || inHand == null || inHand.getType() != Material.MUSHROOM_SOUP) {
            return;
        }


        if (inHand.getItemMeta().getDisplayName().equals(ChatColor.RED + "" + ChatColor.BOLD + "SUPERSOUP")) {
            player.setHealth(20.0);
            player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 1000, 100));
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1000000, 100));
            player.setHealthScaled(false);

            player.setItemInHand(bowl.clone());

            e.setCancelled(true);
            return;
        }

        if (player.getHealth() != 20) {
            double ch = player.getHealth();
            double health = ch + PluginConfiguration.soupHeal > 20 ? 20 : ch + PluginConfiguration.soupHeal;
            player.setHealth(health);

            player.setItemInHand(bowl.clone());
        }

        if (player.getFoodLevel() != 20) {
            int cf = player.getFoodLevel();

            int foodLevel = Math.min(cf + PluginConfiguration.soupHeal, 20);
            player.setFoodLevel(foodLevel);

            player.setItemInHand(bowl.clone());
            e.setCancelled(true);
        }

    }

}
