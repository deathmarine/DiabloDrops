package com.modcrafting.diablodrops.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.modcrafting.toolapi.lib.Tool;

public class IdentifyItemEvent extends Event implements Cancellable
{

	private static final HandlerList handlers = new HandlerList();
	private boolean isCancelled = false;
	private final Tool tool;

	public IdentifyItemEvent(Tool tool)
	{
		this.tool = tool;
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

	public Tool getItemStack()
	{
		return tool;
	}

}
