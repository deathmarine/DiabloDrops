package com.modcrafting.diablodrops.items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.skullapi.lib.Skull;
import com.modcrafting.toolapi.lib.Tool;

public class Socket extends Tool
{

	public Socket(Material mat)
	{
		super(mat);
		this.setName(ChatColor.DARK_AQUA+"Socket Enhancement");
		this.addLore(ChatColor.GOLD+"Put in the bottom of a furnace");
		this.addLore(ChatColor.GOLD+"with another item in the top");
		this.addLore(ChatColor.GOLD+"to add socket enhancements.");
		if(mat.equals(Material.SKULL_ITEM)){
			Skull sk = new Skull(this.getHandle());
			sk.setOwner(Bukkit.getServer().getOfflinePlayers()[DiabloDrops.getInstance().gen.nextInt(Bukkit.getServer().getOfflinePlayers().length)].getName());
		}
	}
}
