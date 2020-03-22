package me.theremixpvp.ckitpvp.kit;

import com.flouet.code.utilities.minecraft.api.exceptions.ParseItemException;
import com.flouet.code.utilities.minecraft.api.item.parse.ItemParser;
import me.theremixpvp.ckitpvp.KitPvP;
import me.theremixpvp.ckitpvp.User;
import me.theremixpvp.ckitpvp.configuration.KitConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Kit {
    // List with all loaded / available kits which are parsed by the configuration
    private static List<Kit> loadedKits = new ArrayList<>();

    // List with all active kits
    private static List<Kit> kits = new ArrayList<>();

    private final String name;
    private final ItemStack displayIcon;
    private final List<ItemStack> items;
    private final boolean needPermission;

    private Ability ability;

    public Kit(String name, ItemStack displayIcon, List<ItemStack> items, boolean needPermission) {
        this.name = name;
        this.displayIcon = displayIcon;
        this.items = items;
        this.needPermission = needPermission;
    }

    public static void parseKits(KitConfiguration configuration) {
        List<String> activeKits = configuration.activeKits;

        for (Map.Entry<String, KitConfiguration.ConfigKit> entry : configuration.kits.entrySet()) {

            KitConfiguration.ConfigKit configKit = entry.getValue();

            String name = entry.getKey();
            boolean needPermission = configKit.permission;
            ItemStack displayIcon = configKit.displayItem;


            if (displayIcon == null)
                KitPvP.LOGGER.log(Level.WARNING, "Display Item of '" + name + "' is null");

            List<ItemStack> items = new ArrayList<>();

            for (String item : configKit.items) {
                try {
                    items.add(ItemParser.DEFAULT_PARSER.parse(item));
                } catch (ParseItemException e) {
                    KitPvP.LOGGER.log(Level.SEVERE, "Could not parse item '" + item + "' in kit: '" + name + "'");
                }
            }

            Kit kit = new Kit(name, displayIcon, items, needPermission);
            if (configKit.ability != null) {
                Ability ability = AbilityManager.getAbilityByName(configKit.ability);
                if (ability == null) {
                    KitPvP.LOGGER.log(Level.WARNING, "Could not find ability '" + configKit.ability + "' at '" +
                            name + "'");
                } else {
                    kit.setAbility(ability);
                    ability.addKit(kit);
                    KitPvP.LOGGER.log(Level.INFO, "Ability '" + ability.getName() + "' added to '" + name + "'");
                }
            }

            loadedKits.add(kit);

            if (activeKits.contains(name))
                kits.add(kit);
        }

        KitPvP.LOGGER.log(Level.INFO, "Loaded " + activeKits.size() + " kits");
        KitPvP.LOGGER.log(Level.INFO, "Active Kits: " + kits.size());
    }

    public static void unload(KitConfiguration configuration) {
        configuration.activeKits = kits.stream().map(Kit::getName).collect(Collectors.toList());
    }

    public static List<Kit> getKits() {
        return kits;
    }

    public static void activateKit(Kit kit) {
        if (kits.contains(kit))
            return;

        kits.add(kit);
    }

    public static Kit byName(String name) {
        return byName(name, false);
    }

    public static Kit byName(String name, boolean ignore) {
        Stream<Kit> stream = ignore ? loadedKits.stream() : kits.stream();

        return stream
                .filter(kit -> kit.getName().equals(name))
                .findAny()
                .orElse(null);
    }

    public static void deactivateKit(Kit kit) {
        kits.remove(kit);
    }

    public boolean hasPlayerPermission(Player player) {
        if (!needPermission)
            return true;

        return player.hasPermission("ckitpvp.kit." + this.name);
    }

    public String getName() {
        return name;
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    public ItemStack getDisplayIcon() {
        return displayIcon;
    }

    public ItemStack getDisplayIcon(User user) {
        ItemStack stack = this.displayIcon.clone();
        ItemMeta meta = stack.getItemMeta();

        if (!meta.hasDisplayName())
            meta.setDisplayName(this.name);

        List<String> lore;
        if (meta.hasLore())
            lore = meta.getLore();
        else
            lore = new ArrayList<>();

        if (user.getUnlockedKits().contains(this))
            lore.add("§a§lUNLOCKED");
        else if (hasPlayerPermission(user.getPlayer()))
            lore.add("§cNOT PURCHASED");
        else
            lore.add("§cLOCKED");

        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    public List<ItemStack> getItems() {
        return items;
    }
}
