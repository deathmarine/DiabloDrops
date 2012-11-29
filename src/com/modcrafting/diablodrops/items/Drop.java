package com.modcrafting.diablodrops.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.modcrafting.toolapi.lib.Tool;

public class Drop extends Tool
{

	public Drop(Material mat, ChatColor color, String name)
	{
		super(mat);
		this.setRepairCost(2);
		this.setName(color + name);
	}

	public Drop(Material mat, ChatColor color, String name, String... lore)
	{
		super(mat);
		this.setRepairCost(2);
		this.setName(color + name);
		for(String e:lore){
			this.addLore(e);
		}
	}

	public Drop(Material mat, ChatColor color, String name, short durability)
	{
		super(mat);
		this.setRepairCost(2);
		this.setName(color + name);
		this.setDurability(durability);
	}

	public Drop(Material mat, ChatColor color, String name, short durability,
			String... lore)
	{
		super(mat);
		this.setRepairCost(2);
		this.setName(color + name);
		for(String e:lore){
			this.addLore(e);
		}
		this.setDurability(durability);
	}

}
