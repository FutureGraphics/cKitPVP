package me.theremixpvp.ckitpvp.shop;

import me.theremixpvp.ckitpvp.PDUtils;
import me.theremixpvp.ckitpvp.PData;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class MenuManager {
	
	static final MenuManager instance = new MenuManager();
	
	public static MenuManager getInstance() {
		return instance;
	}
	
	public void giveItem(ShopItem i, Player p) {
		PData pd = PDUtils.getByName(p.getName());
		if(pd.credits() >= i.getPrice()) {
			pd.setCredits(pd.credits() - i.getPrice());
			p.sendMessage(ChatColor.GREEN + "You bought a " + i.getName() + " for " + i.getPrice());
			p.getInventory().addItem(i.getItem());
			return;
		}
		p.sendMessage(ChatColor.RED + "You cannot afford this!");
	}
	
	public void enchantItem(ShopItem i, Enchantment e, int level, Player p) {
		PData pd = PDUtils.getByName(p.getName());
		if(pd.credits() >= i.getPrice()) {
			pd.setCredits(pd.credits() - i.getPrice());
			i.getItem().addUnsafeEnchantment(e, level);
			p.sendMessage(ChatColor.GREEN + "You bought a " + e.getName() + " " + level + " enchantment for " + i.getPrice() + "!");
			return;
		}
		p.sendMessage(ChatColor.RED + "You cannot afford this!");
	}
	
	public void giveKit(String k, int price, Player p) {
		PData pd = PDUtils.getByName(p.getName());
		
		if(pd.unlockedkits().contains(k) || p.hasPermission("ckitpvp.kit." + k)) {
			p.sendMessage(ChatColor.RED + "You already own this kit!");
			return;
		}
		if(pd.credits() >= price) {
			pd.addkit(k);
			pd.setCredits(pd.credits() - price);
			p.sendMessage(ChatColor.GREEN + "You bought the " + k + " kit for " + price + "!");
			return;
		}
		p.sendMessage(ChatColor.RED + "You cannot afford this!");
	}

}
