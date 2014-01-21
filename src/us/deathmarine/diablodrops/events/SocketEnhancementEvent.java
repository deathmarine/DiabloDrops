package us.deathmarine.diablodrops.events;

import org.bukkit.block.Furnace;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class SocketEnhancementEvent extends Event
{

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

    private final ItemStack fuel;
    private final Furnace furnace;
    private final ItemStack input;

    private final ItemStack result;

    public SocketEnhancementEvent(ItemStack input, ItemStack fuel,
            ItemStack result, Furnace furnace)
    {
        this.input = input;
        this.fuel = fuel;
        this.furnace = furnace;
        this.result = result;
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

    public ItemStack getResult()
    {
        return result;
    }

}
