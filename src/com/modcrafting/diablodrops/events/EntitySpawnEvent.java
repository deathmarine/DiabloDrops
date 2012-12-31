package com.modcrafting.diablodrops.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EntitySpawnEvent extends Event
{

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

    private final LivingEntity entity;
    private int chance;

    public EntitySpawnEvent(LivingEntity entity, int chance)
    {
        this.entity = entity;
        this.chance = chance;
    }

    public int getChance()
    {
        return chance;
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

    public void setChance(int chance)
    {
        this.chance = chance;
    }

}
