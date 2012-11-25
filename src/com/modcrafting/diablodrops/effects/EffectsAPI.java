package com.modcrafting.diablodrops.effects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.modcrafting.toolapi.lib.Tool;

public class EffectsAPI
{
	/**
	 * Handles any effects caused by an EntityDamageByEntityEvent
	 * 
	 * @param entity
	 *            damaged by event
	 * @param entity
	 *            that caused the damage
	 * @param event
	 */
	public static void handlePluginEffects(LivingEntity entityStruck,
			LivingEntity entityStriker, EntityDamageByEntityEvent event)
	{
		if (entityStriker instanceof Player)
		{
			Player striker = (Player) entityStriker;
			List<ItemStack> strikerEquipment = new ArrayList<ItemStack>();
			strikerEquipment.add(striker.getItemInHand());
			for (String s : listEffects(strikerEquipment))
			{
				addEffect(entityStruck, entityStriker, s, event, true);
			}
		}
		if (entityStruck instanceof Player)
		{
			Player struck = (Player) entityStruck;
			List<ItemStack> struckEquipment = new ArrayList<ItemStack>();
			struckEquipment.addAll(Arrays.asList(struck.getInventory()
					.getArmorContents()));
			for (String s : listEffects(struckEquipment))
			{
				addEffect(entityStriker, entityStruck, s, event, true);
			}
		}
	}

	private static List<String> listEffects(List<ItemStack> equipment)
	{
		Set<Tool> toolSet = new HashSet<Tool>();
		for (ItemStack is : equipment)
		{
			if (is != null && !is.getType().equals(Material.AIR))
			{
				toolSet.add(new Tool((CraftItemStack) is));
			}
		}
		List<String> effects = new ArrayList<String>();
		for (Tool tool : toolSet)
		{
			for (String string : tool.getLoreList())
			{
				string = ChatColor.stripColor(string).replace("%", "")
						.replace("+", "");
				effects.add(string);
			}
		}
		return effects;
	}

	private static void addEffect(LivingEntity struck, LivingEntity striker,
			String string, EntityDamageByEntityEvent event, boolean strike)
	{

		String[] args = string.split(" ");
		if (args.length == 0 || args.length == 1)
			return;
		Integer level = null;
		try
		{
			level = Integer.valueOf(args[0]);
		}
		catch (NumberFormatException e)
		{
			level = 0;
		}
		if (args[1].equalsIgnoreCase("damage"))
		{
			// Add to strike damage
			int damage = event.getDamage() + level.intValue();
			if (damage >= 0)
			{
				event.setDamage(damage);
			}
			else
			{
				event.setDamage(0);
			}
			return;
		}
		else if (args[1].equalsIgnoreCase("defense"))
		{
			int damage = event.getDamage() - level.intValue();
			if (damage >= 0)
			{
				event.setDamage(damage);
			}
			else
			{
				event.setDamage(0);
			}
			return;
		}
		else if (args[1].equalsIgnoreCase("freeze"))
		{
			// freeze entities
			float fl;
			try
			{
				fl = Float.parseFloat(args[0]);
			}
			catch (NumberFormatException e)
			{
				return;
			}
			if (fl > 0 && struck instanceof Monster)
				Effects.speed(struck, Math.abs(fl) / 500);
			else if (fl < 0 && striker instanceof Monster)
				Effects.speed(striker, Math.abs(fl) / 500);
			return;
		}
		else if (args[1].equalsIgnoreCase("shrink"))
		{
			// turn into baby
			Effects.makeBaby(struck);
			return;
		}
		else if (args[1].equalsIgnoreCase("lightning"))
		{
			// strike lightning
			if (level.intValue() > 0)
				Effects.strikeLightning(struck.getLocation(),
						Math.abs(level.intValue()));
			else if (level.intValue() < 0)
				Effects.strikeLightning(striker.getLocation(),
						Math.abs(level.intValue()));
			return;
		}
		else if (args[1].equalsIgnoreCase("fire"))
		{
			// Set entity on fire
			if (level.intValue() > 0)
				Effects.setOnFire(struck, Math.abs(level.intValue()));
			else if (level.intValue() < 0)
				Effects.setOnFire(striker, Math.abs(level.intValue()));
			return;
		}
		else if (args[1].equalsIgnoreCase("leech"))
		{
			if (level.intValue() > 0)
			{
				int chng = level.intValue() - struck.getHealth();
				if (chng < struck.getMaxHealth() && chng > 0)
					struck.setHealth(chng);
				chng = level.intValue() + striker.getHealth();
				if (chng < striker.getMaxHealth() && chng > 0)
					striker.setHealth(chng);
			}
			else if (level.intValue() < 0)
			{
				int chng = level.intValue() + struck.getHealth();
				if (chng < struck.getMaxHealth() && chng > 0)
					struck.setHealth(chng);
				chng = level.intValue() - striker.getHealth();
				if (chng < striker.getMaxHealth() && chng > 0)
					striker.setHealth(chng);
			}
			return;
		}
		else
		{
			for (PotionEffectType potionEffect : PotionEffectType.values())
			{
				if (potionEffect == null
						|| !potionEffect.getName().equalsIgnoreCase(args[1]))
					continue;
				if (level.intValue() > 0)
				{
					struck.addPotionEffect(new PotionEffect(potionEffect, Math
							.abs(level.intValue()) * 100, Math.abs(level
							.intValue())));
				}
				else if (level.intValue() < 0)
				{
					striker.addPotionEffect(new PotionEffect(potionEffect, Math
							.abs(level.intValue()) * 100, Math.abs(level
							.intValue())));
				}
			}
			return;
		}
	}
}
