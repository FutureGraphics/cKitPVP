package me.theremixpvp.ckitpvp.kit;

import com.sun.xml.internal.bind.v2.util.CollisionCheckStack;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Florian Hergenhahn at 2020-03-18 <br>
 * Copyright © Flouet 2020
 *
 * @author Florian Hergenhahn
 */
public class AbilityManager {

    private static List<Ability> abilities = new ArrayList<>();

    public static List<Ability> getAbilitiesByKit(Kit kit) {
        return abilities.stream()
                .filter(ability -> ability.canActivate(kit))
                .collect(Collectors.toList());
    }

    public static List<Ability> getAbilities() {
        return abilities;
    }
}
