package me.theremixpvp.ckitpvp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;

        Player player = (Player) sender;

        if (!player.isOp()) {
            return true;
        }

        ItemStack itemStack = player.getItemInHand();
        if(itemStack == null) {
            player.sendMessage(ChatColor.RED + "You don't have anything in your hand");
            return true;
        }

        ItemStack oi = player.getInventory().getHelmet();
        player.getInventory().setHelmet(itemStack);
        player.getInventory().setItemInHand(oi);

        player.sendMessage(ChatColor.GREEN + "Enjoy your hat!");
        return true;
    }

}
