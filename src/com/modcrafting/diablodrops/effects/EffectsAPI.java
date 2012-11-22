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
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.modcrafting.toolapi.lib.Tool;

public class EffectsAPI
{

	public static void handlePluginEffects(LivingEntity entityStruck,
			LivingEntity entityStriker, EntityDamageByEntityEvent event)
	{
		if (!(entityStruck instanceof Player)
				&& !(entityStriker instanceof Player))
			return;
		boolean struckIsPlayer = (entityStruck instanceof Player);
		Player struck = null;
		if (struckIsPlayer)
			struck = (Player) entityStruck;
		boolean strikerIsPlayer = (entityStriker instanceof Player);
		Player striker = null;
		if (strikerIsPlayer)
			striker = (Player) entityStriker;
		List<ItemStack> struckEquipment = new ArrayList<ItemStack>();
		if (struck != null)
		{
			struckEquipment.addAll(Arrays.asList(struck.getInventory()
					.getArmorContents()));
		}
		List<ItemStack> strikerEquipment = new ArrayList<ItemStack>();
		if (striker != null)
		{
			strikerEquipment.add(striker.getItemInHand());
		}
		handleStruckEffects(entityStruck, entityStriker, struckEquipment, event);
		handleStrikerEffects(entityStruck, entityStriker, strikerEquipment,
				event);
	}

	private static void handleStrikerEffects(LivingEntity entityStruck,
			LivingEntity entityStriker, List<ItemStack> strikerEquipment,
			EntityDamageByEntityEvent event)
	{
		Set<Tool> toolSet = new HashSet<Tool>();
		for (ItemStack is : strikerEquipment)
		{
			if (is != null && !is.getType().equals(Material.AIR))
			{
				toolSet.add(new Tool((CraftItemStack) is));
			}
		}
		for (Tool tool : toolSet)
		{
			for (String string : tool.getLoreList())
			{
				string = ChatColor.stripColor(string).replace("%", "")
						.replace("+", "");
				addStrikerEffect(entityStruck, entityStriker, tool, string,
						event);
			}
		}
	}

