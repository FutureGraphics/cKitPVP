package me.theremixpvp.ckitpvp.configuration;

import com.flouet.code.base.configuration.ConfigField;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class KitConfiguration {

    @ConfigField(name = "active_kits")
    public List<String> activeKits;

    @ConfigField
    public Map<String, ConfigKit> kits;

    public static class ConfigKit {

        @ConfigField
        public boolean permission;

        @ConfigField
        public List<String> items;

        @ConfigField
        public int price;

        @ConfigField(name = "display_item")
        public ItemStack displayItem;

    }

}
