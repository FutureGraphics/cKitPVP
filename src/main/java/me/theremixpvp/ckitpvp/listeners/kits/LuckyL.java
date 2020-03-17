package me.theremixpvp.ckitpvp.listeners.kits;

import java.util.Random;

import me.theremixpvp.ckitpvp.KitPvP;
import me.theremixpvp.ckitpvp.PDUtils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class LuckyL implements Listener {
	
	KitPvP kitPvP;
	
	public LuckyL(KitPvP plugin) {
		plugin = kitPvP;
	}
	
	@EventHandler
	public void LuckyAbility(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player) {
			
				if(e.getDamager() instanceof Player) {
					Player h = (Player) e.getEntity();
					Player p = (Player) e.getDamager();
					if(PDUtils.getByName(p.getName()).getKit() != null && PDUtils.getByName(p.getName()).getKit().equalsIgnoreCase("Lucky")) {
					Random rand = new Random();
					int yn = rand.nextInt(100);
					if(yn <= 20) {
						Random rand2 = new Random();
						int ri = rand2.nextInt(10);
						e.setDamage(e.getDamage() + ri / 2);
						h.sendMessage(ChatColor.DARK_AQUA + "You were " + ri + "x hit by " + p.getName());
						p.sendMessage(ChatColor.DARK_AQUA + "You " + ri + "x hit " + h.getName() + "!");
					}
				}
			}
		} else {
				if(e.getDamager() instanceof Player) {
					Entity h = e.getEntity();
					Player p = (Player) e.getDamager();
					if(PDUtils.getByName(p.getName()).getKit() != null && PDUtils.getByName(p.getName()).getKit().equalsIgnoreCase("Lucky")) {
					Random rand = new Random();
					int yn = rand.nextInt(100);
					if(yn <= 20) {
						Random rand2 = new Random();
						int ri = rand2.nextInt(10);
						e.setDamage(e.getDamage() + ri);
						p.sendMessage(ChatColor.DARK_AQUA + "You " + ri + "x hit " + h.getType() + "!");
					}
				}
			}
		}
	}

}
