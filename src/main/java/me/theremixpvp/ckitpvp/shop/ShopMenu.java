package me.theremixpvp.ckitpvp.shop;

import com.flouet.code.utilities.minecraft.api.inventory.InventoryMap;
import com.flouet.code.utilities.minecraft.api.inventory.Slot;
import com.flouet.code.utilities.minecraft.api.utilities.InventoryUtils;
import me.theremixpvp.ckitpvp.KitPvP;
import me.theremixpvp.ckitpvp.User;
import me.theremixpvp.ckitpvp.configuration.ShopConfiguration;
import me.theremixpvp.ckitpvp.exceptions.ShopParsingException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ShopMenu {

    private final String name;
    private final ItemStack icon;
    private final List<ShopItem> items;

    public ShopMenu(String name, ItemStack icon, List<ShopItem> items) {
        this.name = name;
        this.icon = icon;
        this.items = items;
    }

    public static ShopMenu parse(ShopConfiguration.MenuConfig config, String name) {

        List<ShopItem> items = new ArrayList<>();
        for (String item : config.items) {
            try {
                items.add(ShopItem.parse(item));
            } catch (ShopParsingException e) {
                KitPvP.LOGGER.log(Level.SEVERE, "Shop Item '" + item + "' in menu: '" + name +
                        "' is not valid", e);
            }
        }

        return new ShopMenu(name, config.icon, items);
    }

    public InventoryMap generateInventory(Player player) {

        Inventory inventory = Bukkit.createInventory(player, InventoryUtils.calculateInventorySize(items.size()),
                getTitle());

        InventoryMap map = new InventoryMap(inventory);

        for (int i = 0; i < items.size(); i++) {
            ShopItem item = items.get(i);

            map.addSlot(new Slot(i, item.createItem(player), item));
        }

        return map;
    }

    public void onClick(User user) {
        InventoryMap inventoryMap = generateInventory(user.getPlayer());
        inventoryMap.generateInventory();

        user.setInventory(inventoryMap);

        user.getPlayer().openInventory(inventoryMap.getInventory());
    }

    private String getTitle() {
        if(icon.hasItemMeta() && icon.getItemMeta().hasDisplayName())
            return icon.getItemMeta().getDisplayName();

        return name;
    }

    public String getName() {
        return name;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public List<ShopItem> getItems() {
        return items;
    }
}

