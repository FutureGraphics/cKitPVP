package me.theremixpvp.ckitpvp;

import com.flouet.code.utilities.minecraft.api.exceptions.ParseItemException;
import com.flouet.code.utilities.minecraft.api.item.parse.ItemParser;
import me.theremixpvp.ckitpvp.configuration.KitConfiguration;
import org.apache.log4j.Logger;
import org.bukkit.inventory.ItemStack;

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
    private final int price;
    private final List<ItemStack> items;

    private boolean needPermission;

    public Kit(String name, ItemStack displayIcon, int price, List<ItemStack> items) {
        this.name = name;
        this.displayIcon = displayIcon;
        this.price = price;
        this.items = items;
    }

    public static void parseKits(KitConfiguration configuration) {
        List<String> activeKits = configuration.activeKits;

        for (Map.Entry<String, KitConfiguration.ConfigKit> entry : configuration.kits.entrySet()) {

            KitConfiguration.ConfigKit configKit = entry.getValue();

            String name = entry.getKey();
            ItemStack displayIcon = configKit.displayItem;
            int price = configKit.price;
            List<ItemStack> items = new ArrayList<>();

            for (String item : configKit.items) {
                try {
                    items.add(ItemParser.DEFAULT_PARSER.parse(item));
                } catch (ParseItemException e) {
                    KitPvP.LOGGER.log(Level.SEVERE, "Could not parse item '" + item + "' in kit: '" + name + "'");
                    continue;
                }
            }

            Kit kit = new Kit(name, displayIcon, price, items);
            loadedKits.add(kit);

            if(activeKits.contains(name))
                kits.add(kit);
        }

        KitPvP.LOGGER.log(Level.INFO, "Loaded " + activeKits.size() + " kits");
    }

    public static void unload(KitConfiguration configuration) {
        configuration.activeKits = kits.stream().map(Kit::getName).collect(Collectors.toList());
    }

    public static List<Kit> getKits() {
        return kits;
    }

    public static void activateKit(Kit kit) {
        if(kits.contains(kit))
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

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
