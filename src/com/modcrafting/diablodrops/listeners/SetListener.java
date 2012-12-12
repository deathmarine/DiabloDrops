package com.modcrafting.diablodrops.listeners;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.effects.EffectsAPI;
import com.modcrafting.diablodrops.sets.ArmorSet;
import com.modcrafting.diablolibrary.events.DiabloMonsterDamageByEntityEvent;
import com.modcrafting.diablolibrary.events.DiabloMonsterDamageEvent;

public class SetListener implements Listener
{
    DiabloDrops plugin;

    public SetListener(final DiabloDrops instance)
    {
        plugin = instance;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDiabloMonsterDamageByEntityEvent(
            final DiabloMonsterDamageByEntityEvent event)
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
    public void onDiabloMonsterDamageEvent(
            final DiabloMonsterDamageEvent event)
    {
        if (plugin.setsAPI.wearingSet(event.getDiabloMonster()))
        {
            String sName = plugin.setsAPI.getNameOfSet(event
                    .getDiabloMonster());
            ArmorSet aSet = plugin.setsAPI.getArmorSet(sName);
            if (aSet != null)
            {
                List<String> effects = aSet.getBonuses();
                for (String s : effects)
                {
                    EffectsAPI.addEffect(event.getDiabloMonster(), null,
                            s, event);
                }
            }
        }
    }
}
