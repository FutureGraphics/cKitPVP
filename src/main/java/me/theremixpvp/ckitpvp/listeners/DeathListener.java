package me.theremixpvp.ckitpvp.listeners;

import me.theremixpvp.ckitpvp.configuration.PluginConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.Random;

public class DeathListener implements Listener {


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();

        Player killer = player.getKiller();


        dropAward(player, killer);


        if (PluginConfiguration.deathMessage)
            e.setDeathMessage(ChatColor.DARK_AQUA + killer.getName() + ChatColor.GRAY + " killed " +
                    ChatColor.DARK_AQUA + player.getName());
        else
            e.setDeathMessage(null);
    }

    private void dropAward(Player player, Player killer) {
        Random random = new Random();
        int reward = random.nextInt(100) + 1;

        ItemStack rewardItem = new ItemStack(Material.EMERALD);
        ItemMeta im = rewardItem.getItemMeta();
        im.setDisplayName(String.valueOf(reward));
        im.setLore(Collections.singletonList(player.getName()));
        rewardItem.setItemMeta(im);


        Location location = player.getLocation();
        location.setY(location.getBlockY() + 3);
        killer.getWorld().dropItem(location, rewardItem);
    }
	
	/*@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		main.usedkit.remove(e.getEntity().getPlayer());
		Player vic = e.getEntity();
		int lvls = vic.getLevel();
		Player k = vic.getKiller();
		Random rand = new Random();
		int ri = rand.nextInt(10) + 1;
		double rd = ri;
		PData pd = PDUtils.getByName(k.getName());
		pd.setCredits(pd.credits() + rd);
		pd.setKills(pd.kills() + 1);
		PDUtils.getByName(vic.getName()).setDeaths(PDUtils.getByName(vic.getName()).deaths() + 1);
		k.sendMessage(ChatColor.GREEN + "You earned " + rd + " credits for killing " + vic.getName() + "!");
		PDUtils.getByName(vic.getName()).setKit(null);
		
		if(main.getConfig().getBoolean("death-messages") == true) {
			e.setDeathMessage(ChatColor.DARK_AQUA + k.getName() + ChatColor.GRAY + " killed " + ChatColor.DARK_AQUA + vic.getName());
		} else {
			e.setDeathMessage(null);
		}
		
		
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		
		if(!(e.getEntity().getKiller() instanceof Player)) return;
		Player k = e.getEntity().getKiller();
		Random rand = new Random();
		int ri = rand.nextInt(10) + 1;
		double rd = ri;
		PData pd = PDUtils.getByName(k.getName());
		pd.setCredits(pd.credits() + rd);
		pd.setKills(pd.kills() + 1);
		e.setDroppedExp(0);
		
		if(Settings.deathmessages == true) Bukkit.broadcastMessage(ChatColor.DARK_AQUA + k.getName() + ChatColor.GRAY + " killed " + ChatColor.DARK_AQUA + e.getEntityType());
	}*/

}
