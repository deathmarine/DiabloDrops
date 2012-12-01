package com.modcrafting.diablodrops.listeners;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.effects.EffectsAPI;
import com.modcrafting.diablodrops.sets.ArmorSet;

public class SetListener implements Listener
{
    DiabloDrops plugin;

    public SetListener(DiabloDrops instance)
    {
        plugin = instance;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDamageByEntity(EntityDamageEvent event)
    {
        if (event instanceof EntityDamageByEntityEvent)
        {
            EntityDamageByEntityEvent ev = (EntityDamageByEntityEvent) event;

            Entity struckEntity = ev.getEntity();
            Entity strikerEntity = ev.getDamager();
            if (!(struckEntity instanceof LivingEntity))
                return;
            LivingEntity struck = (LivingEntity) struckEntity;
            if (strikerEntity instanceof Player)
            {
                Player striker = (Player) strikerEntity;
                if (plugin.setsAPI.wearingSet(striker))
                {
                	String sName = plugin.setsAPI.getNameOfSet(striker);
                	ArmorSet aSet = plugin.setsAPI.getArmorSet(sName);
                    List<String> effects = aSet.getBonuses();
                    for (String s : effects)
                        EffectsAPI.addEffect(struck, striker, s, event);
                }
            }
            if (strikerEntity instanceof Projectile
                    && ((Projectile) strikerEntity).getShooter() instanceof Player)
            {
                Player shooter = (Player) ((Projectile) strikerEntity)
                        .getShooter();
                if (plugin.setsAPI.wearingSet(shooter))
                {
                	String sName = plugin.setsAPI.getNameOfSet(shooter);
                	ArmorSet aSet = plugin.setsAPI.getArmorSet(sName);
                    List<String> effects = aSet.getBonuses();
                    for (String s : effects)
                        EffectsAPI.addEffect(struck, shooter, s, event);
                }
            }
        }
        else
        {
            if (event.getEntity() instanceof Player
                    && plugin.setsAPI.wearingSet((Player) event.getEntity()))
            {

            	String sName = plugin.setsAPI.getNameOfSet((Player) event.getEntity());
            	ArmorSet aSet = plugin.setsAPI.getArmorSet(sName);
                List<String> effects = aSet.getBonuses();
                for (String s : effects)
                    EffectsAPI.addEffect((LivingEntity) event.getEntity(),
                            null, s, event);
            }
        }
    }
}
