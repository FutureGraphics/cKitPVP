package me.theremixpvp.ckitpvp.commands;

import me.theremixpvp.ckitpvp.KitPvP;
import me.theremixpvp.ckitpvp.configuration.PluginConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SoupCommand implements CommandExecutor {

    private final List<UUID> coolDown = new ArrayList<>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;

        final Player p = (Player) sender;
        if (!(p.hasPermission("ckitpvp.soup"))) {
            p.sendMessage(ChatColor.RED + "You don't have access to this! Donate for access!");
            return true;
        }

        if (coolDown.contains(p.getUniqueId()) && !p.isOp()) {
            p.sendMessage(ChatColor.RED + "Still cooling down!");
            return true;
        }

        Inventory inv = Bukkit.createInventory(p, 54);

        for (int i = 0; i < 54; i++) {
            ItemStack soup = new ItemStack(Material.MUSHROOM_SOUP);
            ItemMeta im = soup.getItemMeta();
            im.setDisplayName(ChatColor.DARK_AQUA + "Stew");
            soup.setItemMeta(im);
            inv.addItem(soup);
        }
        p.openInventory(inv);

        if (!p.isOp())
            coolDown.add(p.getUniqueId());

        Bukkit.getScheduler().scheduleSyncDelayedTask(KitPvP.instance, () -> {
            coolDown.remove(p.getUniqueId());
            p.sendMessage(ChatColor.GREEN + "You can use /soup again!");
        }, PluginConfiguration.coolDown.soupCommand * 20);
        return true;
    }

}
