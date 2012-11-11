package com.modcrafting.diablodrops.socket;

import net.minecraft.server.InventoryMerchant;

import org.bukkit.Material;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.inventory.CraftInventoryCustom;
import org.bukkit.craftbukkit.inventory.CraftInventoryMerchant;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.drops.Drops;
import com.modcrafting.toolapi.lib.Tool;

public class SocketWindow implements Listener{
	DiabloDrops plugin;
	Drops drop;
	public SocketWindow(DiabloDrops instance) {
		plugin=instance;
		drop= new Drops();
	}
	//DO not attempt yet. Bukkit needs work. Lots of work.
	@EventHandler
	public void onRightClick(PlayerInteractEvent e){
	//public void onRightClickIventory(InventoryClickEvent e){
		//if(e.isRightClick()){
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR)){
			//if(drop.isTool(e.getCurrentItem().getType())){
			//if(drop.isTool(e.getPlayer().getItemInHand().getType())){
				final Tool tool = new Tool(((CraftItemStack) e.getPlayer().getItemInHand()).getHandle());
				//if(tool.getName().contains("Socket")){
					//e.setCancelled(true);
					//HumanEntity p = e.getWhoClicked();
					HumanEntity p = (HumanEntity) e.getPlayer();
					//p.closeInventory();
					final CraftHumanEntity cp = (CraftHumanEntity) p;
					//You are your own merchant.... Maybe. Will most likely throw an NPE.
							MerchantInv im = new MerchantInv(cp.getHandle(), null, tool);
							cp.openInventory(new CraftInventoryCustom(cp, InventoryType.MERCHANT));
					
				//}
			//}
		}
	}
}
