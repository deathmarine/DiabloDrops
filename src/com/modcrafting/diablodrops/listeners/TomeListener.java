package com.modcrafting.diablodrops.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.bananaco.bookapi.lib.Book;
import de.bananaco.bookapi.lib.CraftBookBuilder;

public class TomeListener implements Listener{
	@EventHandler
	public void onRightClick(PlayerInteractEvent e){
		if((e.getAction().equals(Action.RIGHT_CLICK_AIR)||e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			&&e.getPlayer().getItemInHand().getType().equals(Material.WRITTEN_BOOK)){
			Book b = new CraftBookBuilder().getBook(e.getPlayer().getItemInHand());
			if(b.getTitle().contains(ChatColor.DARK_AQUA+"Tome of Identify")){
				e.getPlayer().setItemInHand(null);
			}
		}
	}
}
