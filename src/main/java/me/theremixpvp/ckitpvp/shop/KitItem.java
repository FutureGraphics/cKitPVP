package me.theremixpvp.ckitpvp.shop;

import me.theremixpvp.ckitpvp.IClickable;
import me.theremixpvp.ckitpvp.User;
import me.theremixpvp.ckitpvp.kit.Kit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Created by Florian Hergenhahn at 2020-03-20 <br>
 * Copyright © Flouet 2020
 *
 * @author Florian Hergenhahn
 */
public class KitItem implements IClickable {

    private final Kit kit;

    public KitItem(Kit kit) {
        this.kit = kit;
    }

    @Override
    public void onClick(Player player, User user, Inventory inventory) {
        if (!user.getUnlockedKits().contains(kit)) {
            player.closeInventory();
            player.sendMessage("§cYou don't own this kit");
            return;
        }

        user.setKit(kit);

        player.closeInventory();
    }
}
