package me.theremixpvp.ckitpvp.configuration;

import com.flouet.code.base.configuration.ConfigField;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class ShopConfiguration {

    @ConfigField
    public Map<String, MenuConfig> menu;

    public static class MenuConfig {

        @ConfigField
        public ItemStack icon;

        @ConfigField
        public List<String> items;

    }

}
