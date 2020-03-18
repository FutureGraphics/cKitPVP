package me.theremixpvp.ckitpvp.listeners;

import me.theremixpvp.ckitpvp.User;
import me.theremixpvp.ckitpvp.configuration.PluginConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        User user = User.byPlayer(player);

        if (user == null) {
            user = new User(player.getName(), player.getUniqueId(), null);
            User.addUser(user);
        }

        if (PluginConfiguration.showJoinCredits)
            player.sendMessage(ChatColor.GREEN + "Plugin coded by " + ChatColor.RED + ChatColor.BOLD + "TheRemixPvP");
    }

}
