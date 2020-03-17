package me.theremixpvp.ckitpvp.configuration;

import com.flouet.code.base.configuration.ConfigField;

public class PluginConfiguration {

    @ConfigField(name = "death_message")
    public static boolean deathMessage = true;

    @ConfigField(name = "show_credits")
    public static boolean showCredits = true;

    @ConfigField(name = "soup_heal")
    public static int soupHeal = 7;

    @ConfigField(name = "cool_down")
    public static CoolDown coolDown;

    public static class CoolDown {

        @ConfigField(name = "soup_command")
        public int soupCommand = 45;

    }

}
