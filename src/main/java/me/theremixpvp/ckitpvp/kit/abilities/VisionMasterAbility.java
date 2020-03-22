package me.theremixpvp.ckitpvp.kit.abilities;

import me.theremixpvp.ckitpvp.KitPvP;
import me.theremixpvp.ckitpvp.User;
import me.theremixpvp.ckitpvp.kit.Ability;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Florian Hergenhahn at 2020-03-20 <br>
 * Copyright © Flouet 2020
 *
 * @author Florian Hergenhahn
 */
public class VisionMasterAbility extends Ability {

    public VisionMasterAbility() {
        super("vision_master", 3);
        addAdditionalEventListener(EntityDamageByEntityEvent.class, this::onArrowHit);
    }

    private void onArrowHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Arrow) || event.getDamager().hasMetadata("isVisionArrow")
                || !(event.getEntity() instanceof LivingEntity))
            return;

        LivingEntity entity = (LivingEntity) event.getEntity();

        entity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 160, 2));
    }


    @Override
    protected boolean canActivate(Event event) {
        if (!(event instanceof PlayerInteractEvent))
            return false;

        PlayerInteractEvent e = (PlayerInteractEvent) event;

        return e.getItem().getType() == Material.NETHER_STAR;
    }

    @Override
    protected void activate(Player player, User user, Event event) {
        Arrow arrow = player.launchProjectile(Arrow.class);
        arrow.setMetadata("isVisionArrow",
                new FixedMetadataValue(KitPvP.instance, true));
    }
}
