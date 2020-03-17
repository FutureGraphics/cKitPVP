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

public class HulkL implements Listener {

    private final ArrayList<String> hulkc = new ArrayList<>();
    KitPvP kitPvP;
    Plugin pl = kitPvP;

    public HulkL(KitPvP plugin) {
        plugin = kitPvP;
    }

    @EventHandler
    public void HulkAbility(PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        User pd = PDUtils.getByName(p.getName());
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (p.getItemInHand().getType() == Material.FIREWORK_CHARGE) {
                if (pd.getKit() != null && pd.getKit().equalsIgnoreCase("hulk")) {
                    if (hulkc.contains(p.getName())) {
                        p.sendMessage(ChatColor.RED + "Your Hulk ability is still on cooldown!");
                        return;
                    }
                    p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 300, 1));
                    hulkc.add(p.getName());
                    p.sendMessage(ChatColor.GREEN + "You have activated the power of hulk!");
                    Bukkit.getScheduler().scheduleSyncDelayedTask(KitPvP.instance, () -> {
                        hulkc.remove(p.getName());
                        p.sendMessage(ChatColor.DARK_AQUA + "Your hulk ability is recharged!");
                    }, 600);
                }
            }
        }
    }


}
