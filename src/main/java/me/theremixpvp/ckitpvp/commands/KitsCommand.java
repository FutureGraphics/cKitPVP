package me.theremixpvp.ckitpvp.commands;

import com.flouet.code.utilities.minecraft.api.color.BlockColor;
import com.flouet.code.utilities.minecraft.api.inventory.InventoryMap;
import com.flouet.code.utilities.minecraft.api.inventory.Slot;
import com.flouet.code.utilities.minecraft.api.utilities.InventoryUtils;
import com.flouet.code.utilities.minecraft.api.utilities.ItemUtils;
import me.theremixpvp.ckitpvp.kit.Kit;
import me.theremixpvp.ckitpvp.User;
import me.theremixpvp.ckitpvp.shop.KitItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class KitsCommand implements CommandExecutor {

    private List<String> defkits = Arrays.asList("PvP", "Archer");

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 0) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Only players can use this command!");
                return true;
            }

            Player player = (Player) sender;
            User user = User.byPlayer(player);

            List<Kit> ownedKits = user.getUnlockedKits();
            List<Kit> kits = Kit.getKits().stream()
                    .filter(kit -> !ownedKits.contains(kit))
                    .collect(Collectors.toList());

            int size = (ownedKits.size() > 0 ? 9 : 0) + kits.size() + ownedKits.size();

            Inventory inventory = Bukkit.createInventory(player, InventoryUtils.calculateInventorySize(size), "Kits");
            InventoryMap map = new InventoryMap(inventory);
            int index = 0;
            for (Kit ownedKit : ownedKits) {
                map.addSlot(new Slot(index, ownedKit.getDisplayIcon(user), new KitItem(ownedKit)));
                index++;
            }

            if (ownedKits.size() > 0) {

                ItemStack row = ItemUtils.createItem(Material.STAINED_GLASS_PANE, 1,
                        BlockColor.BLACK.getId(), "");

                index = InventoryUtils.calculateInventorySize(ownedKits.size());
                for (int i = 0; i < 9; i++) {
                    map.addSlot(new Slot(index, row.clone()));
                    index++;
                }
            }

            for (Kit kit : kits) {
                map.addSlot(new Slot(index, kit.getDisplayIcon(user), new KitItem(kit)));
            }

            map.generateInventory();
            user.setInventory(map);

            player.openInventory(map.getInventory());
            return true;
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {
                if (!sender.hasPermission("ckitpvp.kits.add")) {
                    return true;
                }

                String k = args[0];

                Kit kit = Kit.byName(k, true);
                if (kit == null) {
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
                if (kit == null) {
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