	private static void addStrikerEffect(LivingEntity struck,
			LivingEntity striker, Tool tool, String string,
			EntityDamageByEntityEvent event)
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
			level = new Integer(0);
		}
		if (args[1].equalsIgnoreCase("damage"))
		{
			// change damage of event
			int damage = event.getDamage() + level.intValue();
			if (damage >= 0)
			{
				event.setDamage(damage);
			}
			else
			{
				event.setDamage(0);
			}
		}
		else if (args[1].equalsIgnoreCase("frenzy"))
		{
			// frenzy (speed up) entities
			Float fl;
			try
			{
				fl = Float.valueOf(args[0]);
			}
			catch (NumberFormatException e)
			{
				fl = new Float(0);
			}
			if (fl > 0)
				Effects.speed(struck, Math.abs(fl.floatValue()) / 100);
			else if (fl < 0)
				Effects.speed(striker, Math.abs(fl.floatValue()) / 100);
		}
		else if (args[1].equalsIgnoreCase("freeze"))
		{
			// freeze entities
			Float fl;
			try
			{
				fl = Float.valueOf(args[0]);
			}
			catch (NumberFormatException e)
			{
				fl = new Float(0);
			}
			if (fl > 0)
				Effects.speed(struck, Math.abs(fl.floatValue()) / 500);
			else if (fl < 0)
				Effects.speed(striker, Math.abs(fl.floatValue()) / 500);
		}
		else if (args[1].equalsIgnoreCase("shrink"))
		{
			// turn into baby
			Effects.makeBaby(struck);
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
		}
		else if (args[1].equalsIgnoreCase("fire"))
		{
			// Set entity on fire
			if (level.intValue() > 0)
				Effects.setOnFire(struck, Math.abs(level.intValue()));
			else if (level.intValue() < 0)
				Effects.setOnFire(striker, Math.abs(level.intValue()));
		}
		else if (args[1].equalsIgnoreCase("leech"))
		{
			if (level.intValue() > 0)
			{
				// Take from struck and give to striker
				int struckHealth = struck.getHealth();
				int struckNewHealth = struckHealth - Math.abs(level.intValue());
				if (struckNewHealth < 0)
					struckNewHealth = 0;
				if (struckNewHealth > 20)
					struckNewHealth = 20;
				struck.setHealth(struckNewHealth);
				int strikerHealth = striker.getHealth();
				int strikerNewHealth = strikerHealth
						+ Math.abs(level.intValue());
				if (strikerNewHealth < 0)
					strikerNewHealth = 0;
				if (strikerNewHealth > 20)
					strikerNewHealth = 20;
				striker.setHealth(strikerNewHealth);
			}
			else if (level.intValue() < 0)
			{
				// Take from striker and give to struck
				int strikerHealth = striker.getHealth();
				int strikerNewHealth = strikerHealth
						- Math.abs(level.intValue());
				if (strikerNewHealth < 0)
					strikerNewHealth = 0;
				if (strikerNewHealth > 20)
					strikerNewHealth = 20;
				striker.setHealth(strikerNewHealth);
				int struckHealth = struck.getHealth();
				int struckNewHealth = struckHealth + Math.abs(level.intValue());
				if (struckNewHealth < 0)
					struckNewHealth = 0;
				if (struckNewHealth > 20)
					struckNewHealth = 20;
				struck.setHealth(struckNewHealth);
			}
			else
			{
				for (PotionEffectType potionEffect : PotionEffectType.values())
				{
					if (potionEffect == null)
						return;
					if (!potionEffect.getName().equalsIgnoreCase(args[1]))
						continue;
					if (level.intValue() > 0)
					{
						struck.addPotionEffect(new PotionEffect(potionEffect,
								Math.abs(level.intValue()) * 100, Math
										.abs(level.intValue())));
					}
					else if (level.intValue() < 0)
					{
						striker.addPotionEffect(new PotionEffect(potionEffect,
								Math.abs(level.intValue()) * 100, Math
										.abs(level.intValue())));
					}
				}
			}
		}
	}

	private static void handleStruckEffects(LivingEntity entityStruck,
			LivingEntity entityStriker, List<ItemStack> struckEquipment,
			EntityDamageByEntityEvent event)
	{
		Set<Tool> toolSet = new HashSet<Tool>();
		for (ItemStack is : struckEquipment)
		{
			if (is != null && !is.getType().equals(Material.AIR))
			{
				toolSet.add(new Tool((CraftItemStack) is));
			}
		}
		for (Tool tool : toolSet)
		{
			for (String string : tool.getLoreList())
			{
				string = ChatColor.stripColor(string).replace("%", "")
						.replace("+", "");
				addStruckEffect(entityStruck, entityStriker, tool, string,
						event);
			}
		}
	}

	private static void addStruckEffect(LivingEntity struck,
			LivingEntity striker, Tool tool, String string,
			EntityDamageByEntityEvent event)
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
			level = new Integer(0);
		}
		if (args[1].equalsIgnoreCase("damage"))
		{
			// change damage of event
			int damage = event.getDamage() + level.intValue();
			if (damage >= 0)
			{
				event.setDamage(damage);
			}
			else
			{
				event.setDamage(0);
			}
		}
		else if (args[1].equalsIgnoreCase("frenzy"))
		{
			// frenzy (speed up) entities
			Float fl;
			try
			{
				fl = Float.valueOf(args[0]);
			}
			catch (NumberFormatException e)
			{
				fl = new Float(0);
			}
			if (fl > 0)
				Effects.speed(striker, Math.abs(fl.floatValue()) / 100);
			else if (fl < 0)
				Effects.speed(struck, Math.abs(fl.floatValue()) / 100);
		}
		else if (args[1].equalsIgnoreCase("freeze"))
		{
			// freeze entities
			Float fl;
			try
			{
				fl = Float.valueOf(args[0]);
			}
			catch (NumberFormatException e)
			{
				fl = new Float(0);
			}
			if (fl > 0)
				Effects.speed(striker, Math.abs(fl.floatValue()) / 500);
			else if (fl < 0)
				Effects.speed(struck, Math.abs(fl.floatValue()) / 500);
		}
		else if (args[1].equalsIgnoreCase("shrink"))
		{
			// turn into baby
			Effects.makeBaby(striker);
		}
		else if (args[1].equalsIgnoreCase("lightning"))
		{
			// strike lightning
			if (level.intValue() > 0)
				Effects.strikeLightning(striker.getLocation(),
						Math.abs(level.intValue()));
			else if (level.intValue() < 0)
				Effects.strikeLightning(struck.getLocation(),
						Math.abs(level.intValue()));
		}
		else if (args[1].equalsIgnoreCase("fire"))
		{
			// Set entity on fire
			if (level.intValue() > 0)
				Effects.setOnFire(striker, Math.abs(level.intValue()));
			else if (level.intValue() < 0)
				Effects.setOnFire(struck, Math.abs(level.intValue()));
		}
		else if (args[1].equalsIgnoreCase("leech"))
		{
			if (level.intValue() > 0)
			{
				// Take from striker and give to struck
				int strikerHealth = striker.getHealth();
				int strikerNewHealth = strikerHealth
						- Math.abs(level.intValue());
				if (strikerNewHealth < 0)
					strikerNewHealth = 0;
				if (strikerNewHealth > 20)
					strikerNewHealth = 20;
				striker.setHealth(strikerNewHealth);
				int struckHealth = struck.getHealth();
				int struckNewHealth = struckHealth + Math.abs(level.intValue());
				if (struckNewHealth < 0)
					struckNewHealth = 0;
				if (struckNewHealth > 20)
					struckNewHealth = 20;
				struck.setHealth(struckNewHealth);
			}
			else if (level.intValue() < 0)
			{
				// Take from struck and give to striker
				int struckHealth = struck.getHealth();
				int struckNewHealth = struckHealth - Math.abs(level.intValue());
				if (struckNewHealth < 0)
					struckNewHealth = 0;
				if (struckNewHealth > 20)
					struckNewHealth = 20;
				struck.setHealth(struckNewHealth);
				int strikerHealth = striker.getHealth();
				int strikerNewHealth = strikerHealth
						+ Math.abs(level.intValue());
				if (strikerNewHealth < 0)
					strikerNewHealth = 0;
				if (strikerNewHealth > 20)
					strikerNewHealth = 20;
				striker.setHealth(strikerNewHealth);
			}
			else
			{
				for (PotionEffectType potionEffect : PotionEffectType.values())
				{
					if (potionEffect == null)
						return;
					if (!potionEffect.getName().equalsIgnoreCase(args[1]))
						continue;
					if (level.intValue() > 0)
					{
						striker.addPotionEffect(new PotionEffect(potionEffect,
								Math.abs(level.intValue()) * 100, Math
										.abs(level.intValue())));
					}
					else if (level.intValue() < 0)
					{
						struck.addPotionEffect(new PotionEffect(potionEffect,
								Math.abs(level.intValue()) * 100, Math
										.abs(level.intValue())));
					}
				}
			}
		}
	}
}
