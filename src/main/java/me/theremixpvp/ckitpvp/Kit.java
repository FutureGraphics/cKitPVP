package me.theremixpvp.ckitpvp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Kit {

    // List with all loaded / available kits which are parsed by the configuration
    private static List<Kit> loadedKits = new ArrayList<>();

    // List with all active kits
    private static List<Kit> kits = new ArrayList<>();

    private final String name;

    public Kit(String name) {
        this.name = name;
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

}
