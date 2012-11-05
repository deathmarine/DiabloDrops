package com.modcrafting.diablodrops.tier;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.modcrafting.diablodrops.drops.DropType;
import com.modcrafting.toolapi.lib.Tool;

public class Set extends Tool{
	ChatColor color = ChatColor.RED;
	public Set(Material mat, String name){
		super(mat,DropType.SET);
        this.setRepairCost(2);
        this.setName(color+name);
	}
}
