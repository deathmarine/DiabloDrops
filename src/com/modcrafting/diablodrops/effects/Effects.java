package com.modcrafting.diablodrops.effects;

import org.bukkit.craftbukkit.entity.CraftZombie;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Effects {
	//Why? Cause we can.
	public static void makeBaby(LivingEntity e){
		if(e instanceof Zombie){
			CraftZombie z =(CraftZombie) e;
			if(!z.getHandle().isBaby())z.getHandle().setBaby(true);
		}
		if(e instanceof Villager){
			if(((Villager) e).isAdult())((Villager) e).setBaby();
		}
		if(e instanceof Pig){
			if(((Pig) e).isAdult())((Pig) e).setBaby();
		}
		if(e instanceof Cow){
			if(((Cow) e).isAdult())((Cow) e).setBaby();
		}
		if(e instanceof Chicken){
			if(((Chicken) e).isAdult())((Chicken) e).setBaby();
		}
		if(e instanceof Wolf){
			if(((Wolf) e).isAdult())((Wolf) e).setBaby();
		}
		if(e instanceof Ocelot){
			if(((Ocelot) e).isAdult())((Ocelot) e).setBaby();
		}
		if(e instanceof Sheep){
			if(((Sheep) e).isAdult())((Sheep) e).setBaby();
		}
	}
	public static void spawnBat(LivingEntity e){
		e.getLocation().getWorld().spawnEntity(e.getLocation(), EntityType.BAT);
	}
	public static void potionEffect(LivingEntity e,PotionEffectType ef){
		e.addPotionEffect(new PotionEffect(ef, 600, 2)); 
	}
}
