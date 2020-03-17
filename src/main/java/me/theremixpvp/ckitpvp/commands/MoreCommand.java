package me.theremixpvp.ckitpvp.commands;

import me.theremixpvp.ckitpvp.KitPvP;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MoreCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;

        Player p = (Player) sender;
        if (!p.isOp()) {
            return false;
        }

        ItemStack stack = p.getItemInHand();
        if(stack == null) {
            p.sendMessage(ChatColor.RED + "You have nothing in your hand");
            return true;
        }

        stack.setAmount(64);
        p.setItemInHand(stack);

        return true;
    }

}
