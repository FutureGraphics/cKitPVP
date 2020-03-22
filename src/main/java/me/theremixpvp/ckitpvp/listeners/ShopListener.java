package me.theremixpvp.ckitpvp.listeners;

import com.flouet.code.utilities.minecraft.api.inventory.Slot;
import me.theremixpvp.ckitpvp.IClickable;
import me.theremixpvp.ckitpvp.User;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by Florian Hergenhahn at 2020-03-18 <br>
 * Copyright © Flouet 2020
 *
 * @author Florian Hergenhahn
 */
public class ShopListener implements Listener {

    @EventHandler
    public void onShopClick(InventoryClickEvent event) {

        HumanEntity clicked = event.getWhoClicked();
        if (!(clicked instanceof Player))
            return;

        Player player = (Player) clicked;
        User user = User.byPlayer(player);

        if (user.getInventory() == null)
            return;

        if (!user.getInventory().getInventory().equals(event.getClickedInventory()))
            return;

        event.setCancelled(true);

        Slot slot = user.getInventory().findSlot(event.getRawSlot());

        if (slot == null)
            return;

        Object object = slot.getObject();
        if (object instanceof IClickable)
            ((IClickable) object).onClick(player, user, event.getClickedInventory());

    }

}
