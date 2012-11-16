package com.modcrafting.diablodrops.events;

import org.bukkit.Chunk;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RuinGenerateEvent extends Event implements Cancellable
{

	private static final HandlerList handlers = new HandlerList();
	private boolean isCancelled = false;
	private final Chunk chunk;

	public RuinGenerateEvent(Chunk chunk)
	{
		this.chunk = chunk;
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

	public Chunk getChunk()
	{
		return chunk;
	}

}
