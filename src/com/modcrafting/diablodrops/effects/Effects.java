package com.modcrafting.diablodrops.effects;

import java.lang.reflect.Field;

import net.minecraft.server.EntityCreature;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityVillager;
import net.minecraft.server.PathfinderGoalBreakDoor;
import net.minecraft.server.PathfinderGoalFloat;
import net.minecraft.server.PathfinderGoalLookAtPlayer;
import net.minecraft.server.PathfinderGoalMeleeAttack;
import net.minecraft.server.PathfinderGoalMoveThroughVillage;
import net.minecraft.server.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.PathfinderGoalRandomLookaround;
import net.minecraft.server.PathfinderGoalRandomStroll;
import net.minecraft.server.PathfinderGoalSelector;

import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
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
	public static void potionEffect(LivingEntity e,PotionEffectType ef,int dur){
		e.addPotionEffect(new PotionEffect(ef, dur, 2)); 
	}
	public static void speed(LivingEntity e,Float sp){
        try {
            EntityLiving a = ((CraftLivingEntity) e).getHandle();
            if(a instanceof EntityCreature){
            	EntityCreature le = (EntityCreature) a;
        		Field fGoalSelector = EntityLiving.class.getDeclaredField("goalSelector");
                fGoalSelector.setAccessible(true);
                PathfinderGoalSelector gs = new PathfinderGoalSelector(((CraftWorld) e.getWorld()).getHandle() != null && ((CraftWorld) e.getWorld()).getHandle().methodProfiler != null ? ((CraftWorld) e.getWorld()).getHandle().methodProfiler : null);
                gs.a(0, new PathfinderGoalFloat(le));
                gs.a(1, new PathfinderGoalBreakDoor(le));
                gs.a(2, new PathfinderGoalMeleeAttack(le, EntityHuman.class, sp, false));
                gs.a(3, new PathfinderGoalMeleeAttack(le, EntityVillager.class, sp, true));
                gs.a(4, new PathfinderGoalMoveTowardsRestriction(le, sp));
                gs.a(5, new PathfinderGoalMoveThroughVillage(le, sp, false));
                gs.a(6, new PathfinderGoalRandomStroll(le, sp));
                gs.a(7, new PathfinderGoalLookAtPlayer(le, EntityHuman.class, 15.0F));
                gs.a(7, new PathfinderGoalRandomLookaround(le));
        		fGoalSelector.set(le,gs);
            }
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	  
	}
}
