package com.modcrafting.diablodrops.listeners;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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

	@SuppressWarnings("unused")
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
	{
		if (event.getDamager() instanceof Player)
		{
			Player player = (Player) event.getDamager();
			for (String string : new Tool(((CraftItemStack) player.getItemInHand()).getHandle()).getLoreList())
			{
				if (StringUtils.containsIgnoreCase(string, "damage"))
				{
					String[] args = ChatColor.stripColor(string).split(" ");
					int dam = event.getDamage();
					try{
						dam = dam+Integer.valueOf(args[0]);
					}catch (NumberFormatException nfe){
						dam = event.getDamage();
					}
					event.setDamage(dam);
				}
				if (StringUtils.containsIgnoreCase(string, "durability"))
				{
					/* Finish this later */
					String[] args = ChatColor.stripColor(string).split(" ");
					player.getItemInHand().setDurability((short) (player.getItemInHand().getDurability()*0.5));
				}
				if (StringUtils.containsIgnoreCase(string, "money"))
				{
					/* Finish this later */
					String[] args = ChatColor.stripColor(string).split(" ");
					//Hook into MobBounty maybe hehe....
				}
				if (StringUtils.containsIgnoreCase(string, "frenzy"))
				{
					//Example: "50% Mob Frenzy"
					if(event.getEntity() instanceof LivingEntity)
					{
						String[] args = ChatColor.stripColor(string).split(" ");
						Effects.speed((LivingEntity) event.getEntity(), Float.valueOf(args[0].replace("%",""))/100);
						//Float value 50/100=0.50 default 0.23
					}
				}
				if (StringUtils.containsIgnoreCase(string, "freeze"))
				{
					//Example: "50% Mob Freeze" - Tested Good.
					if(event.getEntity() instanceof LivingEntity)
					{
						String[] args = ChatColor.stripColor(string).split(" ");
						String speed = args[0];
						if(speed.contains("%")) speed = args[0].replace("%","");
						Effects.speed((LivingEntity) event.getEntity(), Float.valueOf(speed)/500);
						//Float value 50/500=0.10 default 0.23
					}
				}
				if (StringUtils.containsIgnoreCase(string, "shrink"))
				{
					if(event.getEntity() instanceof LivingEntity)
					{
						Effects.makeBaby((LivingEntity) event.getEntity());
					}
				}
				for(PotionEffectType effect:PotionEffectType.values())
				{
					if(effect==null) continue;
					if (StringUtils.containsIgnoreCase(string, effect.getName()))
					{
						//Args (int duration, Invisibility) 
						String[] args = ChatColor.stripColor(string).split(" ");
						if(event.getEntity() instanceof LivingEntity)
						{
							int i = 600;
							try{
								i = Integer.valueOf(args[0])*100;
							}catch (NumberFormatException nfe){
								i = 600;
							}
							if(i<0){
								player.addPotionEffect(new PotionEffect(effect,Math.abs(i),Math.abs(i)/100));
							}else{
								Effects.potionEffect((LivingEntity) event.getEntity(), effect, i);
							}
							
						}
					}
				}
			}
		}
	}
}
