package me.theremixpvp.ckitpvp.kit;

import me.theremixpvp.ckitpvp.User;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Florian Hergenhahn at 2020-03-18 <br>
 * Copyright © Flouet 2020
 *
 * @author Florian Hergenhahn
 */
public class AbilityManager {

    private static List<Ability> abilities = new ArrayList<>();

    public static void registerAbility(Ability ability) {
        abilities.add(ability);
    }

    public static List<Ability> getAbilitiesByKit(Kit kit) {
        return abilities.stream()
                .filter(ability -> ability.getKits().contains(kit))
                .collect(Collectors.toList());
    }

    public static <T extends Event> void triggerAdditionalAbilityEvents(Event event) {
        for (Ability ability : abilities) {
            for (Map.Entry<Class<? extends Event>, Ability.AbilityEventListener<? extends Event>> entry :
                    ability.getAdditionalEventListener().entrySet()) {
                if (entry.getKey().isAssignableFrom(event.getClass()))
                    entry.getValue().trigger(event);
            }
        }
    }

    public static void triggerAbilityEvents(Player player, Event event) {
        User user = User.byPlayer(player);
        if (user.getKit() == null)
            return;

        for (Ability ability : abilities) {
            if (ability.isApplicableAbilityEvent(user.getKit(), event))
                ability.activateAbility(player, user, event);
        }
    }

    public static List<Ability> getAbilities() {
        return abilities;
    }

    public static void registerAbilities(Ability... abilities) {
        getAbilities().addAll(Arrays.asList(abilities));
    }
}
