package com.modcrafting.diablodrops.events;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RuinGenerateEvent extends Event implements Cancellable
{

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

    private boolean isCancelled = false;

    private final Chunk chunk;
    private Block chest;

    public RuinGenerateEvent(Chunk chunk, Block block)
    {
        this.chunk = chunk;
        this.chest = block;
    }

    public Block getChest()
    {
        return chest;
    }

    public Chunk getChunk()
    {
        return chunk;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
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

    public void setChest(Block chest)
    {
        this.chest = chest;
    }

}
