package me.theremixpvp.ckitpvp.configuration;

import com.flouet.code.base.configuration.ConfigField;

import java.util.List;
import java.util.Map;

public class PlayerConfiguration {

    @ConfigField
    public Map<String, PlayerInfo> players;

    public static class PlayerInfo {

        @ConfigField
        public List<String> kits;

        @ConfigField
        public int credits;

        @ConfigField
        public String name;

        @ConfigField
        public int kills;

        @ConfigField
        public int deaths;
    }

}
