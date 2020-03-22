package me.theremixpvp.ckitpvp.shop;

import com.flouet.code.utilities.minecraft.api.inventory.InventoryMap;
import com.flouet.code.utilities.minecraft.api.inventory.Slot;
import com.flouet.code.utilities.minecraft.api.utilities.InventoryUtils;
import me.theremixpvp.ckitpvp.configuration.ShopConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuManager {

    private final List<ShopMenu> shopMenus;

    public MenuManager(List<ShopMenu> shopMenus) {
        this.shopMenus = shopMenus;
    }

    public static MenuManager parse(ShopConfiguration configuration) {

        List<ShopMenu> menus = new ArrayList<>();

        for (Map.Entry<String, ShopConfiguration.MenuConfig> entry : configuration.menu.entrySet()) {
            menus.add(ShopMenu.parse(entry.getValue(), entry.getKey()));
        }

        return new MenuManager(menus);
    }

    public InventoryMap createInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player, InventoryUtils.calculateInventorySize(shopMenus.size()),
                "Shop");

        InventoryMap map = new InventoryMap(inventory);

        for (int i = 0; i < shopMenus.size(); i++) {
            ShopMenu shopMenu = shopMenus.get(i);
            map.addSlot(new Slot(i, shopMenu.getIcon(), shopMenu));
        }

        return map;
    }
}
