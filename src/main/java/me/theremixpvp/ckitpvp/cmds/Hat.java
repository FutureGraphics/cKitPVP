package me.theremixpvp.ckitpvp.cmds;

import me.theremixpvp.ckitpvp.KitPvP;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Hat implements CommandExecutor {
	
	KitPvP kitPvP;
	
	public Hat(KitPvP plugin) {
		plugin = kitPvP;
	}
	
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return true;
		Player p = (Player) sender;
		if(!p.isOp()) {
			return true;
		}
		
		ItemStack i = p.getItemInHand();
		ItemStack oi = p.getInventory().getHelmet();
		p.getInventory().setHelmet(i);
		p.getInventory().setItemInHand(oi);
		p.sendMessage(ChatColor.GREEN + "Enjoy your hat!");
		return true;
    }

}
