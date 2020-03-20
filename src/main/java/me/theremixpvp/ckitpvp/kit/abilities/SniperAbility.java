package me.theremixpvp.ckitpvp.kit.abilities;

import me.theremixpvp.ckitpvp.KitPvP;
import me.theremixpvp.ckitpvp.User;
import me.theremixpvp.ckitpvp.kit.Ability;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Random;

/**
 * Created by Florian Hergenhahn at 2020-03-20 <br>
 * Copyright © Flouet 2020
 *
 * @author Florian Hergenhahn
 */
public class SniperAbility extends Ability {

    private final Random random;

    public SniperAbility() {
        super("sniper", 0);
        addAdditionalEventListener(EntityDamageByEntityEvent.class, this::sniperBulletHit);
        addAdditionalEventListener(EntityDeathEvent.class, this::onEntityDeath);
        random = new Random();
    }

    private void onEntityDeath(EntityDeathEvent event) {
        if(event.getEntity().getKiller() == null || !(event.getEntity() instanceof Player))
            return;

        Player player = event.getEntity().getKiller();
        User user = User.byPlayer(player);
        if(user.getKit() == null || user.getKit().getAbility() == null
                || !(user.getKit().getAbility() instanceof SniperAbility)) {
            return;
        }

        player.getInventory().addItem(new ItemStack(Material.ARROW));
    }

    private void sniperBulletHit(EntityDamageByEntityEvent event) {
        if(!event.getDamager().hasMetadata("isSniperBullet"))
            return;

        event.setDamage(100);
    }

    @Override
    protected boolean canActivate(Event event) {
        return event instanceof EntityShootBowEvent;
    }

    @Override
    protected void activate(Player player, User user, Event event) {
        EntityShootBowEvent e = (EntityShootBowEvent) event;

        e.getProjectile().setMetadata("isSniperBullet", new FixedMetadataValue(KitPvP.instance, true));
    }
}
