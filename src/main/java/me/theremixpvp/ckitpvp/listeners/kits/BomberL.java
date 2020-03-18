package me.theremixpvp.ckitpvp.listeners.kits;

import me.theremixpvp.ckitpvp.KitPvP;
import me.theremixpvp.ckitpvp.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class BomberL implements Listener {

    public final ArrayList<Player> cooldown = new ArrayList<>();
    KitPvP kitPvP;

    public BomberL(KitPvP plugin) {
        plugin = kitPvP;
    }

    @EventHandler
    public void onBombThrown(final PlayerEggThrowEvent e) {
        final Player p = e.getPlayer();
        User pd = PDUtils.getByName(p.getName());
        if (pd.getKit() != null && pd.getKit().equalsIgnoreCase("Bomber")) {
            if (cooldown.contains(p)) {
                p.sendMessage(ChatColor.RED + "Your bombing ability is still on cooldown!");
                ItemStack bombs = new ItemStack(Material.EGG);
                ItemMeta bombmeta = bombs.getItemMeta();
                bombmeta.setDisplayName(ChatColor.GREEN + "Bomb");
                bombs.setItemMeta(bombmeta);
                p.getInventory().setItem(1, bombs);
                return;
            }

            cooldown.add(p);
            Bukkit.getScheduler().scheduleSyncDelayedTask(KitPvP.instance, () -> {
                p.getWorld().createExplosion(e.getEgg().getLocation(), 3F, false);
                p.getWorld().createExplosion(e.getEgg().getLocation(), 2F, false);
                p.getWorld().createExplosion(e.getEgg().getLocation(), 1F, false);
                Bukkit.getScheduler().scheduleSyncDelayedTask(KitPvP.instance, () -> {
                    cooldown.remove(p);
                    ItemStack bombs = new ItemStack(Material.EGG);
                    ItemMeta bombmeta = bombs.getItemMeta();
                    bombmeta.setDisplayName(ChatColor.GREEN + "Bomb");
                    bombs.setItemMeta(bombmeta);
                    p.getInventory().addItem(bombs);
                }, 20 * 3);
            }, 20 * 3);
        }
    }

}
