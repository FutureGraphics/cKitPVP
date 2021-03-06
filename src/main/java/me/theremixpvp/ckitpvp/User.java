package me.theremixpvp.ckitpvp;

import com.flouet.code.utilities.minecraft.api.inventory.InventoryMap;
import com.flouet.code.utilities.minecraft.api.player.EconomyProvider;
import com.flouet.code.utilities.minecraft.api.utilities.InventoryUtils;
import com.flouet.code.utilities.minecraft.api.utilities.ItemUtils;
import com.flouet.code.utilities.minecraft.api.utilities.PotionUtils;
import me.theremixpvp.ckitpvp.configuration.PlayerConfiguration;
import me.theremixpvp.ckitpvp.configuration.PluginConfiguration;
import me.theremixpvp.ckitpvp.kit.Kit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class User implements EconomyProvider {

    private static List<User> users = new ArrayList<>();

    private final String name;
    private final UUID uuid;
    private final PlayerConfiguration.PlayerInfo info;

    private int credits;
    private Kit kit;
    private int kills;
    private int deaths;
    private List<Kit> unlockedKits = new ArrayList<>();
    private InventoryMap inventory;

    public User(String name, UUID uuid, PlayerConfiguration.PlayerInfo info) {
        this.name = name;
        this.uuid = uuid;

        if (info == null)
            this.info = createInfo(name, uuid);
        else
            this.info = info;
    }

    public static User byPlayer(Player p) {
        return byUUID(p.getUniqueId());
    }

    public static User byUUID(UUID uuid) {
        return users.stream()
                .filter(user -> user.getUuid().equals(uuid))
                .findAny()
                .orElse(null);
    }

    public static void load(PlayerConfiguration configuration) {
        for (Map.Entry<String, PlayerConfiguration.PlayerInfo> entry : configuration.players.entrySet()) {
            PlayerConfiguration.PlayerInfo info = entry.getValue();

            User user = new User(info.name, UUID.fromString(entry.getKey()), info);
            user.setCredits(info.credits);
            user.setKills(info.kills);
            user.setDeaths(info.deaths);

            final List<String> kits = info.kits == null ? new ArrayList<>() : info.kits;

            user.setKits(
                    Kit.getKits()
                            .stream()
                            .filter(k -> kits.contains(k.getName()))
                            .collect(Collectors.toList())
            );

            users.add(user);
        }
    }

    public static void unload() {
        for (User user : users) {
            PlayerConfiguration.PlayerInfo info = user.getInfo();
            info.name = user.getName();
            info.deaths = user.getDeaths();
            info.kills = user.getKills();
            info.kits = user.getUnlockedKits().stream().map(Kit::getName).collect(Collectors.toList());
            info.credits = user.getCredits();
        }
    }

    public static User byName(String name) {
        return users.stream()
                .filter(user -> user.getName().equals(name))
                .findAny()
                .orElse(null);
    }

    public static void addUser(User user) {
        users.add(user);
    }

    private PlayerConfiguration.PlayerInfo createInfo(String name, UUID uuid) {
        PlayerConfiguration.PlayerInfo info = new PlayerConfiguration.PlayerInfo();
        info.kits = new ArrayList<>();
        info.credits = PluginConfiguration.startMoney;
        info.name = name;

        KitPvP.instance.addPlayer(uuid.toString(), info);

        return info;
    }

    public PlayerConfiguration.PlayerInfo getInfo() {
        return info;
    }

    public String getName() {
        return name;
    }

    private void setKits(List<Kit> kits) {
        this.unlockedKits = kits;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getCredits() {
        return this.credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getKills() {
        return this.kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public List<Kit> getUnlockedKits() {
        return this.unlockedKits;
    }

    public void addkit(Kit kit) {
        this.unlockedKits.add(kit);
    }

    public Kit getKit() {
        return this.kit;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
        giveKitItems();
        getPlayer().sendMessage("§dYou have selected the " + kit.getName() + " kit");
    }

    public void giveKitItems() {
        Player player = getPlayer();
        InventoryUtils.clearInventory(player.getInventory());
        PotionUtils.takeAllEffectsFromPlayer(player);

        for (ItemStack item : kit.getItems()) {
            Material m = item.getType();
            if (ItemUtils.isHelmet(m))
                player.getInventory().setHelmet(item);
            else if (ItemUtils.isChestplate(m))
                player.getInventory().setChestplate(item);
            else if (ItemUtils.isLeggings(m))
                player.getInventory().setLeggings(item);
            else if (ItemUtils.isBoots(m))
                player.getInventory().setBoots(item);
            else
                player.getInventory().addItem(item);
        }

        if(kit.getAbility() != null)
            kit.getAbility().onKitSelection(player, kit);
    }

    @Override
    public float getBalance() {
        return this.credits;
    }

    @Override
    public void setBalance(float amount) {
        this.credits = (int) amount;
    }

    @Override
    public void removeBalance(float amount) {
        this.credits -= amount;
    }

    @Override
    public void addBalance(float amount) {
        this.credits += amount;
    }

    public boolean kitUnlocked(String kitName) {
        return unlockedKits.stream().anyMatch(kit -> kit.getName().equalsIgnoreCase(kitName));
    }

    public InventoryMap getInventory() {
        return inventory;
    }

    public void setInventory(InventoryMap inventory) {
        this.inventory = inventory;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public void addDeath() {
        this.deaths++;
    }

    public void addKill() {
        this.kills++;
    }
}
