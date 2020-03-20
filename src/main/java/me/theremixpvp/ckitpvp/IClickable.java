package me.theremixpvp.ckitpvp;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Created by Florian Hergenhahn at 2020-03-20 <br>
 * Copyright © Flouet 2020
 *
 * @author Florian Hergenhahn
 */
public interface IClickable {

    void onClick(Player player, User user, Inventory inventory);

}
