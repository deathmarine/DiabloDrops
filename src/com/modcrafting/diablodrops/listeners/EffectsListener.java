package com.modcrafting.diablodrops.listeners;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.toolapi.lib.Tool;

public class EffectsListener implements Listener
{
	DiabloDrops plugin;

	public EffectsListener(DiabloDrops instance)
	{
		plugin = instance;
	}

	@SuppressWarnings("unused")
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
	{
		if (event.getDamager() instanceof Player)
		{
			Player player = (Player) event.getDamager();
			for (String string : new Tool(
					((CraftItemStack) player.getItemInHand()).getHandle())
					.getLore())
			{
				if (string.contains("damage"))
				{
					/* Finish this later */
					String[] args = ChatColor.stripColor(string).split(" ");
				}
			}
		}
	}
}
