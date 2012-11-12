package com.modcrafting.diablodrops.socket.gem;

import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;

import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;

public class SocketGem extends CraftItemStack
{
	private NBTTagCompound tag;

	public SocketGem()
	{
		super(Material.EMERALD);
		ItemStack mitem = this.getHandle();
		if (mitem.tag == null)
		{
			mitem.tag = new NBTTagCompound();
		}
		this.setTag(mitem.tag);
	}

	public NBTTagCompound getTag()
	{
		return tag;
	}

	public void setTag(NBTTagCompound tag)
	{
		this.tag = tag;
	}
}
