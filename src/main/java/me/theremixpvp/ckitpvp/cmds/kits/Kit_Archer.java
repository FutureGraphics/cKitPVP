package me.theremixpvp.ckitpvp.cmds.kits;

import me.theremixpvp.ckitpvp.KitPvP;
import me.theremixpvp.ckitpvp.PDUtils;
import me.theremixpvp.ckitpvp.PData;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

public class Kit_Archer implements CommandExecutor {
	
	KitPvP kitPvP;
	
	public Kit_Archer(KitPvP plugin) {
		plugin = kitPvP;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can use this commands!");
			return true;
		}
		PData pd = PDUtils.getByName(sender.getName());
		
		Player p = (Player)sender;
		
		if(KitPvP.usedkit.contains(p) && !p.isOp()) {
			p.sendMessage(ChatColor.RED + "Only one kit per life!");
			return true;
		}
		
		PlayerInventory inv = p.getInventory();
		
		inv.clear();
		for(PotionEffect pe : p.getActivePotionEffects()) {
			p.removePotionEffect(pe.getType());
		}
		
		ItemStack sword = new ItemStack(Material.WOOD_SWORD);
		sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
		sword.addEnchantment(Enchantment.DURABILITY, 3);
		inv.addItem(sword);
		
		ItemStack bow = new ItemStack(Material.BOW);
		bow.addEnchantment(Enchantment.ARROW_DAMAGE, 3);
		bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
		inv.addItem(bow);
		
		inv.setArmorContents(new ItemStack[] {
				new ItemStack(Material.IRON_BOOTS),
				new ItemStack(Material.LEATHER_LEGGINGS),
				new ItemStack(Material.LEATHER_CHESTPLATE),
				new ItemStack(Material.CHAINMAIL_HELMET),
		});
		
		for(int i = 0; i < 33; i++) {
			ItemStack soup = new ItemStack(Material.MUSHROOM_SOUP);
			ItemMeta im = soup.getItemMeta();
			im.setDisplayName(ChatColor.DARK_AQUA + "Stew");
			soup.setItemMeta(im);
			inv.addItem(soup);
		}
		inv.addItem(new ItemStack(Material.ARROW));
		
		p.sendMessage(ChatColor.DARK_AQUA + "Archer kit equipped!");
		p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 7.0F, 7.0F);
		pd.setKit("Archer");
		KitPvP.usedkit.add(p);
		return true;
	}

}
