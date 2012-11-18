package com.modcrafting.diablodrops.tier;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.modcrafting.toolapi.lib.Tool;
import com.stirante.ItemNamer.Namer;

public class Drop extends Tool
{

	public Drop(Material mat, ChatColor color, String name)
	{
		super(mat);
		this.setRepairCost(2);
		Namer.setName(this, color + name);
	}

	public Drop(Material mat, ChatColor color, String name, String... lore)
	{
		super(mat);
		this.setRepairCost(2);
		Namer.setName(this, color + name);
		Namer.setLore(this, lore);
	}

	public Drop(Material mat, ChatColor color, String name, short durability)
	{
		super(mat);
		this.setRepairCost(2);
		Namer.setName(this, color + name);
		this.setDurability(durability);
	}

	public Drop(Material mat, ChatColor color, String name, short durability,
			String... lore)
	{
		super(mat);
		this.setRepairCost(2);
		Namer.setName(this, color + name);
		Namer.setLore(this, lore);
		this.setDurability(durability);
	}

}
