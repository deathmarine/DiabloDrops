package com.stirante.ItemNamer;

import java.util.ArrayList;

import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.NBTTagString;

import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class Namer
{

	private static CraftItemStack craftStack;
	private static net.minecraft.server.ItemStack itemStack;

	public static ItemStack setName(ItemStack item, String name)
	{
		if (item instanceof CraftItemStack)
		{
			craftStack = (CraftItemStack) item;
			Namer.itemStack = craftStack.getHandle();
		}
		else if (item instanceof ItemStack)
		{
			craftStack = new CraftItemStack(item);
			Namer.itemStack = craftStack.getHandle();
		}
		NBTTagCompound tag = itemStack.tag;
		if (tag == null)
		{
			tag = new NBTTagCompound();
			tag.setCompound("display", new NBTTagCompound());
			itemStack.tag = tag;
		}

		tag = itemStack.tag.getCompound("display");
		tag.setString("Name", name);
		itemStack.tag.setCompound("display", tag);
		return craftStack;
	}

	public static String getName(ItemStack item)
	{
		if (item instanceof CraftItemStack)
		{
			craftStack = (CraftItemStack) item;
			Namer.itemStack = craftStack.getHandle();
		}
		else if (item instanceof ItemStack)
		{
			craftStack = new CraftItemStack(item);
			Namer.itemStack = craftStack.getHandle();
		}
		NBTTagCompound tag = itemStack.tag;
		if (tag == null)
		{
			return null;
		}
		tag = itemStack.tag.getCompound("display");
		return tag.getString("Name");
	}

	//
	// public ItemStack setLore(ItemStack item, String... lore)
	// {
	// if (item instanceof CraftItemStack)
	// {
	// craftStack = (CraftItemStack) item;
	// Namer.itemStack = craftStack.getHandle();
	// }
	// else if (item instanceof ItemStack)
	// {
	// craftStack = new CraftItemStack(item);
	// Namer.itemStack = craftStack.getHandle();
	// }
	// NBTTagCompound tag = itemStack.tag;
	// if (tag == null)
	// {
	// tag = new NBTTagCompound();
	// tag.setCompound("display", new NBTTagCompound());
	// itemStack.tag = tag;
	// }
	// tag = itemStack.tag.getCompound("display");
	// NBTTagList list = new NBTTagList();
	// for (String l : lore)
	// {
	// list.add(new NBTTagString("", l));
	// }
	// tag.set("Lore", list);
	// itemStack.tag.setCompound("display", tag);
	// return craftStack;
	// }

	public static ItemStack setLore(ItemStack item, String... lore)
	{
		if (item instanceof CraftItemStack)
		{
			craftStack = (CraftItemStack) item;
			Namer.itemStack = craftStack.getHandle();
		}
		else if (item instanceof ItemStack)
		{
			craftStack = new CraftItemStack(item);
			Namer.itemStack = craftStack.getHandle();
		}
		NBTTagCompound tag = itemStack.tag;
		if (tag == null)
		{
			tag = new NBTTagCompound();
			tag.setCompound("display", new NBTTagCompound());
			itemStack.tag = tag;
		}
		tag = itemStack.tag.getCompound("display");
		NBTTagList list = new NBTTagList();
		for (String l : lore)
		{
			list.add(new NBTTagString("", l));
		}
		tag.set("Lore", list);
		itemStack.tag.setCompound("display", tag);
		return craftStack;
	}

	public static ItemStack addLore(ItemStack item, String lore)
	{
		if (item instanceof CraftItemStack)
		{
			craftStack = (CraftItemStack) item;
			Namer.itemStack = craftStack.getHandle();
		}
		else if (item instanceof ItemStack)
		{
			craftStack = new CraftItemStack(item);
			Namer.itemStack = craftStack.getHandle();
		}
		NBTTagCompound tag = itemStack.tag;
		if (tag == null)
		{
			tag = new NBTTagCompound();
			tag.setCompound("display", new NBTTagCompound());
			tag.getCompound("display").set("Lore", new NBTTagList());
			itemStack.tag = tag;
		}

		tag = itemStack.tag.getCompound("display");
		NBTTagList list = tag.getList("Lore");
		list.add(new NBTTagString("", lore));
		tag.set("Lore", list);
		itemStack.tag.setCompound("display", tag);
		return craftStack;
	}

	public static String[] getLore(ItemStack item)
	{
		if (item instanceof CraftItemStack)
		{
			craftStack = (CraftItemStack) item;
			Namer.itemStack = craftStack.getHandle();
		}
		NBTTagCompound tag = itemStack.tag;
		if (tag == null)
		{
			tag = new NBTTagCompound();
			tag.setCompound("display", new NBTTagCompound());
			tag.getCompound("display").set("Lore", new NBTTagList());
			itemStack.tag = tag;
		}
		tag = itemStack.tag;
		NBTTagList list = tag.getCompound("display").getList("Lore");
		ArrayList<String> strings = new ArrayList<String>();
		String[] lores = new String[] {};
		for (int i = 0; i < list.size(); i++)
			strings.add(((NBTTagString) list.get(i)).data);
		strings.toArray(lores);
		return lores;
	}
}
