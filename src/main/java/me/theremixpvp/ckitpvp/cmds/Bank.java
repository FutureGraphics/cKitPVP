package me.theremixpvp.ckitpvp.cmds;

import me.theremixpvp.ckitpvp.KitPvP;
import me.theremixpvp.ckitpvp.PDUtils;
import me.theremixpvp.ckitpvp.PData;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Bank implements CommandExecutor {
	
	KitPvP kitPvP;
	
	public Bank(KitPvP plugin) {
		plugin = kitPvP;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) return true;
		
		Player p = (Player) sender;
		
		if(args.length == 0) {
			PData pd = PDUtils.getByName(p.getName());

			Inventory inv = p.getEnderChest();
			p.openInventory(inv);
			p.sendMessage(ChatColor.GOLD + "Bank opened.");
			return true;
		} else if(args.length == 1 && args[0].equalsIgnoreCase("reset")) {
			if(!(p.hasPermission("ckitpvp.bank.reset"))) return true;
			PData pd = PDUtils.getByName(p.getName());
			pd.setBank(null);
			return true;
		}
		
		return false;
		
	}

}
