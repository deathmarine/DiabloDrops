package com.modcrafting.diablodrops.listeners;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.NBTTagCompound;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.drops.Drops;
import com.modcrafting.diablodrops.tier.Legendary;

public class KillListener implements Listener
{
	DiabloDrops plugin;
	Legendary legend;
	Drops drops = new Drops();
	boolean spawner;
	boolean egg;
	int chance;
	boolean dropfix;
	List<String> multiW;

	public KillListener(DiabloDrops instance)
	{
		plugin = instance;
		spawner = plugin.config.getBoolean("Reason.Spawner", true);
		egg = plugin.config.getBoolean("Reason.Egg", true);
		chance = plugin.config.getInt("Precentages.ChancePerSpawn", 3);
		dropfix = plugin.config.getBoolean("DropFix.Equipment", false);
		//Fix Case
		if(plugin.config.getBoolean("Worlds.Enabled",false)){
			List<String> fixCase = new ArrayList<String>();
			for(String s:plugin.config.getStringList("Worlds.Allowed")){
				fixCase.add(s.toLowerCase());
			}
			if(fixCase.size()>0)multiW=fixCase;
		}
	}

	@EventHandler
	public void onSpawn(CreatureSpawnEvent event)
	{
		LivingEntity entity = event.getEntity();
		if (multiW!=null&&!multiW.contains(entity.getLocation().getWorld().getName().toLowerCase())) 
			return;
		if (spawner && event.getSpawnReason().equals(SpawnReason.SPAWNER))
			return;
		if (egg && (event.getSpawnReason().equals(SpawnReason.EGG) || event.getSpawnReason().equals(SpawnReason.SPAWNER_EGG)))
			return;
		Integer random = plugin.gen.nextInt(100) + 1;
		if (entity instanceof Monster && chance >= random)
		{
			CraftItemStack ci = plugin.dropsAPI.getItem();
			int tries = 0;
			while (ci == null && tries < 5)
			{
				ci = plugin.dropsAPI.getItem();
				tries++;
			}
			if (ci != null)
				setEquipment(ci, entity);
		}
	}

	public void setEquipment(CraftItemStack ci, Entity e)
	{
		Material mat = ci.getType();
		EntityLiving ev = ((CraftLivingEntity) e).getHandle();
		if (drops.isBoots(mat))
		{
			ev.setEquipment(4, ci.getHandle());
		}
		else if (drops.isChestPlate(mat))
		{
			ev.setEquipment(3, ci.getHandle());
		}
		else if (drops.isLeggings(mat))
		{
			ev.setEquipment(2, ci.getHandle());
		}
		else if (drops.isHelmet(mat))
		{
			ev.setEquipment(1, ci.getHandle());
		}
		else
		{
			ev.setEquipment(0, ci.getHandle());
		}

	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event)
	{
		if (event.getEntity() instanceof Monster)
		{
			Location loc = event.getEntity().getLocation();
			for (net.minecraft.server.ItemStack mItem : ((CraftLivingEntity) event
					.getEntity()).getHandle().getEquipment())
			{
				if (mItem != null)
				{
					if (dropfix)
					{
						dropItem(mItem, loc);
					}
					else
					{
						if (mItem.tag != null)
						{
							NBTTagCompound nc = mItem.tag
									.getCompound("display");
							if (nc != null)
							{
								String sg = nc.getString("Name");
								if (sg != null
										&& sg.contains(new Character((char) 167)
												.toString()))
								{
									dropItem(mItem, loc);
								}
							}
						}
					}
				}
			}
		}
	}

	public void dropItem(net.minecraft.server.ItemStack mItem, Location loc)
	{
		double xs = plugin.gen.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;
		double ys = plugin.gen.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;
		double zs = plugin.gen.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;
		EntityItem entity = new EntityItem(
				((CraftWorld) loc.getWorld()).getHandle(), loc.getX() + xs,
				loc.getY() + ys, loc.getZ() + zs, mItem);
		((CraftWorld) loc.getWorld()).getHandle().addEntity(entity);
	}

	
}
