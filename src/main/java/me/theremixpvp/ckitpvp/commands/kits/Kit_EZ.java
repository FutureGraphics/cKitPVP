package me.theremixpvp.ckitpvp.commands.kits;

import me.theremixpvp.ckitpvp.KitPvP;
import me.theremixpvp.ckitpvp.User;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

public class Kit_EZ implements CommandExecutor {

    KitPvP kitPvP;

    public Kit_EZ(KitPvP plugin) {
        plugin = kitPvP;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this commands!");
            return true;
        }

        User pd = PDUtils.getByName(sender.getName());
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "You do not have permission for this kit!");
            return true;
        }

        Player p = (Player) sender;

        if (KitPvP.usedkit.contains(p) && !p.isOp()) {
            p.sendMessage(ChatColor.RED + "Only one kit per life!");
            return true;
        }


        PlayerInventory inv = p.getInventory();

        inv.clear();
        for (PotionEffect pe : p.getActivePotionEffects()) {
            p.removePotionEffect(pe.getType());
        }

        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 5);
        sword.addEnchantment(Enchantment.FIRE_ASPECT, 2);
        inv.addItem(sword);

        ItemStack h = new ItemStack(Material.DIAMOND_HELMET);
        h.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);

        ItemStack c = new ItemStack(Material.DIAMOND_CHESTPLATE);
        c.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);

        ItemStack l = new ItemStack(Material.DIAMOND_LEGGINGS);
        l.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);

        ItemStack b = new ItemStack(Material.DIAMOND_BOOTS);
        b.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);

        inv.setArmorContents(new ItemStack[]{
                b,
                l,
                c,
                h,
        });

        for (int i = 0; i < 34; i++) {
            ItemStack soup = new ItemStack(Material.MUSHROOM_SOUP);
            ItemMeta im = soup.getItemMeta();
            im.setDisplayName(ChatColor.DARK_AQUA + "Stew");
            soup.setItemMeta(im);
            inv.addItem(soup);
        }

        p.sendMessage(ChatColor.DARK_AQUA + "EZ kit equipped!");
        p.playSound(p.getLocation(), Sound.ANVIL_LAND, 7.0F, 7.0F);
        PDUtils.getByName(p.getName()).setKit("EZ");
        KitPvP.usedkit.add(p);
        return true;
    }

}