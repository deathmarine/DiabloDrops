package com.modcrafting.diablodrops.listeners;

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

public class EffectsListener implements Listener
{
	DiabloDrops plugin;

	public EffectsListener(DiabloDrops instance)
	{
		plugin = instance;
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onEntityDamageByEntity(EntityDamageEvent event)
	{
		if (plugin.worlds.size()>0
				&& plugin.config.getBoolean("Worlds.Enabled", false)
				&& !plugin.worlds.contains(event.getEntity().getLocation().getWorld()
						.getName().toLowerCase()))
			return;
		if(event instanceof EntityDamageByEntityEvent){
			EntityDamageByEntityEvent ev=(EntityDamageByEntityEvent) event;

			Entity struckEntity = ev.getEntity();
			Entity strikerEntity = ev.getDamager();
			if (!(struckEntity instanceof LivingEntity))
				return;
			LivingEntity struck = (LivingEntity) struckEntity;
			if (strikerEntity instanceof LivingEntity)
				EffectsAPI.handlePluginEffects(struck,
						(LivingEntity) strikerEntity, ev);
			if (strikerEntity instanceof Projectile)
				EffectsAPI.handlePluginEffects(struck,
						((Projectile) strikerEntity).getShooter(), ev);
		}else{
			if(event.getEntity() instanceof Player){
				EffectsAPI.handlePluginEffects((LivingEntity) event.getEntity(), null, event);
			}
		}
	}
}
