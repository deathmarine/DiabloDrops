package us.deathmarine.diablodrops.listeners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import us.deathmarine.diablodrops.DiabloDrops;
import us.deathmarine.diablodrops.effects.EffectsAPI;

public class EffectsListener implements Listener {
	DiabloDrops plugin;

	public EffectsListener(final DiabloDrops instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onDiabloMonsterDamageByEntityEvent(
			final EntityDamageByEntityEvent event) {
		if ((plugin.worlds.size() > 0)
				&& plugin.getConfig().getBoolean("Worlds.Enabled", false)
				&& !plugin.worlds.contains(event.getEntity().getLocation()
						.getWorld().getName().toLowerCase()))
			return;
		if (!(event.getEntity() instanceof LivingEntity))
			return;
		LivingEntity entity = (LivingEntity) event.getEntity();
		LivingEntity damager = null;
		if (event.getDamager() instanceof LivingEntity) {
			damager = (LivingEntity) event.getDamager();
		}
		if ((entity == null) || (damager == null))
			return;
		if (entity.getWorld() != damager.getWorld())
			return;
		EffectsAPI.handlePluginEffects(entity, damager, event);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onDiabloMonsterDamageEvent(final EntityDamageEvent event) {
		if ((plugin.worlds.size() > 0)
				&& plugin.getConfig().getBoolean("Worlds.Enabled", false)
				&& !plugin.worlds.contains(event.getEntity().getLocation()
						.getWorld().getName().toLowerCase()))
			return;
		if (event.getEntity() instanceof Player) {
			EffectsAPI.handlePluginEffects((LivingEntity) event.getEntity(),
					null, event);
		}
	}
}
