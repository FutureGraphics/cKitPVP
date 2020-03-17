package me.theremixpvp.ckitpvp.configuration;

import com.flouet.code.base.configuration.ConfigField;

import java.util.List;

public class KitConfiguration {

    @ConfigField(name = "active_kits")
    public List<String> activeKits;

    @ConfigField
    public List<ConfigKit> kits;

    public static class ConfigKit {

        @ConfigField
        public String permission;

        @ConfigField
        public List<String> items;

        @ConfigField
        public int price;

    }

}
