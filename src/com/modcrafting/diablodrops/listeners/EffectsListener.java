package com.modcrafting.diablodrops.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.effects.EffectsAPI;

public class EffectsListener implements Listener
{
	DiabloDrops plugin;

	public EffectsListener(DiabloDrops instance)
	{
		plugin = instance;
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
	{
		Entity struckEntity = event.getEntity();
		Entity strikerEntity = event.getDamager();
		if (!(struckEntity instanceof LivingEntity))
			return;
		LivingEntity struck = (LivingEntity) struckEntity;
		if (strikerEntity instanceof LivingEntity)
			EffectsAPI.handlePluginEffects(struck,
					(LivingEntity) strikerEntity, event);
		if (strikerEntity instanceof Projectile)
			EffectsAPI.handlePluginEffects(struck,
					((Projectile) strikerEntity).getShooter(), event);
	}
}
