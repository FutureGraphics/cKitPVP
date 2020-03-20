package me.theremixpvp.ckitpvp;

import com.flouet.code.base.configuration.ConfigurationParser;
import com.flouet.code.base.configuration.yaml.YAMLConfigurationOption;
import com.flouet.code.base.configuration.yaml.YAMLConfigurationProvider;
import com.flouet.code.base.exceptions.ConfigurationSaveException;
import com.flouet.code.utilities.minecraft.api.configuration.PluginConfigurationParser;
import me.theremixpvp.ckitpvp.commands.*;
import me.theremixpvp.ckitpvp.configuration.KitConfiguration;
import me.theremixpvp.ckitpvp.configuration.PlayerConfiguration;
import me.theremixpvp.ckitpvp.configuration.PluginConfiguration;
import me.theremixpvp.ckitpvp.configuration.ShopConfiguration;
import me.theremixpvp.ckitpvp.kit.AbilityManager;
import me.theremixpvp.ckitpvp.kit.Kit;
import me.theremixpvp.ckitpvp.kit.abilities.*;
import me.theremixpvp.ckitpvp.listeners.*;
import me.theremixpvp.ckitpvp.shop.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KitPvP extends JavaPlugin {

    public static final Logger LOGGER = Logger.getLogger("cKitPvP");
    public static KitPvP instance;
    public static PlayerConfiguration playerConfig;
    public static KitConfiguration kitConfiguration;
    public static ShopConfiguration shopConfiguration;
    public static MenuManager menuManager;

    private static PluginConfigurationParser<KitConfiguration> kitConfigParser;
    private static ConfigurationParser<PlayerConfiguration> playerConfigParser;

    public void onEnable() {
        instance = this;

        loadConfig();

        Kit.parseKits(kitConfiguration);
        User.load(playerConfig);
        menuManager = MenuManager.parse(shopConfiguration);

        registerAbilities();
        executors();
        listeners();
    }

    private void registerAbilities() {
        AbilityManager.registerAbilities(
                new AcrobatAbility(),
                new BomberAbility(),
                new DodgeAbility(),
                new FishermanAbility(),
                new GrapplerAbility(),
                new HulkAbility(),
                new LuckyAbility(),
                new NinjaAbility(),
                new RusherAbility(),
                new SniperAbility(),
                new VisionMasterAbility());
    }

    public void onDisable() {
        saveConfig();
    }

    public void executors() {
        getCommand("stats").setExecutor(new StatCommand(this));
        getCommand("credits").setExecutor(new CreditCommand());
        getCommand("kits").setExecutor(new KitsCommand());
        getCommand("kit").setExecutor(new KitCommand());
        getCommand("bank").setExecutor(new BankCommand());
        getCommand("soup").setExecutor(new SoupCommand());
        getCommand("hat").setExecutor(new HatCommand());
        getCommand("more").setExecutor(new MoreCommand());
        getCommand("shop").setExecutor(new ShopCommand());
        getCommand("playerdata").setExecutor(new PlayerDataCommand());
    }

    private void listeners() {
        final PluginManager pm = Bukkit.getServer().getPluginManager();

        pm.registerEvents(new ShopListener(), this);
        pm.registerEvents(new DeathListener(), this);
        pm.registerEvents(new JoinListener(), this);
        pm.registerEvents(new SoupListener(), this);
        pm.registerEvents(new PlayerListener(), this);
        pm.registerEvents(new AbilityListener(), this);
    }

    public void loadConfig() {
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        File config = new File(getDataFolder(), "config.yml");
        File players = new File(getDataFolder(), "players.yml");
        File kits = new File(getDataFolder(), "kits.yml");
        File shop = new File(getDataFolder(), "shop.yml");

        copyFile(config);
        copyFile(players);
        copyFile(kits);
        copyFile(shop);

        playerConfigParser = new ConfigurationParser<>(new YAMLConfigurationProvider(), PlayerConfiguration.class);
        playerConfig = playerConfigParser.load(new YAMLConfigurationOption(players));

        kitConfigParser = new PluginConfigurationParser<>(new YAMLConfigurationProvider(), KitConfiguration.class);
        kitConfiguration = kitConfigParser.load(new YAMLConfigurationOption(kits));

        shopConfiguration = new PluginConfigurationParser<>(new YAMLConfigurationProvider(), ShopConfiguration.class)
                .load(new YAMLConfigurationOption(shop));

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
            User.unload();
            Kit.unload(kitConfiguration);

            playerConfigParser.save();
            kitConfigParser.save();
        } catch (ConfigurationSaveException e) {
            LOGGER.log(Level.SEVERE, "Could not save player configurations", e);
            return;
        }

        LOGGER.log(Level.INFO, "Configurations saved");
    }
}
