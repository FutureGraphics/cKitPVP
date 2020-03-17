package me.theremixpvp.ckitpvp.cmds;

import me.theremixpvp.ckitpvp.KitPvP;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

public class Test implements CommandExecutor, Listener {
	
	KitPvP kitPvP;
	
	public Test(KitPvP plugin) {
		plugin = kitPvP;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		return false;
	}

}
