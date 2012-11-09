package com.modcrafting.diablodrops.listeners;

import net.minecraft.server.NBTTagCompound;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.modcrafting.diablodrops.DiabloDrops;

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
			if (b.getTitle().contains(ChatColor.DARK_AQUA + "Tome of Identify"))
			{
				PlayerInventory pi = e.getPlayer().getInventory();
				for (int i = 0; i < pi.getSize(); i++)
				{
					ItemStack is = pi.getItem(i);
					if (is == null || is.getType().equals(Material.AIR)
							|| !plugin.dropsAPI.canBeItem(is.getType()))
						continue;
					CraftItemStack cis = ((CraftItemStack) is);
					net.minecraft.server.ItemStack mis = cis.getHandle();
					NBTTagCompound tag = mis.tag;
					if (tag == null)
					{
						tag = new NBTTagCompound();
						tag.setCompound("display", new NBTTagCompound());
						mis.tag = tag;
					}
					if (ChatColor.valueOf(
							ChatColor.getLastColors(tag.getString("Name")))
							.equals(ChatColor.MAGIC))
					{
						pi.setItem(i, plugin.dropsAPI.getItem(cis.getType()));
						e.getPlayer().setItemInHand(null);
						return;
					}
				}
			}
		}
	}
}
