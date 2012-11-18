package com.stirante.NBTTagAPI;

import net.minecraft.server.EntityLiving;

import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class EntityEquipment
{
	public static void setWeapon(LivingEntity mob, ItemStack item)
	{
		EntityLiving ent = ((CraftLivingEntity) mob).getHandle();
		net.minecraft.server.ItemStack itemStack = ((CraftItemStack) item)
				.getHandle();
		ent.setEquipment(0, itemStack);
	}

	public static ItemStack getWeapon(LivingEntity mob)
	{
		EntityLiving ent = ((CraftLivingEntity) mob).getHandle();
		return new CraftItemStack(ent.getEquipment(0));
	}

	public static void setHelmet(LivingEntity mob, ItemStack item)
	{
		EntityLiving ent = ((CraftLivingEntity) mob).getHandle();
		net.minecraft.server.ItemStack itemStack = ((CraftItemStack) item)
				.getHandle();
		ent.setEquipment(1, itemStack);
	}

	public static ItemStack getHelmet(LivingEntity mob)
	{
		EntityLiving ent = ((CraftLivingEntity) mob).getHandle();
		return new CraftItemStack(ent.getEquipment(1));
	}

	public static void setChestplate(LivingEntity mob, ItemStack item)
	{
		EntityLiving ent = ((CraftLivingEntity) mob).getHandle();
		net.minecraft.server.ItemStack itemStack = ((CraftItemStack) item)
				.getHandle();
		ent.setEquipment(2, itemStack);
	}

	public static ItemStack getChestplate(LivingEntity mob)
	{
		EntityLiving ent = ((CraftLivingEntity) mob).getHandle();
		return new CraftItemStack(ent.getEquipment(2));
	}

	public static void setPants(LivingEntity mob, ItemStack item)
	{
		EntityLiving ent = ((CraftLivingEntity) mob).getHandle();
		net.minecraft.server.ItemStack itemStack = ((CraftItemStack) item)
				.getHandle();
		ent.setEquipment(3, itemStack);
	}

	public static ItemStack getPants(LivingEntity mob)
	{
		EntityLiving ent = ((CraftLivingEntity) mob).getHandle();
		return new CraftItemStack(ent.getEquipment(3));
	}

	public static void setBoots(LivingEntity mob, ItemStack item)
	{
		EntityLiving ent = ((CraftLivingEntity) mob).getHandle();
		net.minecraft.server.ItemStack itemStack = ((CraftItemStack) item)
				.getHandle();
		ent.setEquipment(4, itemStack);
	}

	public static ItemStack getBoots(LivingEntity mob)
	{
		EntityLiving ent = ((CraftLivingEntity) mob).getHandle();
		return new CraftItemStack(ent.getEquipment(4));
	}
}
