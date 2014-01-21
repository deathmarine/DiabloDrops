package us.deathmarine.diablodrops.effects;

import java.lang.reflect.Method;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
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

import us.deathmarine.diablodrops.DiabloDrops;

public class EffectsUtil {

	private static Method getMethod(Class<?> cl, String method) {
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
	public static void launchEntity(final LivingEntity entity, final int value) {
		entity.setVelocity(new Vector(0, 2 * value, 0));
	}

	/**
	 * Makes entity into baby
	 * 
	 * @param e
	 *            Entity to make into baby
	 */
	public static void makeBaby(final LivingEntity e) {
		if (e instanceof Zombie) {
			Zombie z = (Zombie) e;
			if (!z.isBaby()) {
				z.setBaby(true);
			}
		} else if (e instanceof Villager) {
			if (((Villager) e).isAdult()) {
				((Villager) e).setBaby();
			}
		} else if (e instanceof Pig) {
			if (((Pig) e).isAdult()) {
				((Pig) e).setBaby();
			}
		} else if (e instanceof Cow) {
			if (((Cow) e).isAdult()) {
				((Cow) e).setBaby();
			}
		} else if (e instanceof Chicken) {
			if (((Chicken) e).isAdult()) {
				((Chicken) e).setBaby();
			}
		} else if (e instanceof Wolf) {
			if (((Wolf) e).isAdult()) {
				((Wolf) e).setBaby();
			}
		} else if (e instanceof Ocelot) {
			if (((Ocelot) e).isAdult()) {
				((Ocelot) e).setBaby();
			}
		} else if (e instanceof Sheep) {
			if (((Sheep) e).isAdult()) {
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
	public static void playFirework(Location loc) {
		Random gen = DiabloDrops.getInstance().getSingleRandom();
		try {
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
		} catch (Exception ex) {
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
			final PotionEffectType ef, final int dur) {
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
	public static void setOnFire(final LivingEntity entity, final int value) {
		entity.setFireTicks(20 * 3 * Math.abs(value));
	}
}
