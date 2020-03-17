package me.theremixpvp.ckitpvp.listeners;

import me.theremixpvp.ckitpvp.KitPvP;
import me.theremixpvp.ckitpvp.PData;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
	
	KitPvP kitPvP;
	
	public JoinListener(KitPvP plugin) {
		plugin = kitPvP;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if(!KitPvP.pdata.containsKey(e.getPlayer().getName())) {
			PData pd = new PData(e.getPlayer().getName());
			pd.setCredits(0);
			pd.setKills(0);
			pd.setDeaths(0);
			KitPvP.pdata.put(e.getPlayer().getName(), pd);
		}
		e.getPlayer().sendMessage(ChatColor.GREEN + "Plugin coded by " + ChatColor.RED + "" + ChatColor.BOLD + "TheRemixPvP");
	}
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
		if(e.getMessage().equalsIgnoreCase("listpds")) {
			String s = KitPvP.pdata.toString();
			e.getPlayer().sendMessage(s);
		}
	}

}
