package me.theremixpvp.ckitpvp.listeners.kits;

import me.theremixpvp.ckitpvp.KitPvP;
import me.theremixpvp.ckitpvp.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class VisionMasterL implements Listener {

    private final ArrayList<Player> sc = new ArrayList<>();
    KitPvP kitPvP;

    public VisionMasterL(KitPvP plugin) {
        plugin = kitPvP;
    }

    @EventHandler
    public void StarShoot(PlayerInteractEvent e) {
        User pd = PDUtils.getByName(e.getPlayer().getName());
        if (pd.getKit() != null && pd.getKit().equalsIgnoreCase("VisionMaster") && e.getPlayer().getItemInHand().getType() == Material.NETHER_STAR) {
            final Player p = e.getPlayer();
            if (sc.contains(p)) {
                p.sendMessage(ChatColor.RED + "Please wait a second to shoot your VisionArrow again!");
                return;
            }
            Arrow a = p.launchProjectile(Arrow.class);
            a.setMetadata("isVisionArrow", new FixedMetadataValue(KitPvP.instance, true));
            sc.add(p);
            Bukkit.getScheduler().scheduleSyncDelayedTask(KitPvP.instance, () -> sc.remove(p), 60);
        }
    }

    @EventHandler
    public void VisionArrowHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Arrow && e.getDamager().hasMetadata("isVisionArrow")) {
            if (e.getEntity() instanceof Player) {
                Player hit = (Player) e.getEntity();
                hit.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 160, 2));
            }
        }
    }

}
