package me.theremixpvp.ckitpvp.commands;

import me.theremixpvp.ckitpvp.KitPvP;
import me.theremixpvp.ckitpvp.User;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatCommand implements CommandExecutor {

    KitPvP kitPvP;

    public StatCommand(KitPvP plugin) {
        plugin = kitPvP;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Only players can use this command!");
                return true;
            }

            Player p = (Player) sender;
            User user = User.byPlayer(p);

            int credits = user.getCredits();
            int kills = user.getKills();
            int deaths = user.getDeaths();
            p.sendMessage(ChatColor.DARK_AQUA + "----[ " + p.getName() + " ]----");
            p.sendMessage(ChatColor.GRAY + "Kills: " + ChatColor.DARK_AQUA + kills);
            p.sendMessage(ChatColor.GRAY + "Deaths: " + ChatColor.DARK_AQUA + deaths);
            p.sendMessage(ChatColor.GRAY + "Credits: " + ChatColor.DARK_AQUA + credits);

            return true;
        }

        User user = User.byName(args[0]);
        if (user == null) {
            sender.sendMessage(ChatColor.RED + "Invalid player!");
            return true;
        }

        if (args.length == 1) {
            int credits = user.getCredits();
            int kills = user.getKills();
            int deaths = user.getDeaths();

            sender.sendMessage(ChatColor.DARK_AQUA + "----[ " + user.name() + " ]----");
            sender.sendMessage(ChatColor.GRAY + "Kills: " + ChatColor.DARK_AQUA + kills);
            sender.sendMessage(ChatColor.GRAY + "Deaths: " + ChatColor.DARK_AQUA + deaths);
            sender.sendMessage(ChatColor.GRAY + "Credits: " + ChatColor.DARK_AQUA + credits);
            return true;
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("clear")) {
                user.setCredits(0);
                user.setDeaths(0);
                user.setKills(0);

                sender.sendMessage(ChatColor.DARK_AQUA + user.getName() + "'s stats cleared!");
                return true;
            }
        }


        return false;
    }

}
