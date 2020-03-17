package me.theremixpvp.ckitpvp;

import me.theremixpvp.ckitpvp.configuration.PlayerConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class User {

    private static List<User> users = new ArrayList<>();

    private final String name;
    private final UUID uuid;

    private int credits;
    private Kit kit;
    private int kills;
    private int deaths;
    private List<String> unlockedKits = new ArrayList<>();
    private Inventory bank;

    public User(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
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

            User user = new User(info.name, UUID.fromString(entry.getKey()));
            user.setCredits(info.credits);
            user.setKills(info.kills);
            user.setDeaths(info.deaths);
            user.setKits(info.kits);

            users.add(user);
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

    public String getName() {
        return name;
    }

    private void setKits(List<String> kits) {
        this.unlockedKits = kits;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int credits() {
        return this.credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int kills() {
        return this.kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int deaths() {
        return this.deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public List<String> getUnlockedKits() {
        return this.unlockedKits;
    }

    public void addkit(String kit) {
        this.unlockedKits.add(kit);
    }

    public Kit getKit() {
        return this.kit;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public Inventory getBank() {
        return this.bank;
    }

    public void setBank(Inventory bank) {
        this.bank = bank;
    }

}
