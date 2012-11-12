package com.modcrafting.diablodrops.tier;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.modcrafting.toolapi.lib.Tool;
<<<<<<< HEAD

public class Drop extends Tool{
	public Drop(Material mat,ChatColor color, String name){
		super(mat);
        this.setRepairCost(2);
        this.setName(color+name);
	}
	
=======
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

>>>>>>> refs/remotes/fork/master
}
