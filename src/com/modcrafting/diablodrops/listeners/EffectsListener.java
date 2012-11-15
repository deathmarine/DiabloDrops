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
import com.stirante.ItemNamer.Namer;

public class EffectsListener implements Listener
{
	DiabloDrops plugin;

	public EffectsListener(DiabloDrops instance)
	{
		plugin = instance;
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
	{

		if (event.getDamager() instanceof Player)
		{
			Player player = (Player) event.getDamager();
			for (String string : 
				//new Tool(((CraftItemStack) player.getItemInHand()).getHandle()).getLore())
				new Tool(((CraftItemStack) player.getItemInHand()).getHandle()).getLoreList())
				//Namer.getLore(player.getItemInHand()))
			{
				
				if (StringUtils.containsIgnoreCase(string, "damage"))
				{
					String[] args = ChatColor.stripColor(string).split(" ");
					int dam = event.getDamage();
					try{
						dam = dam*Integer.valueOf(args[0]);
					}catch (NumberFormatException nfe){
						dam = event.getDamage();
					}
					event.setDamage(dam);
				}
				if (string.contains("durability"))
				{
					/* Finish this later */
					String[] args = ChatColor.stripColor(string).split(" ");
					player.getItemInHand().setDurability((short) (player.getItemInHand().getDurability()*1.5));
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
					//Example: "50% Mob Frenzy"
					if(event.getEntity() instanceof LivingEntity){
						String[] args = ChatColor.stripColor(string).split(" ");
						Effects.speed((LivingEntity) event.getEntity(), Float.valueOf(args[0].replace("%",""))/100);
						//Float value 50/100=0.50 default 0.23
					}
				}
				if (StringUtils.containsIgnoreCase(string, "freeze")){
					//Example: "50% Mob Freeze"
					if(event.getEntity() instanceof LivingEntity){
						String[] args = ChatColor.stripColor(string).split(" ");
						Effects.speed((LivingEntity) event.getEntity(), Float.valueOf(args[0].replace("%",""))/500);
						//Float value 50/500=0.10 default 0.23
						player.sendMessage("Freeze");
					}
				}
			}
		}
	}
}
