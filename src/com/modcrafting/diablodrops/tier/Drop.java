package com.modcrafting.diablodrops.tier;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.modcrafting.toolapi.lib.Tool;

public class Drop extends Tool{
	public Drop(Material mat,ChatColor color, String name){
		super(mat);
        this.setRepairCost(2);
        this.setName(color+name);
	}
	
}
