package me.theremixpvp.ckitpvp.commands.kits;

import me.theremixpvp.ckitpvp.KitPvP;
import me.theremixpvp.ckitpvp.User;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

public class template implements CommandExecutor {

    KitPvP kitPvP;

    public template(KitPvP plugin) {
        plugin = kitPvP;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this commands!");
            return true;
        }
        User pd = PDUtils.getByName(sender.getName());
        if (!(pd.getUnlockedKits().contains("KITNAME"))) {
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

        return false;
    }

}
