package com.modcrafting.diablodrops.tier;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.modcrafting.diablodrops.drops.DropType;
import com.modcrafting.toolapi.lib.Tool;

public class Legendary extends Tool{
	ChatColor color = ChatColor.GOLD;
	public Legendary(Material mat, String name){
		super(mat,DropType.LEGENDARY);
        this.setRepairCost(2);
        this.setName(color+name);
	}
	
}
