package com.modcrafting.diablodrops.tier;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.modcrafting.diablodrops.drops.DropType;
import com.modcrafting.toolapi.lib.Tool;

public class Rare extends Tool{
	ChatColor color = ChatColor.YELLOW;
	public Rare(Material mat, String name){
		super(mat,DropType.RARE);
        this.setRepairCost(2);
        this.setName(color+name);
	}
}
