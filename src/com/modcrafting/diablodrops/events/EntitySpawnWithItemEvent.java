package com.modcrafting.diablodrops.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.modcrafting.diablolibrary.items.DiabloItemStack;

public class EntitySpawnWithItemEvent extends Event implements Cancellable
{

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

    private boolean isCancelled = false;
    private final LivingEntity entity;

    private List<DiabloItemStack> items;

    public EntitySpawnWithItemEvent(LivingEntity entity)
    {
        this.entity = entity;
        this.setItems(new ArrayList<DiabloItemStack>());
    }

    public EntitySpawnWithItemEvent(LivingEntity entity,
            List<DiabloItemStack> items)
    {
        this.entity = entity;
        setItems(items);
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

    public List<DiabloItemStack> getItems()
    {
        return items;
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

    public void setItems(List<DiabloItemStack> items)
    {
        this.items = items;
    }

}
