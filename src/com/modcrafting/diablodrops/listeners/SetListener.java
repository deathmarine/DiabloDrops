package com.modcrafting.diablodrops.listeners;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.effects.EffectsAPI;
import com.modcrafting.diablodrops.sets.ArmorSet;
import com.modcrafting.diablolibrary.events.DiabloLivingEntityDamageByEntityEvent;
import com.modcrafting.diablolibrary.events.DiabloLivingEntityDamageEvent;

public class SetListener implements Listener
{
    DiabloDrops plugin;

    public SetListener(final DiabloDrops instance)
    {
        plugin = instance;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDiabloLivingEntityDamageByEntityEvent(
            final DiabloLivingEntityDamageByEntityEvent event)
    {
        if (plugin.setsAPI.wearingSet(event.getDamagingEntity()))
        {
            String sName = plugin.setsAPI.getNameOfSet(event
                    .getDamagingEntity());
            ArmorSet aSet = plugin.setsAPI.getArmorSet(sName);
            if (aSet != null)
            {
                List<String> effects = aSet.getBonuses();
                for (String s : effects)
                {
                    EffectsAPI.addEffect(event.getDamagedEntity(),
                            event.getDamagingEntity(), s, event);
                }
            }
        }
        if (plugin.setsAPI.wearingSet(event.getDamagedEntity()))
        {
            String sName = plugin.setsAPI
                    .getNameOfSet(event.getDamagedEntity());
            ArmorSet aSet = plugin.setsAPI.getArmorSet(sName);
            if (aSet != null)
            {
                List<String> effects = aSet.getBonuses();
                for (String s : effects)
                {
                    EffectsAPI.addEffect(event.getDamagingEntity(),
                            event.getDamagedEntity(), s, event);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDiabloLivingEntityDamageEvent(
            final DiabloLivingEntityDamageEvent event)
    {
        if (plugin.setsAPI.wearingSet(event.getDiabloLivingEntity()))
        {
            String sName = plugin.setsAPI.getNameOfSet(event
                    .getDiabloLivingEntity());
            ArmorSet aSet = plugin.setsAPI.getArmorSet(sName);
            if (aSet != null)
            {
                List<String> effects = aSet.getBonuses();
                for (String s : effects)
                {
                    EffectsAPI.addEffect(event.getDiabloLivingEntity(), null,
                            s, event);
                }
            }
        }
    }
}
