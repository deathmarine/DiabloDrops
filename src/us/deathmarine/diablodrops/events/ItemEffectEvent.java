package us.deathmarine.diablodrops.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ItemEffectEvent extends Event implements Cancellable
{

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

    private final LivingEntity damaged;

    private final LivingEntity damager;
    private final String effect;
    private boolean isCancelled = false;

    public ItemEffectEvent(LivingEntity damaged, LivingEntity damager,
            String effect)
    {
        this.damaged = damaged;
        this.damager = damager;
        this.effect = effect;
    }

    public LivingEntity getDamaged()
    {
        return damaged;
    }

    public LivingEntity getDamager()
    {
        return damager;
    }

    public String getEffect()
    {
        return effect;
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

}
