package me.theremixpvp.ckitpvp.shop;

import me.theremixpvp.ckitpvp.KitPvP;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ShopCmd implements CommandExecutor {


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use the shop!");
            return true;
        }


        final Player p = (Player) sender;

        ShopMenu menu = new ShopMenu(ChatColor.GOLD + "" + ChatColor.ITALIC + "Shop", 9, event -> {
            if (event.getItem().getType() == Material.DIAMOND_SWORD) {
                event.setWillClose(false);
                event.setWillDestroy(false);
                p.closeInventory();
                ShopMenu m = Menus.weapons;
                m.open(p);
                return;
            } else if (event.getItem().getType() == Material.ENCHANTED_BOOK) {
                event.setWillClose(false);
                event.setWillDestroy(false);
                p.closeInventory();
                ShopMenu m = Menus.enchants;
                m.open(p);
                return;
            } else if (event.getItem().getType() == Material.CHEST) {
                event.setWillClose(false);
                event.setWillDestroy(false);
                p.closeInventory();
                ShopMenu kits = new ShopMenu(ChatColor.AQUA + "" + ChatColor.ITALIC + "Kits", 36, event1 -> {

                    if (event1.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("Dodge"))
                        MenuManager.getInstance().giveKit("Dodge", 640, event1.getPlayer());
                    if (event1.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("Fisherman"))
                        MenuManager.getInstance().giveKit("Fisherman", 800, event1.getPlayer());
                    if (event1.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("Hulk"))
                        MenuManager.getInstance().giveKit("Hulk", 700, event1.getPlayer());
                    if (event1.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("Bomber"))
                        MenuManager.getInstance().giveKit("Bomber", 650, event1.getPlayer());

                }, KitPvP.instance)
                        .setOption(0, new ItemStack(Material.ARROW), "Dodge", "Price: 640C", Arrays.asList(ChatColor.GREEN + "Return ALL projectiles", ChatColor.GREEN + "back to their sender!"))
                        .setOption(1, new ItemStack(Material.FISHING_ROD), "Fisherman", "Price: 800C", Arrays.asList(ChatColor.GREEN + "Reel in with a player on your line", ChatColor.GREEN + "and the player will be teleported to you!"))
                        .setOption(2, new ItemStack(Material.EMERALD), "Hulk", "Price: 700C", Arrays.asList(ChatColor.GREEN + "Use your special hulk strength ball", ChatColor.GREEN + "to activate the power of Hulk!"))
                        .setOption(3, new ItemStack(Material.FIREBALL), "Bomber", "Price: 650C", Arrays.asList(ChatColor.GREEN + "Use your bombs to jar your", ChatColor.GREEN + "enemies with powerful explosions!"));
                kits.open(p);
                return;
            }


        }, KitPvP.instance)
                .setOption(0, new ItemStack(Material.DIAMOND_SWORD), "Weapons", "Buy weapons.")
                .setOption(1, new ItemStack(Material.ENCHANTED_BOOK), "Enchantments", "Buy enchantments.")
                .setOption(2, new ItemStack(Material.CHEST), "Kits", "Buy kits");
        menu.open(p);


        return false;
    }


}
