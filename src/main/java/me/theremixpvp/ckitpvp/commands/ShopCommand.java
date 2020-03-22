package me.theremixpvp.ckitpvp.commands;

import com.flouet.code.utilities.minecraft.api.inventory.InventoryMap;
import me.theremixpvp.ckitpvp.KitPvP;
import me.theremixpvp.ckitpvp.User;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopCommand implements CommandExecutor {


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use the shop!");
            return true;
        }

        final Player player = (Player) sender;
        User user = User.byPlayer(player);

        InventoryMap inventory = KitPvP.menuManager.createInventory(player);
        inventory.generateInventory();
        user.setInventory(inventory);

        player.openInventory(inventory.getInventory());
        return true;
    }


}
