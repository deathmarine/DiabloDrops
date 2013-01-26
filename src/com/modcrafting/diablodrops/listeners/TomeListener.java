package com.modcrafting.diablodrops.listeners;

import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.events.IdentifyItemEvent;

public class TomeListener implements Listener
{

    private final DiabloDrops plugin;

    public TomeListener(final DiabloDrops plugin)
    {
        this.plugin = plugin;
    }

    public ChatColor findColor(final String s)
    {
        char[] c = s.toCharArray();
        for (int i = 0; i < c.length; i++)
            if ((c[i] == new Character((char) 167)) && ((i + 1) < c.length))
                return ChatColor.getByChar(c[i + 1]);
        return null;
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
            ItemStack inh = e.getPlayer().getItemInHand();
            BookMeta b = (BookMeta) inh.getItemMeta();
            if (b == null)
                return;
            if (!b.hasTitle() || !b.hasAuthor())
                return;
            if (b.getTitle().contains("Identity Tome")
                    && findColor(b.getAuthor()).equals(ChatColor.MAGIC))
            {
                Player p = e.getPlayer();
                PlayerInventory pi = p.getInventory();
                p.updateInventory();
                Iterator<ItemStack> itis = pi.iterator();
                while (itis.hasNext())
                {
                    ItemStack tool = itis.next();
                    if ((tool == null)
                            || !plugin.getDropAPI().canBeItem(tool.getType()))
                    {
                        continue;
                    }
                    ItemMeta meta;
                    if (tool.hasItemMeta())
                        meta = tool.getItemMeta();
                    else
                        meta = plugin.getServer().getItemFactory()
                                .getItemMeta(tool.getType());
                    String name;
                    if (meta.hasDisplayName())
                        name = meta.getDisplayName();
                    else
                        name = tool.getType().name();
                    if ((ChatColor.getLastColors(name) == null || (!ChatColor
                            .getLastColors(name).equalsIgnoreCase(
                                    ChatColor.MAGIC.name()) && !ChatColor
                            .getLastColors(name).equalsIgnoreCase(
                                    ChatColor.MAGIC.toString()))
                            && (!name.contains(ChatColor.MAGIC.name()) && !name
                                    .contains(ChatColor.MAGIC.toString()))))
                    {
                        continue;
                    }
                    IdentifyItemEvent iie = new IdentifyItemEvent(tool);
                    plugin.getServer().getPluginManager().callEvent(iie);
                    if (iie.isCancelled())
                    {
                        p.sendMessage(ChatColor.RED
                                + "You are unable to identify right now.");
                        p.closeInventory();
                        e.setUseItemInHand(Result.DENY);
                        e.setCancelled(true);
                        return;
                    }
                    pi.setItemInHand(null);
                    ItemStack item = plugin.getDropAPI().getItem(tool);
                    while ((item == null)
                            || !item.hasItemMeta()
                            || !item.getItemMeta().hasDisplayName()
                            || item.getItemMeta().getDisplayName()
                                    .contains(ChatColor.MAGIC.toString()))
                    {
                        item = plugin.getDropAPI().getItem(tool);
                    }
                    pi.removeItem(tool);
                    pi.addItem(item);
                    p.sendMessage(ChatColor.GREEN
                            + "You have identified an item!");
                    p.updateInventory();
                    e.setUseItemInHand(Result.DENY);
                    e.setCancelled(true);
                    p.closeInventory();
                    return;
                }
                p.sendMessage(ChatColor.RED + "You have no items to identify.");
                p.closeInventory();
                e.setUseItemInHand(Result.DENY);
                e.setCancelled(true);
                return;
            }
        }
    }
}
