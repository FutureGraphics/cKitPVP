package me.theremixpvp.ckitpvp.commands;

import me.theremixpvp.ckitpvp.User;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BankCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;

        Player p = (Player) sender;

        User user = User.byPlayer(p);

        if (args.length == 0) {

            Inventory inv = p.getEnderChest();
            p.openInventory(inv);
            p.sendMessage(ChatColor.GOLD + "Bank opened.");
            return true;
        }

        return true;
    }

}
