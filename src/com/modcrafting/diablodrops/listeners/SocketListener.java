package com.modcrafting.diablodrops.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.ItemStack;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.socket.SocketBonus;
import com.modcrafting.diablodrops.socket.SocketBuilder.SocketType;
import com.modcrafting.skullapi.lib.Skull;
import com.modcrafting.toolapi.lib.Tool;

public class SocketListener implements Listener{
	DiabloDrops plugin;
	public SocketListener(DiabloDrops instance){
		plugin=instance;
	}
	@EventHandler
	public void onSmeltSocket(FurnaceSmeltEvent event){
		if(!plugin.furnanceMap.containsKey(event.getBlock())) return;
		ItemStack is = plugin.furnanceMap.remove(event.getBlock());
		Material fuel = is.getType();
		if(plugin.drop.isArmor(event.getResult().getType())){
			SocketBonus sb = plugin.bonuses.get(SocketType.ARMOR);
			//TODO: Determine Item Bonuses from SocketItemType and SocketType
			Tool tool = new Tool(event.getResult());
			Tool oldtool = new Tool(event.getSource());
			tool.addEnchantments(oldtool.getEnchantments());
			tool.setLore(plugin.lore.get(plugin.gen.nextInt(plugin.lore.size()))); //Testing code.
			if(fuel.equals(Material.SKULL)){
				ChatColor color = this.findColor(oldtool.getName());
				String skullName = new Skull(((CraftItemStack)is).getHandle()).getOwner(); //PlaceHolder
				tool.setName(color+skullName+"'s "+ChatColor.stripColor(oldtool.getName()));
				//TODO: If player get player skull name/skull type from skull add to tool name
				// i.e. color+ "Deathmarine's Prefix Suffix" or "Skeleton's Prefix Suffix"
			}else{
				tool.setName(oldtool.getName());
			}
			return;
		}
		if(plugin.drop.isTool(event.getResult().getType())&&!plugin.drop.isSword(event.getResult().getType())){
			//SocketBonus sb = plugin.bonuses.get(SocketType.TOOL);
			return;
		}
		if(plugin.drop.isSword(event.getResult().getType())||event.getResult().getType().equals(Material.BOW)){
			//SocketBonus sb = plugin.bonuses.get(SocketType.WEAPON);
			return;
		}
	}
	@EventHandler
	public void burnGem(FurnaceBurnEvent event){
		for(String name: plugin.config.getStringList("SocketItem.Items")){
			if(event.getFuel().getType().equals(Material.matchMaterial(name))){
				plugin.furnanceMap.put(event.getBlock(), event.getFuel());
				event.setBurnTime(240);
				event.setBurning(true);
			}
		}
	}
	public ChatColor findColor(String s){
		char[] c = s.toCharArray();
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<c.length;i++){
			if(c[i]==new Character((char) 167)){
				sb.append(c[i]);
				return ChatColor.getByChar(c[i+1]);
			}
		}
		return null;
	}
}