package me.theremixpvp.ckitpvp;

import com.flouet.code.base.configuration.ConfigurationParser;
import com.flouet.code.base.configuration.yaml.YAMLConfigurationOption;
import com.flouet.code.base.configuration.yaml.YAMLConfigurationProvider;
import com.flouet.code.base.exceptions.ConfigurationSaveException;
import me.theremixpvp.ckitpvp.commands.*;
import me.theremixpvp.ckitpvp.commands.kits.*;
import me.theremixpvp.ckitpvp.configuration.KitConfiguration;
import me.theremixpvp.ckitpvp.configuration.PlayerConfiguration;
import me.theremixpvp.ckitpvp.configuration.PluginConfiguration;
import me.theremixpvp.ckitpvp.listeners.*;
import me.theremixpvp.ckitpvp.listeners.kits.*;
import me.theremixpvp.ckitpvp.commands.PlayerDataCommand;
import me.theremixpvp.ckitpvp.shop.ShopCmd;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KitPvP extends JavaPlugin {

    public static final Logger LOGGER = Logger.getLogger("Minecraft");
    public static KitPvP instance;
    public static PlayerConfiguration playerConfig;
    public static KitConfiguration kitConfiguration;
    private static ConfigurationParser<PlayerConfiguration> playerConfigParser;
    final PluginManager pm = Bukkit.getServer().getPluginManager();

    public void onEnable() {
        instance = this;

        loadConfig();
        executors();
        listeners();

    }

    public void onDisable() {
        saveConfig();
    }

    public void executors() {
        getCommand("pvp").setExecutor(new Kit_PVP(this));
        getCommand("archer").setExecutor(new Kit_Archer(this));
        getCommand("tank").setExecutor(new Kit_Tank(this));
        getCommand("fisherman").setExecutor(new Kit_Fisherman(this));
        getCommand("grappler").setExecutor(new Kit_Grappler(this));
        getCommand("hulk").setExecutor(new Kit_Hulk(this));
        getCommand("dodge").setExecutor(new Kit_Dodge(this));
        getCommand("lucky").setExecutor(new Kit_Lucky(this));
        getCommand("ez").setExecutor(new Kit_EZ(this));
        getCommand("rusher").setExecutor(new Kit_Rusher(this));
        getCommand("sniper").setExecutor(new Kit_Sniper(this));
        getCommand("visionmaster").setExecutor(new Kit_VisionMaster(this));
        getCommand("acrobat").setExecutor(new Kit_Acrobat(this));
        getCommand("ninja").setExecutor(new Kit_Ninja(this));
        getCommand("bomber").setExecutor(new Kit_Bomber(this));
        getCommand("stats").setExecutor(new StatCommand(this));
        getCommand("credits").setExecutor(new CreditCommand(this));
        getCommand("kits").setExecutor(new KitsCommand(this));
        getCommand("kit").setExecutor(new KitCommand());
        getCommand("bank").setExecutor(new Bank(this));
        getCommand("soup").setExecutor(new SoupCommand());
        getCommand("test").setExecutor(new Test(this));
        getCommand("hat").setExecutor(new HatCommand());
        getCommand("more").setExecutor(new MoreCommand());
        getCommand("shop").setExecutor(new ShopCmd());
        getCommand("playerdata").setExecutor(new PlayerDataCommand());
        //getCommand("ss").setExecutor(new SuperSoup(this));
    }

    public void listeners() {
        pm.registerEvents(new DeathListener(this), this);
        pm.registerEvents(new JoinListener(this), this);
        pm.registerEvents(new SoupListener(this), this);
        pm.registerEvents(new Test(this), this);
        pm.registerEvents(new PlayerChat(), this);
        pm.registerEvents(new PlayerListener(this), this);
        //pm.registerEvents(ShopMenu, this);


        pm.registerEvents(new FishermanL(this), this);
        pm.registerEvents(new GrapplerL(this), this);
        pm.registerEvents(new HulkL(this), this);
        pm.registerEvents(new DodgeL(this), this);
        pm.registerEvents(new LuckyL(this), this);
        pm.registerEvents(new RusherL(this), this);
        pm.registerEvents(new SniperL(this), this);
        pm.registerEvents(new VisionMasterL(this), this);
        pm.registerEvents(new AcrobatL(this), this);
        pm.registerEvents(new NinjaL(this), this);
        pm.registerEvents(new BomberL(this), this);
    }

    public void loadConfig() {
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        File config = new File(getDataFolder(), "config.yml");
        File players = new File(getDataFolder(), "players.yml");
        File kits = new File(getDataFolder(), "kits.yml");

        copyFile(config);
        copyFile(players);
        copyFile(kits);

        playerConfigParser = new ConfigurationParser<>(new YAMLConfigurationProvider(), PlayerConfiguration.class);
        playerConfig = playerConfigParser.load(new YAMLConfigurationOption(players));

        kitConfiguration = new ConfigurationParser<>(new YAMLConfigurationProvider(), KitConfiguration.class)
                .load(new YAMLConfigurationOption(kits));

        new ConfigurationParser<>(new YAMLConfigurationProvider(), PluginConfiguration.class)
                .setMakeInstance(false)
                .load(new YAMLConfigurationOption(config));

    }

    private void copyFile(File file) {

        if (!file.exists()) {
            LOGGER.log(Level.INFO, file.getName() + " does not exist, trying to copy from resources");
            try {
                Files.copy(getResource(file.getName() + ".yml"), file.toPath());
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Could not copy " + file.getName() + " file into data folder", e);
            }
        }
    }

    public void saveConfig() {
        try {
            playerConfigParser.save();
        } catch (ConfigurationSaveException e) {
            LOGGER.log(Level.SEVERE, "Could not save player configuration", e);
            return;
        }

        LOGGER.log(Level.INFO, "Player configuration saved");
    }
}
