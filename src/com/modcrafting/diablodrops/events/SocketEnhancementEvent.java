package com.modcrafting.diablodrops.events;

import org.bukkit.block.Furnace;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class SocketEnhancementEvent extends Event
{

	private static final HandlerList handlers = new HandlerList();
	private final ItemStack input;
	private final ItemStack fuel;
	private final ItemStack result;
	private final Furnace furnace;

	public SocketEnhancementEvent(ItemStack input, ItemStack fuel,
			ItemStack result, Furnace furnace)
	{
		this.input = input;
		this.fuel = fuel;
		this.furnace = furnace;
		this.result = result;
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

	public ItemStack getInput()
	{
		return input;
	}

	public ItemStack getFuel()
	{
		return fuel;
	}

	public Furnace getFurnace()
	{
		return furnace;
	}

	public ItemStack getResult()
	{
		return result;
	}

}
