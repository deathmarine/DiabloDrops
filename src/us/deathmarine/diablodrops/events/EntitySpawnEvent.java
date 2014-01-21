package us.deathmarine.diablodrops.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EntitySpawnEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlerList() {
		return handlers;
	}

	private int chance;
	private final LivingEntity entity;

	public EntitySpawnEvent(LivingEntity entity, int chance) {
		this.entity = entity;
		this.chance = chance;
	}

	/**
	 * Gets the event's chance
	 * 
	 * @return event's chance
	 */
	public int getChance() {
		return chance;
	}

	/**
	 * Gets the event's entity
	 * 
	 * @return event's entity
	 */
	public LivingEntity getEntity() {
		return entity;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	/**
	 * Sets the event's chance
	 * 
	 * @param chance
	 *            Chance for entity to spawn with an item
	 */
	public void setChance(int chance) {
		this.chance = chance;
	}

}
