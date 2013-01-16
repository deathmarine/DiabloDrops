package com.modcrafting.diablodrops.effects;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.modcrafting.diablodrops.DiabloDrops;

public class EffectsUtil
{
    /**
     * Entombs the entity in a variety of blocks: 1 - Glass; 2 - Ice; 3 - Dirt;
     * 4 - Cobblestone; 5 - Stone; 6 - Brick; 7 - Stone Brick; 8 - Iron Bars; 9
     * - Ender Chest; 10 - Obsidian
     * 
     * @param loc
     *            Location at which to create the entombment
     * @param value
     *            Block variety
     */
    public static void entomb(final Location loc, final int value)
    {
        final HashMap<Location, Material> atLoc = new HashMap<Location, Material>();
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
                                    Location l = new Location(world, x
                                            + (dx * r), y + (dy * r), z
                                            + (dz * r));
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
                                Block block1 = world.getBlockAt(x_o,
                                        vertex[0].getBlockY(), z_o);
                                atLoc.put(block1.getLocation(),
                                        block1.getType());
                                entombBlockType(block1, value);
                                Block block2 = world.getBlockAt(x_o,
                                        vertex[2].getBlockY(), z_o);
                                atLoc.put(block2.getLocation(),
                                        block2.getType());
                                entombBlockType(block2, value);
                            }
                        }
                        for (int y_o = vertex[0].getBlockY(); y_o <= vertex[2]
                                .getBlockY(); y_o++)
                        {
                            for (int z_o = vertex[0].getBlockZ(); z_o <= vertex[1]
                                    .getBlockZ(); z_o++)
                            {
                                Block block1 = world.getBlockAt(
                                        vertex[0].getBlockX(), y_o, z_o);
                                atLoc.put(block1.getLocation(),
                                        block1.getType());
                                entombBlockType(world.getBlockAt(
                                        vertex[0].getBlockX(), y_o, z_o), value);
                                Block block2 = world.getBlockAt(
                                        vertex[5].getBlockX(), y_o, z_o);
                                atLoc.put(block2.getLocation(),
                                        block2.getType());
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
                                Block block1 = world.getBlockAt(x_o, y_o,
                                        vertex[0].getBlockZ());
                                atLoc.put(block1.getLocation(),
                                        block1.getType());
                                entombBlockType(
                                        world.getBlockAt(x_o, y_o,
                                                vertex[0].getBlockZ()), value);
                                Block block2 = world.getBlockAt(x_o, y_o,
                                        vertex[5].getBlockZ());
                                atLoc.put(block2.getLocation(),
                                        block2.getType());
                                entombBlockType(
                                        world.getBlockAt(x_o, y_o,
                                                vertex[5].getBlockZ()), value);
                            }
                        }
                    }
                }, 20L * 1);
        Bukkit.getScheduler().scheduleSyncDelayedTask(
                DiabloDrops.getInstance(), new Runnable()
                {
                    @Override
                    public void run()
                    {
                        for (Location loc : atLoc.keySet())
                        {
                            loc.getBlock().setType(atLoc.get(loc));
                        }
                    }
                }, 20L * 10);
    }

    private static void entombBlockType(final Block block, final int value)
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

    private static Method getMethod(Class<?> cl, String method)
    {
        for (Method m : cl.getMethods())
            if (m.getName().equals(method))
                return m;
        return null;
    }

    /**
     * Launch entity into the air with an acceleration of 2 times value
     * 
     * @param entity
     *            Entity to launch into the air
     * @param value
     *            Acceleration of entity
     */
    public static void launchEntity(final LivingEntity entity, final int value)
    {
        entity.setVelocity(new Vector(0, 2 * value, 0));
    }

    /**
     * Makes entity into baby
     * 
     * @param e
     *            Entity to make into baby
     */
    public static void makeBaby(final LivingEntity e)
    {
        if (e instanceof Zombie)
        {
            Zombie z = (Zombie) e;
            if (!z.isBaby())
            {
                z.setBaby(true);
            }
        }
        else if (e instanceof Villager)
        {
            if (((Villager) e).isAdult())
            {
                ((Villager) e).setBaby();
            }
        }
        else if (e instanceof Pig)
        {
            if (((Pig) e).isAdult())
            {
                ((Pig) e).setBaby();
            }
        }
        else if (e instanceof Cow)
        {
            if (((Cow) e).isAdult())
            {
                ((Cow) e).setBaby();
            }
        }
        else if (e instanceof Chicken)
        {
            if (((Chicken) e).isAdult())
            {
                ((Chicken) e).setBaby();
            }
        }
        else if (e instanceof Wolf)
        {
            if (((Wolf) e).isAdult())
            {
                ((Wolf) e).setBaby();
            }
        }
        else if (e instanceof Ocelot)
        {
            if (((Ocelot) e).isAdult())
            {
                ((Ocelot) e).setBaby();
            }
        }
        else  if (e instanceof Sheep)
        {
            if (((Sheep) e).isAdult())
            {
                ((Sheep) e).setBaby();
            }
        }
    }

    /**
     * Explodes random firework on location
     * 
     * @param loc
     *            Location to explode
     */
    public static void playFirework(Location loc)
    {
        Random gen = DiabloDrops.getInstance().getSingleRandom();
        try
        {
            Firework fw = loc.getWorld().spawn(loc, Firework.class);
            Method d0 = getMethod(loc.getWorld().getClass(), "getHandle");
            Method d2 = getMethod(fw.getClass(), "getHandle");
            Object o3 = d0.invoke(loc.getWorld(), (Object[]) null);
            Object o4 = d2.invoke(fw, (Object[]) null);
            Method d1 = getMethod(o3.getClass(), "broadcastEntityEffect");
            FireworkMeta data = fw.getFireworkMeta();
            data.addEffect(FireworkEffect
                    .builder()
                    .with(FireworkEffect.Type.values()[gen
                            .nextInt(FireworkEffect.Type.values().length)])
                    .flicker(gen.nextBoolean())
                    .trail(gen.nextBoolean())
                    .withColor(
                            Color.fromRGB(gen.nextInt(255), gen.nextInt(255),
                                    gen.nextInt(255)))
                    .withFade(
                            Color.fromRGB(gen.nextInt(255), gen.nextInt(255),
                                    gen.nextInt(255))).build());
            fw.setFireworkMeta(data);
            d1.invoke(o3, new Object[] { o4, (byte) 17 });
            fw.remove();
        }
        catch (Exception ex)
        {
            // not a Beta1.4.6R0.2 Server
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
    public static void potionEffect(final LivingEntity e,
            final PotionEffectType ef, final int dur)
    {
        e.addPotionEffect(new PotionEffect(ef, dur, 2));
    }

    /**
     * Set entity on fire for specified value of time
     * 
     * @param entity
     *            Entity to set on fire
     * @param value
     *            Duration of time to be set on fire
     */
    public static void setOnFire(final LivingEntity entity, final int value)
    {
        entity.setFireTicks(20 * 3 * Math.abs(value));
    }

    /**
    * Strike lightning on an entities location a number of times.
    *
    * @param entity LivingEntity to strike
    * @param times Number of times to strike
    */
    public static void strikeLightning(final LivingEntity entity,
            final int times)
    {
        entity.getWorld().strikeLightning(entity.getLocation());
        int amt = times - 1;
        for (int x = 1; x <= amt; x++)
        {
            Bukkit.getScheduler().scheduleSyncDelayedTask(
                    DiabloDrops.getInstance(), new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            entity.getWorld().strikeLightning(
                                    entity.getLocation());
                        }
                    }, 20L * x);
        }
    }
}
