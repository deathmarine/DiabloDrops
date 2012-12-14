package com.modcrafting.diablodrops.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.modcrafting.diablolibrary.items.DiabloItemStack;

import de.bananaco.bookapi.lib.Book;
import de.bananaco.bookapi.lib.CraftBookBuilder;

public class DiabloifyTome extends DiabloItemStack
{
    public DiabloifyTome()
    {
        super(Material.WRITTEN_BOOK);
        Book b = new CraftBookBuilder().getBook(this);
        b.setTitle(ChatColor.DARK_RED + "Diablo Tome");
        b.setAuthor(ChatColor.MAGIC + "BBBBBBBB");
        List<String> pages = new ArrayList<String>();
        pages.add(0, ChatColor.MAGIC + "AAAAA AA AAAA AAAA AAAA");
        b.setPages(pages);
    }

}
