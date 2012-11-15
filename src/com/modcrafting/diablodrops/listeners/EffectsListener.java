package com.modcrafting.diablodrops.listeners;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
			for (String string : new Tool(
					((CraftItemStack) player.getItemInHand()).getHandle())
					.getLore())
			{
				if (StringUtils.containsIgnoreCase(string, "damage"))
				{
					/* Finish this later */
					String[] args = ChatColor.stripColor(string).split(" ");
				}
				if (string.contains("durability"))
				{
					/* Finish this later */
					String[] args = ChatColor.stripColor(string).split(" ");
				}
				if (string.contains("money"))
				{
					/* Finish this later */
					String[] args = ChatColor.stripColor(string).split(" ");
					//Hook into MobBounty hehe....
				}
				if (StringUtils.containsIgnoreCase(string, "potion")){
					//Args (int amount, "Potion:", Invisiblity, int Duration) 
					String[] args = ChatColor.stripColor(string).split(" ");
					if(event.getEntity() instanceof LivingEntity){
						Effects.potionEffect((LivingEntity) event.getEntity(), PotionEffectType.getByName(args[2]), Integer.valueOf(args[3]));
					}
				}
				if (StringUtils.containsIgnoreCase(string, "frenzy")){
					//Example: "Mob Frenzy 50%"
					if(event.getEntity() instanceof LivingEntity){
						String[] args = ChatColor.stripColor(string).split(" ");
						Effects.speed((LivingEntity) event.getEntity(), Float.valueOf(args[2].replace("%",""))/100);
						//Float value 50/100=0.50 default 0.23
					}
				}
				if (StringUtils.containsIgnoreCase(string, "freeze")){
					//Example: "Mob Freeze 50%"
					if(event.getEntity() instanceof LivingEntity){
						String[] args = ChatColor.stripColor(string).split(" ");
						Effects.speed((LivingEntity) event.getEntity(), Float.valueOf(args[2].replace("%",""))/500);
						//Float value 50/500=0.10 default 0.23
					}
				}
			}
		}
	}
}
