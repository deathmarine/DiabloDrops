package com.modcrafting.diablodrops.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.modcrafting.diablolibrary.items.DiabloItemStack;

public class IdentifyItemEvent extends Event implements Cancellable
{

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

    private boolean isCancelled = false;

    private final DiabloItemStack tool;

    public IdentifyItemEvent(DiabloItemStack tool)
    {
        this.tool = tool;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

    public DiabloItemStack getItemStack()
    {
        return tool;
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
