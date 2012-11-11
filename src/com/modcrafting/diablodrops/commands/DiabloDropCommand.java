package com.modcrafting.diablodrops.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.tier.Tome;

public class DiabloDropCommand implements CommandExecutor
{

	private DiabloDrops plugin;

	public DiabloDropCommand(DiabloDrops plugin)
	{
		setPlugin(plugin);
	}

	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args)
	{
		if (!(sender instanceof Player))
		{
			sender.sendMessage(ChatColor.RED + "You cannot run this command.");
			return true;
		}
		Player player = ((Player) sender);
		if (!player.hasPermission("diablodrops.dd"))
		{
			player.sendMessage(ChatColor.RED + "You don't have permission.");
			return true;
		}
		PlayerInventory pi = player.getInventory();
		switch (args.length)
		{
			case 0:
				CraftItemStack ci = plugin.dropsAPI.getItem();
				while (ci == null)
					ci = plugin.dropsAPI.getItem();
				pi.addItem(ci);
				player.sendMessage(ChatColor.GREEN
						+ "You have been given a DiabloDrops item.");
				return true;
			default:
				if (args[0].equalsIgnoreCase("tome")
						|| args[0].equalsIgnoreCase("book"))
				{
					pi.addItem(new Tome());
					player.sendMessage(ChatColor.GREEN
							+ "You have been given a tome.");
					return true;
				}
				if (plugin.dropsAPI.matchesTier(args[0]))
				{
					CraftItemStack ci2 = plugin.dropsAPI.getItem(args[0]);
					while (ci2 == null)
						ci2 = plugin.dropsAPI.getItem(args[0]);
					pi.addItem(ci2);
					player.sendMessage(ChatColor.GREEN
							+ "You have been given a " + ChatColor.WHITE
							+ args[0] + ChatColor.GREEN + " DiabloDrops item.");
					return true;
				}
				pi.addItem(plugin.dropsAPI.getItem());
				player.sendMessage(ChatColor.GREEN
						+ "You have been given a DiabloDrops item.");
				return true;
		}
	}

	public DiabloDrops getPlugin()
	{
		return plugin;
	}

	public void setPlugin(DiabloDrops plugin)
	{
		this.plugin = plugin;
	}

}
