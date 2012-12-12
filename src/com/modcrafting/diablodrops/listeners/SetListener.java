package com.modcrafting.diablodrops.listeners;

import java.util.List;

import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.effects.EffectsAPI;
import com.modcrafting.diablodrops.sets.ArmorSet;
import com.modcrafting.diablolibrary.entities.DiabloMonster;
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
    	if(event.getDamagingEntity() instanceof Monster)
    	{
            if (plugin.setsAPI.wearingSet((DiabloMonster) event.getDamagingEntity()))
            {
                String sName = plugin.setsAPI.getNameOfSet((DiabloMonster) event
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
    	}
    	else if(event.getDamagingEntity() instanceof Player)
    	{
            if (plugin.setsAPI.wearingSet((Player) event.getDamagingEntity()))
            {
                String sName = plugin.setsAPI.getNameOfSet((Player) event
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
    	}
    	if(event.getDamagedEntity() instanceof Monster)
    	{
            if (plugin.setsAPI.wearingSet((DiabloMonster) event.getDamagedEntity()))
            {
                String sName = plugin.setsAPI
                        .getNameOfSet((DiabloMonster) event.getDamagedEntity());
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
    	else if(event.getDamagedEntity() instanceof Player)
    	{
            if (plugin.setsAPI.wearingSet((Player) event.getDamagedEntity()))
            {
                String sName = plugin.setsAPI
                        .getNameOfSet((Player) event.getDamagedEntity());
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
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDiabloMonsterDamageEvent(
            final DiabloMonsterDamageEvent event)
    {
    	if(event.getLivingEntity() instanceof Monster)
    	{
            if (plugin.setsAPI.wearingSet((DiabloMonster) event.getLivingEntity()))
            {
                String sName = plugin.setsAPI.getNameOfSet((DiabloMonster) event
                        .getLivingEntity());
                ArmorSet aSet = plugin.setsAPI.getArmorSet(sName);
                if (aSet != null)
                {
                    List<String> effects = aSet.getBonuses();
                    for (String s : effects)
                    {
                        EffectsAPI.addEffect(event.getLivingEntity(), null,
                                s, event);
                    }
                }
            }
    		
    	}
    	else if(event.getLivingEntity() instanceof Player)
    	{
            if (plugin.setsAPI.wearingSet((Player) event.getLivingEntity()))
            {
                String sName = plugin.setsAPI.getNameOfSet((Player) event
                        .getLivingEntity());
                ArmorSet aSet = plugin.setsAPI.getArmorSet(sName);
                if (aSet != null)
                {
                    List<String> effects = aSet.getBonuses();
                    for (String s : effects)
                    {
                        EffectsAPI.addEffect(event.getLivingEntity(), null,
                                s, event);
                    }
                }
            }
    		
    	}
    }
}
