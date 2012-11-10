package com.modcrafting.diablodrops.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.modcrafting.diablodrops.DiabloDrops;
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

	@EventHandler
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
			if (b.getTitle().contains(ChatColor.DARK_AQUA+"Identity Tome"))
			{
				PlayerInventory pi = e.getPlayer().getInventory();
				for (int i = 0; i < pi.getSize(); i++)
				{
					ItemStack is = pi.getItem(i);
					if (is == null || is.getType().equals(Material.AIR)
							|| !plugin.dropsAPI.canBeItem(is.getType()))
						continue;
					Tool tool = new Tool(((CraftItemStack) is).getHandle());
					if(tool.getName().contains(ChatColor.MAGIC.toString())&&!tool.getType().equals(Material.WRITTEN_BOOK)){
						e.setCancelled(true);
						//TODO: Add trying method considering may return null
						e.getPlayer().setItemInHand(null);
						tool = plugin.dropsAPI.getItem(tool);
						pi.setItem(i,tool);
						return;
					}
				}
				//For fun
				String[] p = {ChatColor.RED+"You have no items to identify"};
				b.setPages(p);
			}
		}
	}
}
