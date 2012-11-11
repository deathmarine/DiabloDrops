package com.modcrafting.diablodrops.tier;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;

import de.bananaco.bookapi.lib.Book;
import de.bananaco.bookapi.lib.CraftBookBuilder;

public class Tome extends CraftItemStack{
	public Tome(){
		super(Material.WRITTEN_BOOK);
		Book b = new CraftBookBuilder().getBook(this);
		b.setTitle(ChatColor.DARK_AQUA+"Identity Tome");
		b.setAuthor(ChatColor.MAGIC+"AAAAAAAA");
		List<String> pages = new ArrayList<String>();
		pages.add(0, ChatColor.MAGIC+"AAAAA AA AAAA AAAA AAAA");
		b.setPages(pages);
	}

}
