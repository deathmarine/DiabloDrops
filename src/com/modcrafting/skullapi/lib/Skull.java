package com.modcrafting.skullapi.lib;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.NBTTagString;

import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;

public class Skull extends CraftItemStack implements SkullInterface
{

	public Skull(Material type) throws Exception
	{
		super(type);
		if (!type.equals(Material.SKULL))
			throw new Exception("Item must be a skull.");
	}
	public Skull(ItemStack is)
	{
		super(is);
	}

	public enum SkullType
	{
		SKELETON(0), WITHER(1), ZOMBIE(2), PLAYER(3), CREEPER(4);
		public int type;

		private SkullType(int i)
		{
			type = i;
		}

		public int getData()
		{
			return type;
		}

	}

	@Override
	public String getName()
	{
		NBTTagCompound nc = this.getHandle().tag.getCompound("display");
		if (nc != null)
		{
			String s = nc.getString("Name");
			if (s != null)
				return s;
		}
		return null;
	}

	@Override
	public void setName(String name)
	{
		NBTTagCompound ntag = new NBTTagCompound();
		NBTTagString p = new NBTTagString(name);
		p.setName(name);
		p.data = name;
		ntag.set("Name", p);
		ntag.setString("Name", name);
		this.getHandle().tag.setCompound("display", ntag);
	}

	@Override
	public void setOwner(String name)
	{
		this.setDurability((short) 3);
		NBTTagCompound tag = new NBTTagCompound();
		tag.set("SkullOwner", new NBTTagString("SkullOwner", name));
		this.getHandle().setTag(tag);
	}

	@Override
	public String getOwner()
	{
		if (this.getHandle().tag != null
				&& this.getHandle().tag.getString("SkullOwner") != null)
			return this.getHandle().tag.getString("SkullOwner");
		return null;
	}

	@Override
	public void setSkullType(SkullType type)
	{
		this.getHandle().setData(type.getData());
	}

	@Override
	public SkullType getSkullType()
	{
		return SkullType.values()[this.getHandle().getData()];
	}

	@Override
	public void setLore(List<String> lore)
	{
		NBTTagCompound ntag = new NBTTagCompound();
		NBTTagList p = new NBTTagList("Lore");
		for (String s : lore)
		{
			p.add(new NBTTagString("", s));
		}
		ntag.set("Lore", p);
		this.getHandle().tag.setCompound("display", ntag);
	}

	@Override
	public String[] getLore()
	{
		NBTTagList list = this.getHandle().tag.getCompound("display").getList(
				"Lore");
		ArrayList<String> strings = new ArrayList<String>();
		String[] lores = new String[] {};
		for (int i = 0; i < strings.size(); i++)
			strings.add(((NBTTagString) list.get(i)).data);
		strings.toArray(lores);
		return lores;
	}

}
