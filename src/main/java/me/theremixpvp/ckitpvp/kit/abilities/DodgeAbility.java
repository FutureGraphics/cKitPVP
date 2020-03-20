package me.theremixpvp.ckitpvp.kit.abilities;

import me.theremixpvp.ckitpvp.User;
import me.theremixpvp.ckitpvp.kit.Ability;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.projectiles.ProjectileSource;

import java.util.List;

/**
 * Created by Florian Hergenhahn at 2020-03-18 <br>
 * Copyright © Flouet 2020
 *
 * @author Florian Hergenhahn
 */
public class DodgeAbility extends Ability {

    public DodgeAbility() {
        super("dodge", 0);
        addAdditionalEventListener(ProjectileHitEvent.class, this::projectiveHitEvent);
    }

    private void projectiveHitEvent(ProjectileHitEvent event) {
        Location location = event.getEntity().getLocation();
        List<Entity> nbe = event.getEntity().getNearbyEntities(location.getX(), location.getY(), location.getZ());

        for (Entity entity : nbe) {
            ProjectileSource shooter = event.getEntity().getShooter();
            if (!(entity instanceof Player) || !(shooter instanceof Entity))
                continue;

            Player player = (Player) entity;
            User user = User.byPlayer(player);

            if (user.getKit() == null || user.getKit().getAbility() == null ||
                    !(user.getKit().getAbility() instanceof DodgeAbility)) {
                continue;
            }

            Arrow a = player.launchProjectile(Arrow.class);
            a.setShooter(player);

            a.setVelocity(((Entity) shooter).getLocation().toVector());
        }
    }

    @Override
    protected boolean canActivate(Event event) {
        if (!(event instanceof EntityDamageByEntityEvent))
            return false;

        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        return e.getCause() == EntityDamageByEntityEvent.DamageCause.PROJECTILE
                && e.getEntity() instanceof Player;
    }

    @Override
    protected void activate(Player player, User user, Event event) {
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        e.setCancelled(true);

        Arrow arrow = player.launchProjectile(Arrow.class);
        arrow.setShooter(player);
        arrow.setVelocity(e.getDamager().getVelocity().multiply(-1));
    }
}
