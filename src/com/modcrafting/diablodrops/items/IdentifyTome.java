package com.modcrafting.diablodrops.items;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class IdentifyTome extends Drop
{
    public IdentifyTome()
    {
        super(Material.WRITTEN_BOOK, ChatColor.DARK_AQUA, "Identity Tome");
        ItemMeta im = this.getItemMeta();
        if (im == null)
            im = Bukkit.getItemFactory().getItemMeta(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) im;
        String author = UUID.randomUUID().toString();
        if (author.length() > 16)
            author = author.substring(0, 15);
        meta.setAuthor(ChatColor.MAGIC + author);
        List<String> pages = new ArrayList<String>();
        pages.add(0, ChatColor.MAGIC + author);
        meta.setPages(pages);
        this.setItemMeta(meta);
    }
}