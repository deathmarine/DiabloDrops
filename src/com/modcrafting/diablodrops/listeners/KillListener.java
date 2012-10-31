package com.modcrafting.diablodrops.listeners;

import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.NBTTagCompound;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;

import com.modcrafting.diablodrops.Main;
import com.modcrafting.diablodrops.drops.Drops;
import com.modcrafting.diablodrops.tier.Legendary;
import com.modcrafting.diablodrops.tier.Lore;
import com.modcrafting.diablodrops.tier.Magical;
import com.modcrafting.diablodrops.tier.Rare;
import com.modcrafting.diablodrops.tier.Set;

public class KillListener implements Listener{
	Main plugin;
	Legendary legend;
	Drops drops = new Drops();
	String[] types = {"legendary","lore","magical","rare","set"};
	public KillListener(Main instance){
		plugin=instance;
	}
	@EventHandler
	public void onSpawn(CreatureSpawnEvent event){
		LivingEntity entity = event.getEntity();
		
		if(entity instanceof Monster&&plugin.gen.nextInt(125)==0&&!event.getSpawnReason().equals(SpawnReason.SPAWNER)){
			Material mat = Material.DIAMOND_SWORD;
			if(plugin.gen.nextBoolean()){
				Material[] mats = drops.armorPicker();
				mat = mats[plugin.gen.nextInt(mats.length-1)];
			}else{
				mat = drops.weaponPicker();				
			}
			String type = types[plugin.gen.nextInt(types.length-1)];
			//Configurable / Conditional types coming soon
			if(type.equalsIgnoreCase("legendary")){
				int e = 7,l = 10;
				CraftItemStack ci = new Legendary(mat,plugin);
				for(;e>0;e--){
					int lvl = plugin.gen.nextInt(l+1);
					Enchantment ench = drops.enchant();
					if(lvl!=0&&ench!=null)ci.addUnsafeEnchantment(ench, lvl);
				}
				setEquipment(ci,event.getEntity());
			}
			if(type.equalsIgnoreCase("lore")){
				int e = 7,l = 9;
				CraftItemStack ci = new Lore(mat,plugin);
				for(;e>0;e--){
					int lvl = plugin.gen.nextInt(l+1);
					Enchantment ench = drops.enchant();
					if(lvl!=0&&ench!=null)ci.addUnsafeEnchantment(ench, lvl);
				}
				setEquipment(ci,event.getEntity());
			}
			if(type.equalsIgnoreCase("magical")){
				int e = 3,l = 4;
				CraftItemStack ci = new Magical(mat,plugin);
				for(;e>0;e--){
					int lvl = plugin.gen.nextInt(l+1);
					Enchantment ench = drops.enchant();
					if(lvl!=0&&ench!=null)ci.addUnsafeEnchantment(ench, lvl);
				}
				setEquipment(ci,event.getEntity());
			}
			if(type.equalsIgnoreCase("rare")){
				int e = 5,l = 5;
				CraftItemStack ci = new Rare(mat,plugin);
				for(;e>0;e--){
					int lvl = plugin.gen.nextInt(l+1);
					Enchantment ench = drops.enchant();
					if(lvl!=0&&ench!=null)ci.addUnsafeEnchantment(ench, lvl);
				}
				setEquipment(ci,event.getEntity());
			}
			if(type.equalsIgnoreCase("set")){
				int e = 7,l = 6;
				CraftItemStack ci = new Set(mat,plugin);
				for(;e>0;e--){
					int lvl = plugin.gen.nextInt(l+1);
					Enchantment ench = drops.enchant();
					if(lvl!=0&&ench!=null)ci.addUnsafeEnchantment(ench, lvl);
				}
				setEquipment(ci,event.getEntity());
			}
		}
	}
	public void setEquipment(CraftItemStack ci, Entity e){
		Material mat = ci.getType();
		EntityLiving ev = ((CraftLivingEntity) e).getHandle();
		if(drops.isBoots(mat)){
			ev.setEquipment(4, ci.getHandle());
		}else if(drops.isChestPlate(mat)){
			ev.setEquipment(3, ci.getHandle());
		}else if(drops.isLeggings(mat)){
			ev.setEquipment(2, ci.getHandle());
		}else if(drops.isHelmet(mat)){
			ev.setEquipment(1, ci.getHandle());
		}else{
			ev.setEquipment(0, ci.getHandle());
		}
		
	}
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event){
		if(event.getEntity() instanceof Monster){
			Location loc = event.getEntity().getLocation();
			for(net.minecraft.server.ItemStack mItem:((CraftLivingEntity) event.getEntity()).getHandle().getEquipment()){
				if(mItem!=null){
					NBTTagCompound nc = mItem.tag.getCompound("display");
					if(nc!=null){
						String sg = nc.getString("Name");
						if(sg!=null&&sg.contains(new Character((char) 167).toString())){
					        double xs = plugin.gen.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;
					        double ys = plugin.gen.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;
					        double zs = plugin.gen.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;
					        EntityItem entity = new EntityItem(((CraftWorld) loc.getWorld()).getHandle(), loc.getX() + xs, loc.getY() + ys, loc.getZ() + zs, mItem);
					        ((CraftWorld) loc.getWorld()).getHandle().addEntity(entity);							
						}
					}						
				}
			}
		}
	}
}
