package com.modcrafting.diablodrops.tier;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.modcrafting.diablodrops.drops.DropType;
import com.modcrafting.toolapi.lib.Tool;

public class Unidentified extends Tool{
	ChatColor color = ChatColor.MAGIC;
	public Unidentified(Material mat, String name){
		super(mat,DropType.UNIDENTIFIED);
        this.setRepairCost(2);
        this.setName(color+name);
	}
}
