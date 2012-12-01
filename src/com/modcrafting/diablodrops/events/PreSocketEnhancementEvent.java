package com.modcrafting.diablodrops.events;

import org.bukkit.block.Furnace;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class PreSocketEnhancementEvent extends Event implements Cancellable
{

    private static final HandlerList handlers = new HandlerList();
    public static HandlerList getHandlerList()
    {
        return handlers;
    }
    private boolean isCancelled = false;
    private final ItemStack input;
    private final ItemStack fuel;

    private final Furnace furnace;

    public PreSocketEnhancementEvent(ItemStack input, ItemStack fuel,
            Furnace furnace)
    {
        this.input = input;
        this.fuel = fuel;
        this.furnace = furnace;
    }

    public ItemStack getFuel()
    {
        return fuel;
    }

    public Furnace getFurnace()
    {
        return furnace;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

    public ItemStack getInput()
    {
        return input;
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

}
