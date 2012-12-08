package com.modcrafting.diablodrops.events;

import java.util.List;

import net.minecraft.server.ItemStack;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EntityDropItemEvent extends Event implements Cancellable
{

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

    private boolean isCancelled = false;

    private final LivingEntity entity;
    private List<net.minecraft.server.ItemStack> list;
    public EntityDropItemEvent(LivingEntity entity, List<ItemStack> list)
    {
        this.entity = entity;
        this.list=list;
    }

    public LivingEntity getEntity()
    {
        return entity;
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

	public List<net.minecraft.server.ItemStack> getDropList() {
		return list;
	}

}
