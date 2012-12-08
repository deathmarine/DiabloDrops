package com.modcrafting.diablodrops.effects;

import java.lang.reflect.Field;
import java.util.Random;

import net.minecraft.server.EntityCreature;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityVillager;
import net.minecraft.server.NBTTagCompound;
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
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.entity.CraftZombie;
import org.bukkit.entity.Arrow;
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
import org.bukkit.util.Vector;

import com.modcrafting.diablodrops.DiabloDrops;

public class EffectsUtil
{
    /**
     * Entombs the entity in a variety of blocks:
     * 1 - Glass;
     * 2 - Ice;
     * 3 - Dirt;
     * 4 - Cobblestone;
     * 5 - Stone;
     * 6 - Brick;
     * 7 - Stone Brick;
     * 8 - Iron Bars;
     * 9 - Ender Chest;
     * 10 - Obsidian
     * 
     * @param loc
     *            Location at which to create the entombment
     * @param value
     *            Block variety
     */
    public static void entomb(final Location loc, final int value)
    {
        Bukkit.getScheduler().scheduleSyncDelayedTask(
                DiabloDrops.getInstance(), new Runnable()
                {

                    @Override
                    public void run()
                    {
                        int r = 3;

                        World world = loc.getWorld();

                        int x = loc.getBlockX();
                        int y = loc.getBlockY();
                        int z = loc.getBlockZ();

                        Location[] vertex = new Location[8];
                        int i = 0;
                        for (int dx = -1; dx <= 1; dx += 2)
                        {
                            for (int dy = -1; dy <= 1; dy += 2)
                            {
                                for (int dz = -1; dz <= 1; dz += 2)
                                {
                                    Location l = new Location(world,
                                            x + dx * r, y + dy * r, z + dz * r);
                                    vertex[i++] = l;
                                }
                            }
                        }

                        for (int x_o = vertex[0].getBlockX(); x_o <= vertex[4]
                                .getBlockX(); x_o++)
                        {
                            for (int z_o = vertex[0].getBlockZ(); z_o <= vertex[1]
                                    .getBlockZ(); z_o++)
                            {
                                entombBlockType(
                                        world.getBlockAt(x_o,
                                                vertex[0].getBlockY(), z_o),
                                        value);
                                entombBlockType(
                                        world.getBlockAt(x_o,
                                                vertex[2].getBlockY(), z_o),
                                        value);
                            }
                        }
                        for (int y_o = vertex[0].getBlockY(); y_o <= vertex[2]
                                .getBlockY(); y_o++)
                        {
                            for (int z_o = vertex[0].getBlockZ(); z_o <= vertex[1]
                                    .getBlockZ(); z_o++)
                            {
                                entombBlockType(world.getBlockAt(
                                        vertex[0].getBlockX(), y_o, z_o), value);
                                entombBlockType(world.getBlockAt(
                                        vertex[5].getBlockX(), y_o, z_o), value);
                            }
                        }

                        for (int x_o = vertex[0].getBlockX(); x_o <= vertex[4]
                                .getBlockX(); x_o++)
                        {
                            for (int y_o = vertex[0].getBlockY(); y_o <= vertex[6]
                                    .getBlockY(); y_o++)
                            {
                                entombBlockType(
                                        world.getBlockAt(x_o, y_o,
                                                vertex[0].getBlockZ()), value);
                                entombBlockType(
                                        world.getBlockAt(x_o, y_o,
                                                vertex[5].getBlockZ()), value);
                            }
                        }
                    }
                }, 20L * 1);
    }

    private static void entombBlockType(Block block, int value)
    {
        switch (value)
        {
            case 1:
                block.setTypeIdAndData(20, (byte) 0, false);
                break;
            case 2:
                block.setTypeIdAndData(79, (byte) 0, false);
                break;
            case 3:
                block.setTypeIdAndData(3, (byte) 0, false);
                break;
            case 4:
                block.setTypeIdAndData(4, (byte) 0, false);
                break;
            case 5:
                block.setTypeIdAndData(1, (byte) 0, false);
                break;
            case 6:
                block.setTypeIdAndData(45, (byte) 0, false);
                break;
            case 7:
                block.setTypeIdAndData(98, (byte) new Random().nextInt(4),
                        false);
                break;
            case 8:
                block.setTypeIdAndData(101, (byte) 0, false);
                break;
            case 9:
                block.setTypeIdAndData(130, (byte) 0, false);
                break;
            case 10:
                block.setTypeIdAndData(49, (byte) 0, false);
                break;
            default:
                block.setTypeIdAndData(1, (byte) 0, false);
                break;
        }
    }

    /**
     * Launch entity into the air with an acceleration of 2 times value
     * 
     * @param entity
     *            Entity to launch into the air
     * @param value
     *            Acceleration of entity
     */
    public static void launchEntity(LivingEntity entity, int value)
    {
        entity.setVelocity(new Vector(0, 2 * value, 0));
    }
    
    /**
     * Makes entity into baby
     * 
     * @param e
     *            Entity to make into baby
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
     * Add PotionEffect to entity
     * 
     * @param e
     *            Entity receiving the PotionEffect
     * @param ef
     *            Type of PotionEffect to apply to entity
     * @param dur
     *            Duration of PotionEffect
     */
    public static void potionEffect(LivingEntity e, PotionEffectType ef, int dur)
    {
        e.addPotionEffect(new PotionEffect(ef, dur, 2));
    }

    /**
     * Set entity on fire for specified value of time
     * 
     * @param entity
     *            Entity
     *            to set on fire
     * @param value
     *            Duration
     *            of time to be set on fire
     */
    public static void setOnFire(LivingEntity entity, int value)
    {
        entity.setFireTicks(20 * 3 * Math.abs(value));
    }

    /**
     * Change the speed of an entity
     * 
     * @param e
     *            Entity to change the speed of
     * @param sp
     *            Speed
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
            if (DiabloDrops.getInstance().debug)
                DiabloDrops.getInstance().log.warning(e1.getMessage());
            e1.printStackTrace();
        }
    }

    /**
     * Launches an arrow from the entity an amount of times equal to value
     * 
     * @param entity
     * @param value
     */
    public static void spine(final LivingEntity entity, final int value)
    {
        for (int i = value; i > 0; i--)
        {
            Bukkit.getServer()
                    .getScheduler()
                    .scheduleSyncDelayedTask(DiabloDrops.getInstance(),
                            new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    entity.launchProjectile(Arrow.class);
                                }
                            }, 20L * i);
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
     * Sets bleed time for an entity
     * @param le
     * @param i
     */
    public static void bleed(LivingEntity le,short i){
    	EntityLiving el = ((CraftLivingEntity) le).getHandle();
    	NBTTagCompound nbt = new NBTTagCompound();
    	el.b(nbt);
    	if(nbt.hasKey("HurtTime")){
    		nbt.setShort("HurtTime", i);
    	}
    	el.a(nbt);
    	
    }
}
