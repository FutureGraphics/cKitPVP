package me.theremixpvp.ckitpvp.commands;

import me.theremixpvp.ckitpvp.User;
import me.theremixpvp.ckitpvp.kit.Kit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.DARK_AQUA + "/kit <player>");
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Invalid player!");
            return true;
        }

        User user = User.byPlayer(player);
        Kit kit = user.getKit();

        if (kit == null)
            sender.sendMessage(ChatColor.DARK_AQUA + player.getName() + " does not have a kit selected");
        else
            sender.sendMessage(ChatColor.DARK_AQUA + player.getName() + "'s Kit: " + kit.getName());
        return true;
    }

}
