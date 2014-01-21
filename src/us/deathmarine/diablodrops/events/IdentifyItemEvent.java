package us.deathmarine.diablodrops.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class IdentifyItemEvent extends Event implements Cancellable
{

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

    private boolean isCancelled = false;

    private final ItemStack tool;

    public IdentifyItemEvent(ItemStack tool)
    {
        this.tool = tool;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

    public ItemStack getItemStack()
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
