package me.theremixpvp.ckitpvp.commands;

import me.theremixpvp.ckitpvp.User;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerDataCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender.hasPermission("ckitpvp.playerdata.admin"))) {
            return false;
        }

        if (args.length <= 0) {
            sender.sendMessage(ChatColor.GOLD + "/user <set:add:reset> <Player> <option> [value]");
            return true;
        }
        
        User user = User.byName(args[0]);
        
        if(user == null) {
            sender.sendMessage(ChatColor.RED + "Player not found!");
            return true;
        }
        
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("reset")) {
                reset(user, args, sender);
            }
        }

        return true;
    }

    private void reset(User user, String[] args, CommandSender sender ) {
        if (args[2].equalsIgnoreCase("all")) {
            user.setCredits(0);
            user.setDeaths(0);
            user.setKills(0);
            user.getUnlockedKits().clear();
            sender.sendMessage(ChatColor.GOLD + user.getName() + "'s playerdata has been reset!");
        } else if (args[2].equalsIgnoreCase("credits")) {
            user.setCredits(0);
            sender.sendMessage(ChatColor.GOLD + "Credits reset!");
        } else if (args[2].equalsIgnoreCase("kills")) {
            user.setKills(0);
            sender.sendMessage(ChatColor.GOLD + "Kills reset!");
        } else if (args[2].equalsIgnoreCase("deaths")) {
            user.setDeaths(0);
            sender.sendMessage(ChatColor.GOLD + "Deaths reset!");
        } else if (args[2].equalsIgnoreCase("kits")) {
            user.getUnlockedKits().clear();
            sender.sendMessage(ChatColor.GOLD + "Kits reset!");
        }
    }
    
}
