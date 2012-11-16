package com.modcrafting.diablodrops.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EntitySpawnWithItemEvent extends Event implements
		Cancellable
{

	private static final HandlerList handlers = new HandlerList();
	private boolean isCancelled = false;
	private final LivingEntity entity;

	public EntitySpawnWithItemEvent(LivingEntity entity)
	{
		this.entity = entity;
	}

	@Override
	public boolean isCancelled()
	{
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean bln)
	{
		isCancelled = bln;
	}

	@Override
	public HandlerList getHandlers()
	{
		return handlers;
	}

	public static HandlerList getHandlerList()
	{
		return handlers;
	}

	public LivingEntity getEntity()
	{
		return entity;
	}

}
