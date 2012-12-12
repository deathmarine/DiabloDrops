package com.modcrafting.diablodrops.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.effects.EffectsAPI;
import com.modcrafting.diablolibrary.entities.DiabloLivingEntity;
import com.modcrafting.diablolibrary.events.DiabloLivingEntityDamageByEntityEvent;
import com.modcrafting.diablolibrary.events.DiabloLivingEntityDamageEvent;

public class EffectsListener implements Listener
{
    DiabloDrops plugin;

    public EffectsListener(final DiabloDrops instance)
    {
        plugin = instance;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDiabloLivingEntityDamageByEntityEvent(
            final DiabloLivingEntityDamageByEntityEvent event)
    {

        if ((plugin.worlds.size() > 0)
                && plugin.config.getBoolean("Worlds.Enabled", false)
                && !plugin.worlds.contains(event.getDamagedEntity()
                        .getLocation().getWorld().getName().toLowerCase()))
            return;
        DiabloLivingEntity damaged = event.getDamagedEntity();
        DiabloLivingEntity damager = event.getDamagingEntity();
        if (damaged.getWorld() != damager.getWorld())
            return;
        EffectsAPI.handlePluginEffects(damaged, damager, event);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDiabloLivingEntityDamageEvent(
            final DiabloLivingEntityDamageEvent event)
    {
        if ((plugin.worlds.size() > 0)
                && plugin.config.getBoolean("Worlds.Enabled", false)
                && !plugin.worlds.contains(event.getDiabloLivingEntity()
                        .getLocation().getWorld().getName().toLowerCase()))
            return;
        if (event.getDiabloLivingEntity() instanceof Player)
        {
            EffectsAPI.handlePluginEffects(event.getDiabloLivingEntity(), null,
                    event);
        }
    }
}
