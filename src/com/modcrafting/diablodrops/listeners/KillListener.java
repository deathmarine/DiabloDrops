package com.modcrafting.diablodrops.listeners;

import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
	public void onEntityDeath(EntityDeathEvent event){
		LivingEntity entity = event.getEntity();
		if(entity instanceof Monster&&plugin.gen.nextInt(125)==0){
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
				event.getDrops().add(ci);
			}
			if(type.equalsIgnoreCase("lore")){
				int e = 7,l = 9;
				CraftItemStack ci = new Lore(mat,plugin);
				for(;e>0;e--){
					int lvl = plugin.gen.nextInt(l+1);
					Enchantment ench = drops.enchant();
					if(lvl!=0&&ench!=null)ci.addUnsafeEnchantment(ench, lvl);
				}
				event.getDrops().add(ci);
			}
			if(type.equalsIgnoreCase("magical")){
				int e = 3,l = 4;
				CraftItemStack ci = new Magical(mat,plugin);
				for(;e>0;e--){
					int lvl = plugin.gen.nextInt(l+1);
					Enchantment ench = drops.enchant();
					if(lvl!=0&&ench!=null)ci.addUnsafeEnchantment(ench, lvl);
				}
				event.getDrops().add(ci);
			}
			if(type.equalsIgnoreCase("rare")){
				int e = 5,l = 5;
				CraftItemStack ci = new Rare(mat,plugin);
				for(;e>0;e--){
					int lvl = plugin.gen.nextInt(l+1);
					Enchantment ench = drops.enchant();
					if(lvl!=0&&ench!=null)ci.addUnsafeEnchantment(ench, lvl);
				}
				event.getDrops().add(ci);
			}
			if(type.equalsIgnoreCase("set")){
				int e = 7,l = 6;
				CraftItemStack ci = new Set(mat,plugin);
				for(;e>0;e--){
					int lvl = plugin.gen.nextInt(l+1);
					Enchantment ench = drops.enchant();
					if(lvl!=0&&ench!=null)ci.addUnsafeEnchantment(ench, lvl);
				}
				event.getDrops().add(ci);
			}
		}
	}
}
