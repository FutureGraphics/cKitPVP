package me.theremixpvp.ckitpvp.listeners.kits;

import me.theremixpvp.ckitpvp.KitPvP;
import me.theremixpvp.ckitpvp.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class RusherL implements Listener {

    private final ArrayList<String> c = new ArrayList<>();
    KitPvP kitPvP;
    Plugin pl = kitPvP;

    public RusherL(KitPvP plugin) {
        plugin = kitPvP;
    }

    @EventHandler
    public void RushAbility(PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        User pd = PDUtils.getByName(p.getName());
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (p.getItemInHand().getType() == Material.FEATHER) {
                if (pd.getKit() != null && pd.getKit().equalsIgnoreCase("rusher")) {
                    if (c.contains(p.getName())) {
                        p.sendMessage(ChatColor.RED + "Your Rush ability is still on cooldown!");
                        return;
                    }
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 160, 2));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 160, 1));
                    c.add(p.getName());
                    Bukkit.getScheduler().scheduleSyncDelayedTask(KitPvP.instance, () -> {
                        c.remove(p.getName());
                        p.sendMessage(ChatColor.DARK_AQUA + "Your Rush ability is recharged!");
                    }, 600);
                }
            }
        }
    }


}
