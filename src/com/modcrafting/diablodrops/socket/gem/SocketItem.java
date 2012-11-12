package com.modcrafting.diablodrops.socket.gem;

import org.bukkit.Material;

import com.stirante.ItemNamer.Namer;

public class SocketItem extends SocketGem
{

	public SocketItem(Material mat)
	{
		super(mat);
		Namer.setName(this, "Socket Enhancement");
		Namer.setLore(this, "Put in the bottom of a furnace",
				"with another item in the top", "to add socket enhancements.");
	}
}
