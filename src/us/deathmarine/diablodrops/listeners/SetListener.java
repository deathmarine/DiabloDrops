package us.deathmarine.diablodrops.listeners;

import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import us.deathmarine.diablodrops.DiabloDrops;
import us.deathmarine.diablodrops.effects.EffectsAPI;
import us.deathmarine.diablodrops.sets.ArmorSet;

public class SetListener implements Listener {
	DiabloDrops plugin;

	public SetListener(final DiabloDrops instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onDiabloMonsterDamageByEntityEvent(
			final EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Monster
				&& event.getEntity() instanceof LivingEntity) {
			if (plugin.getSetAPI()
					.wearingSet((LivingEntity) event.getDamager())) {
				String sName = plugin.getSetAPI().getNameOfSet(
						(LivingEntity) event.getDamager());
				ArmorSet aSet = plugin.getSetAPI().getArmorSet(sName);
				if (aSet != null) {
					List<String> effects = aSet.getBonuses();
					for (String s : effects) {
						EffectsAPI.addEffect((LivingEntity) event.getEntity(),
								(LivingEntity) event.getDamager(), s, event);
					}
				}
			}
		} else if (event.getDamager() instanceof Player
				&& event.getEntity() instanceof LivingEntity) {
			if (plugin.getSetAPI().wearingSet((Player) event.getDamager())) {
				String sName = plugin.getSetAPI().getNameOfSet(
						(Player) event.getDamager());
				ArmorSet aSet = plugin.getSetAPI().getArmorSet(sName);
				if (aSet != null) {
					List<String> effects = aSet.getBonuses();
					for (String s : effects) {
						EffectsAPI.addEffect((LivingEntity) event.getEntity(),
								(LivingEntity) event.getDamager(), s, event);
					}
				}
			}
		}
		if (event.getEntity() instanceof Monster
				&& event.getDamager() instanceof LivingEntity) {
			if (plugin.getSetAPI().wearingSet((LivingEntity) event.getEntity())) {
				String sName = plugin.getSetAPI().getNameOfSet(
						(LivingEntity) event.getEntity());
				ArmorSet aSet = plugin.getSetAPI().getArmorSet(sName);
				if (aSet != null) {
					List<String> effects = aSet.getBonuses();
					for (String s : effects) {
						EffectsAPI.addEffect((LivingEntity) event.getDamager(),
								(LivingEntity) event.getEntity(), s, event);
					}
				}
			}

		} else if (event.getEntity() instanceof Player
				&& event.getDamager() instanceof LivingEntity) {
			if (plugin.getSetAPI().wearingSet((Player) event.getEntity())) {
				String sName = plugin.getSetAPI().getNameOfSet(
						(Player) event.getEntity());
				ArmorSet aSet = plugin.getSetAPI().getArmorSet(sName);
				if (aSet != null) {
					List<String> effects = aSet.getBonuses();
					for (String s : effects) {
						EffectsAPI.addEffect((LivingEntity) event.getDamager(),
								(Player) event.getEntity(), s, event);
					}
				}
			}

		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onDiabloMonsterDamageEvent(final EntityDamageEvent event) {
		if (event.getEntity() instanceof Monster) {
			if (plugin.getSetAPI().wearingSet((LivingEntity) event.getEntity())) {
				String sName = plugin.getSetAPI().getNameOfSet(
						(LivingEntity) event.getEntity());
				ArmorSet aSet = plugin.getSetAPI().getArmorSet(sName);
				if (aSet != null) {
					List<String> effects = aSet.getBonuses();
					for (String s : effects) {
						EffectsAPI.addEffect((LivingEntity) event.getEntity(),
								null, s, event);
					}
				}
			}

		} else if (event.getEntity() instanceof Player) {
			if (plugin.getSetAPI().wearingSet((Player) event.getEntity())) {
				String sName = plugin.getSetAPI().getNameOfSet(
						(Player) event.getEntity());
				ArmorSet aSet = plugin.getSetAPI().getArmorSet(sName);
				if (aSet != null) {
					List<String> effects = aSet.getBonuses();
					for (String s : effects) {
						EffectsAPI.addEffect((Player) event.getEntity(), null,
								s, event);
					}
				}
			}

		}
	}
}
