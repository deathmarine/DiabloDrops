package com.modcrafting.diablodrops.items;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class IdentifyTome extends ItemStack
{
    public IdentifyTome()
    {
        super(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) this.getItemMeta();
        meta.setTitle(ChatColor.DARK_AQUA + "Identity Tome");
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
