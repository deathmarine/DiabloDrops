package com.modcrafting.diablodrops.tier;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.modcrafting.diablodrops.drops.DropType;
import com.modcrafting.toolapi.lib.Tool;

public class Magical extends Tool{
	ChatColor color = ChatColor.BLUE;
	public Magical(Material mat, String name){
		super(mat,DropType.MAGICAL);
        this.setRepairCost(2);
        this.setName(color+name);
	}
}
