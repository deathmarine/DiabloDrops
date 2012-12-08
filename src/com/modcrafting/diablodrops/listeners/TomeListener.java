package com.modcrafting.diablodrops.listeners;

import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.events.IdentifyItemEvent;
import com.modcrafting.diablodrops.items.Tome;
import com.modcrafting.toolapi.lib.Tool;

import de.bananaco.bookapi.lib.Book;
import de.bananaco.bookapi.lib.CraftBookBuilder;

public class TomeListener implements Listener
{

    private final DiabloDrops plugin;

    public TomeListener(final DiabloDrops plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCraftItem(final CraftItemEvent e)
    {
        ItemStack item = e.getCurrentItem();
        if (item.getType().equals(Material.WRITTEN_BOOK))
        {
            if (e.isShiftClick())
            {
                e.setCancelled(true);
            }
            e.setCurrentItem(new Tome());
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.LOWEST)
    public void onRightClick(final PlayerInteractEvent e)
    {
        if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction()
                .equals(Action.RIGHT_CLICK_BLOCK))
                && e.getPlayer().getItemInHand().getType()
                        .equals(Material.WRITTEN_BOOK))
        {
            Book b = new CraftBookBuilder().getBook(e.getPlayer()
                    .getItemInHand());
            if (b == null)
                return;
            if (b.getTitle().contains(ChatColor.DARK_AQUA + "Identity Tome"))
            {
                Player p = e.getPlayer();
                PlayerInventory pi = p.getInventory();
                p.updateInventory();
                Iterator<ItemStack> itis = pi.iterator();
                while (itis.hasNext())
                {
                    ItemStack next = itis.next();
                    if ((next == null)
                            || !plugin.dropsAPI.canBeItem(next.getType()))
                    {
                        continue;
                    }
                    CraftItemStack cis = ((CraftItemStack) next);
                    Tool tool = new Tool(cis.getHandle());
                    String name = tool.getName();
                    if ((!ChatColor.getLastColors(name).equalsIgnoreCase(
                            ChatColor.MAGIC.name()) && !ChatColor
                            .getLastColors(name).equalsIgnoreCase(
                                    ChatColor.MAGIC.toString()))
                            && (!name.contains(ChatColor.MAGIC.name()) && !name
                                    .contains(ChatColor.MAGIC.toString())))
                    {
                        continue;
                    }
                    IdentifyItemEvent iie = new IdentifyItemEvent(tool);
                    if (iie.isCancelled())
                    {
                        p.sendMessage(ChatColor.RED
                                + "You are unable to identify right now.");
                        e.setUseItemInHand(Result.DENY);
                        e.setCancelled(true);
                        return;
                    }
                    pi.setItemInHand(null);
                    Tool item = plugin.dropsAPI.getItem(tool);
                    while ((item == null)
                            || item.getName().contains(
                                    ChatColor.MAGIC.toString()))
                    {
                        item = plugin.dropsAPI.getItem(tool);
                    }
                    pi.removeItem(tool);
                    pi.addItem(item);
                    p.sendMessage(ChatColor.GREEN
                            + "You have identified an item!");
                    p.updateInventory();
                    e.setUseItemInHand(Result.DENY);
                    e.setCancelled(true);
                    return;
                }
                p.sendMessage(ChatColor.RED + "You have no items to identify.");
                e.setUseItemInHand(Result.DENY);
                e.setCancelled(true);
                return;
            }
        }
    }
}
