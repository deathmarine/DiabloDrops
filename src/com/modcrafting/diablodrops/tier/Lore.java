package com.modcrafting.diablodrops.tier;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.modcrafting.diablodrops.drops.DropType;
import com.modcrafting.toolapi.lib.Tool;

public class Lore extends Tool{
	ChatColor color = ChatColor.GREEN;
	public Lore(Material mat, String name){
		super(mat,DropType.LORE);
        this.setRepairCost(2);
        this.setName(color+name);
	}
}
