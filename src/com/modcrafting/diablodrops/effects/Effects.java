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

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.entity.CraftZombie;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.modcrafting.diablodrops.DiabloDrops;

public class Effects
{
	// Why? Cause we can.
	/**
	 * Makes entity into baby
	 * 
	 * @param entity
	 */
	public static void makeBaby(LivingEntity e)
	{
		if (e instanceof Zombie)
		{
			CraftZombie z = (CraftZombie) e;
			if (!z.getHandle().isBaby())
				z.getHandle().setBaby(true);
		}
		if (e instanceof Villager)
		{
			if (((Villager) e).isAdult())
				((Villager) e).setBaby();
		}
		if (e instanceof Pig)
		{
			if (((Pig) e).isAdult())
				((Pig) e).setBaby();
		}
		if (e instanceof Cow)
		{
			if (((Cow) e).isAdult())
				((Cow) e).setBaby();
		}
		if (e instanceof Chicken)
		{
			if (((Chicken) e).isAdult())
				((Chicken) e).setBaby();
		}
		if (e instanceof Wolf)
		{
			if (((Wolf) e).isAdult())
				((Wolf) e).setBaby();
		}
		if (e instanceof Ocelot)
		{
			if (((Ocelot) e).isAdult())
				((Ocelot) e).setBaby();
		}
		if (e instanceof Sheep)
		{
			if (((Sheep) e).isAdult())
				((Sheep) e).setBaby();
		}
	}

	/**
	 * Add potion effect to entity
	 * 
	 * @param entity
	 * @param potion
	 *            type
	 * @param duration
	 */
	public static void potionEffect(LivingEntity e, PotionEffectType ef, int dur)
	{
		e.addPotionEffect(new PotionEffect(ef, dur, 2));
	}

	/**
	 * Change the speed of an entity
	 * 
	 * @param entity
	 * @param speed
	 *            modifier
	 */
	public static void speed(LivingEntity e, Float sp)
	{
		try
		{
			EntityLiving a = ((CraftLivingEntity) e).getHandle();
			if (a instanceof EntityCreature)
			{
				EntityCreature le = (EntityCreature) a;
				Field fGoalSelector = EntityLiving.class
						.getDeclaredField("goalSelector");
				fGoalSelector.setAccessible(true);
				PathfinderGoalSelector gs = new PathfinderGoalSelector(
						((CraftWorld) e.getWorld()).getHandle() != null
								&& ((CraftWorld) e.getWorld()).getHandle().methodProfiler != null ? ((CraftWorld) e
								.getWorld()).getHandle().methodProfiler : null);
				gs.a(0, new PathfinderGoalFloat(le));
				gs.a(1, new PathfinderGoalBreakDoor(le));
				gs.a(2, new PathfinderGoalMeleeAttack(le, EntityHuman.class,
						sp, false));
				gs.a(3, new PathfinderGoalMeleeAttack(le, EntityVillager.class,
						sp, true));
				gs.a(4, new PathfinderGoalMoveTowardsRestriction(le, sp));
				gs.a(5, new PathfinderGoalMoveThroughVillage(le, sp, false));
				gs.a(6, new PathfinderGoalRandomStroll(le, sp));
				gs.a(7, new PathfinderGoalLookAtPlayer(le, EntityHuman.class,
						15.0F));
				gs.a(7, new PathfinderGoalRandomLookaround(le));
				fGoalSelector.set(le, gs);
			}
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
	}

	/**
	 * Strikes lightning on location a specified number of times
	 * 
	 * @param location
	 *            to strike
	 * @param times
	 *            to strike
	 */
	public static void strikeLightning(final Location location, final int times)
	{
		final World world = location.getWorld();
		for (int i = times; i > 0; i--)
		{
			Bukkit.getServer()
					.getScheduler()
					.scheduleSyncDelayedTask(DiabloDrops.getInstance(),
							new Runnable()
							{
								@Override
								public void run()
								{
									world.strikeLightning(location);
								}
							}, 20L * i);
		}
	}

	/**
	 * Set entity on fire for specified value of time
	 * 
	 * @param entity
	 *            to set on fire
	 * @param value
	 *            of time
	 */
	public static void setOnFire(LivingEntity entity, int value)
	{
		entity.setFireTicks(20 * 3 * Math.abs(value));
	}
}
