package com.modcrafting.diablodrops.listeners;

import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.events.IdentifyItemEvent;
import com.modcrafting.toolapi.lib.Tool;

import de.bananaco.bookapi.lib.Book;
import de.bananaco.bookapi.lib.CraftBookBuilder;

public class TomeListener implements Listener
{

	private DiabloDrops plugin;

	public TomeListener(DiabloDrops plugin)
	{
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOWEST)
	public void onRightClick(PlayerInteractEvent e)
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
					if (next == null
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
						e.setCancelled(true);
						return;
					}
					pi.setItemInHand(null);
					pi.removeItem(cis);
					CraftItemStack newStack = plugin.dropsAPI.getIdItem(
							cis.getType(), name);
					while (newStack == null)
						newStack = plugin.dropsAPI.getIdItem(cis.getType(),
								name);
					Tool newTool = new Tool(newStack.getHandle());
					pi.addItem(newTool);
					p.sendMessage(ChatColor.GREEN
							+ "You have identified an item!");
					p.updateInventory();
					e.setCancelled(true);
					return;
				}
				p.sendMessage(ChatColor.RED + "You have no items to identify.");
				e.setCancelled(true);
				return;
			}
		}
	}
}
