package me.theremixpvp.ckitpvp.commands;

import me.theremixpvp.ckitpvp.Kit;
import me.theremixpvp.ckitpvp.User;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class KitsCommand implements CommandExecutor {

    private List<String> defkits = Arrays.asList("PvP", "Archer");

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 0) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Only players can use this command!");
                return true;
            }

            Player p = (Player) sender;
            User user = User.byPlayer(p);

            if (user == null) {
                sender.sendMessage(ChatColor.RED + "Player Data not found, report this to an administrator");
                return true;
            }

            StringBuilder kits = new StringBuilder();

            for (Kit kit : Kit.getKits()) {
                if (p.isOp() || p.hasPermission("ckitpvp.kit." + kit)
                        || user.getUnlockedKits().contains(kit)) {
                    kits.append(ChatColor.GREEN)
                            .append("-" + kit.getName() + ChatColor.GRAY + "\n" + ChatColor.RESET);
                    continue;
                }

                kits.append("-" + ChatColor.RED + kit.getName() + ChatColor.GRAY + "\n" + ChatColor.RESET);

            }

            p.sendMessage(kits.toString());

            return true;
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {
                if (!sender.hasPermission("ckitpvp.kits.add")) {
                    return true;
                }

                String k = args[0];

                Kit kit = Kit.byName(k, true);
                if(kit == null) {
                    sender.sendMessage(ChatColor.RED + "Kit could not be found");
                    return true;
                }

                Kit.activateKit(kit);
                sender.sendMessage(ChatColor.AQUA + "Kit imported!");
                return true;
            }

            if (args[0].equalsIgnoreCase("remove")) {
                if (!sender.hasPermission("ckitpvp.kits.remove")) {
                    return true;
                }

                Kit kit = Kit.byName(args[0]);
                if(kit == null) {
                    sender.sendMessage(ChatColor.RED + "Kit could not be found");
                    return true;
                }

                Kit.deactivateKit(kit);

                sender.sendMessage(ChatColor.AQUA + "Kit removed!");
                return true;
            }

        }

        return false;
    }

}
