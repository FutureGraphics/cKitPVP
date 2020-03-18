package me.theremixpvp.ckitpvp.commands;

import me.theremixpvp.ckitpvp.User;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreditCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 0) {
            if (!(sender instanceof Player))
                return true;

            Player player = (Player) sender;

            User user = User.byPlayer(player);

            if (user == null) {
                player.sendMessage(ChatColor.RED + "A critical error has occurred! Tell the owner!");
                return true;
            }

            player.sendMessage(ChatColor.AQUA + "Credits: " + ChatColor.DARK_AQUA + user.getCredits());
            return true;
        }

        if (args.length >= 3) {
            if (!sender.isOp()) {
                return true;
            }

            if (args[0].equalsIgnoreCase("set")) {

                User user = User.byName(args[1]);

                if (user != null) {
                    user.setCredits(Integer.parseInt(args[2]));
                    sender.sendMessage(ChatColor.GREEN + "Set " + user.getName() + "'s tokens to "
                            + Double.parseDouble(args[2]) + ".");
                    return true;
                }
            }
            return false;
        }
        return false;

    }

}
