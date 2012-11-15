package com.modcrafting.diablodrops.listeners;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.effects.Effects;
import com.modcrafting.toolapi.lib.Tool;

public class EffectsListener implements Listener
{
	DiabloDrops plugin;

	public EffectsListener(DiabloDrops instance)
	{
		plugin = instance;
	}
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event)
	{
		if(event instanceof EntityDamageByEntityEvent){
			EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
			if(e.getEntity() instanceof Player)
			{
				Player player = (Player) e.getEntity();
				Set<Tool> toolSet = new HashSet<Tool>();
				for (ItemStack is : player.getInventory().getArmorContents())
				{
					if (is != null && !is.getType().equals(Material.AIR))
						toolSet.add(new Tool((CraftItemStack) is));
				}
				if(player.getItemInHand()!=null)
					toolSet.add(new Tool((CraftItemStack) player.getItemInHand()));
				for (Tool tool : toolSet)
				{

					for (String string : tool
							.getLoreList())
					{
						string = ChatColor.stripColor(string).replace("%", "")
								.replace("+", "");
						
						addEffect(player,string,e);
					}
				}
			}
			if (e.getDamager() instanceof Player)
			{
				Player player = (Player) e.getDamager();
				Set<Tool> toolSet = new HashSet<Tool>();
				for (ItemStack is : player.getInventory().getArmorContents())
				{
					if (is != null && !is.getType().equals(Material.AIR))
						toolSet.add(new Tool((CraftItemStack) is));
				}
				if(player.getItemInHand()!=null)
					toolSet.add(new Tool((CraftItemStack) player.getItemInHand()));
				for (Tool tool : toolSet)
				{
					for (String string : tool.getLoreList())
					{
						string = ChatColor.stripColor(string).replace("%", "")
								.replace("+", "");
						
						addEffect(player,string,e);
						
						
					}
				}
			}
		}else{
			if(event.getEntity() instanceof Player)
			{
				Player player = (Player) event.getEntity();
				Set<Tool> toolSet = new HashSet<Tool>();
				for (ItemStack is : player.getInventory().getArmorContents())
				{
					if (is != null && !is.getType().equals(Material.AIR))
						toolSet.add(new Tool((CraftItemStack) is));
				}
				if(player.getItemInHand()!=null)
					toolSet.add(new Tool((CraftItemStack) player.getItemInHand()));
				for (Tool tool : toolSet)
				{

					for (String string : tool
							.getLoreList())
					{
						string = ChatColor.stripColor(string).replace("%", "")
								.replace("+", "");
						
						addEffect(player,string,event);
					}
				}
			}
		}
		
	}
		
	public void addEffect(Player player,String string,EntityDamageEvent event){
		if (StringUtils.containsIgnoreCase(string, "damage"))

		{
			String[] args = string.split(" ");
			int dam = event.getDamage();
			try
			{
				dam = dam + Integer.valueOf(args[0]);
			}
			catch (NumberFormatException nfe)
			{
				dam = event.getDamage();
			}
			event.setDamage(dam);
		}
		else if (StringUtils.containsIgnoreCase(string,
				"durability"))
		{
			/* Finish this later */
			String[] args = string.split(" ");
			player.getItemInHand()
					.setDurability(
							(short) (player.getItemInHand()
									.getDurability() * 0.5));
		}
		else if (StringUtils.containsIgnoreCase(string, "money"))
		{
			/* Finish this later */
			String[] args = string.split(" ");
			// Hook into MobBounty maybe hehe....
		}
		else if (StringUtils.containsIgnoreCase(string, "frenzy"))
		{
			// Example: "50% Mob Frenzy"
			if (event.getEntity() instanceof LivingEntity)
			{
				String[] args = string.split(" ");
				Effects.speed(
						(LivingEntity) event.getEntity(),
						Float.valueOf(args[0].replace("%", "")) / 100);
				// Float value 50/100=0.50 default 0.23
			}
		}
		else if (StringUtils.containsIgnoreCase(string, "freeze"))
		{
			// Example: "50% Mob Freeze" - Tested Good.
			if (event.getEntity() instanceof LivingEntity)
			{
				String[] args = string.split(" ");
				String speed = args[0];
				if (speed.contains("%"))
					speed = args[0].replace("%", "");
				Effects.speed((LivingEntity) event.getEntity(),
						Float.valueOf(speed) / 500);
				// Float value 50/500=0.10 default 0.23
			}
		}
		else if (StringUtils.containsIgnoreCase(string, "shrink"))
		{
			if (event.getEntity() instanceof LivingEntity)
			{
				Effects.makeBaby((LivingEntity) event.getEntity());
			}
		}
		else if (StringUtils
				.containsIgnoreCase(string, "lightning"))
		{
			String[] args = string.split(" ");
			Integer integer = null;
			try
			{
				integer = Integer.valueOf(args[0]);
			}
			catch (NumberFormatException e)
			{
			}
			if (integer != null)
			{
				int value = integer.intValue();
				if (value > 0)
					Effects.strikeLightning(event.getEntity()
							.getLocation(), value);
				else if (value < 0)
					Effects.strikeLightning(player.getLocation(),
							value);
			}
		}
		else if (StringUtils.containsIgnoreCase(string, "fire"))
		{
			String[] args = string.split(" ");
			Integer integer = null;
			try
			{
				integer = Integer.valueOf(args[0]);
			}
			catch (NumberFormatException e)
			{
			}
			if (integer != null)
			{
				int value = integer.intValue();
				if (value > 0)
					Effects.setOnFire(
							(LivingEntity) event.getEntity(),
							Math.abs(value));
				else if (value < 0)
					Effects.setOnFire(player, Math.abs(value));
			}
		}
		else if (StringUtils.containsIgnoreCase(string, "leech"))
		{
			String[] args = string.split(" ");
			Integer integer = null;
			try
			{
				integer = Integer.valueOf(args[0]);
			}
			catch (NumberFormatException e)
			{
			}
			if (integer != null)
			{
				int value = integer.intValue();
				Effects.leechLife((LivingEntity) event.getEntity(),
						player, value);
			}
		}
		for (PotionEffectType effect : PotionEffectType.values())
		{
			if (effect == null)
				continue;
			if (StringUtils.containsIgnoreCase(string,
					effect.getName()))
			{
				// Args (int duration, Invisibility)
				String[] args = ChatColor.stripColor(string).split(
						" ");
				if (event.getEntity() instanceof LivingEntity)
				{
					int i = 600;
					try
					{
						i = Integer.valueOf(args[0]) * 100;
					}
					catch (NumberFormatException nfe)
					{
						i = 600;
					}
					if (i < 0)
					{
						player.addPotionEffect(new PotionEffect(
								effect, Math.abs(i),
								Math.abs(i) / 100));
					}
					else
					{
						Effects.potionEffect(
								(LivingEntity) event.getEntity(),
								effect, i);
					}
				}
			}
		}
	}
}
