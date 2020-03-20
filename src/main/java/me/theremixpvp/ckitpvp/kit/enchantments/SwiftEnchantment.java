package me.theremixpvp.ckitpvp.kit.enchantments;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Florian Hergenhahn at 2020-03-20 <br>
 * Copyright © Flouet 2020
 *
 * @author Florian Hergenhahn
 */
public class SwiftEnchantment extends Enchantment {

    public SwiftEnchantment(int id) {
        super(id);
    }

    @Override
    public String getName() {
        return "Swift";
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ALL;
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return true;
    }
}
