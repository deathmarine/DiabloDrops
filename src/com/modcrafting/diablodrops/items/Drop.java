package com.modcrafting.diablodrops.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Drop extends ItemStack
{

    public Drop(Material mat)
    {
        super(mat);
        ItemMeta meta;
        if (hasItemMeta())
            meta = this.getItemMeta();
        else
            meta = Bukkit.getItemFactory().getItemMeta(mat);
        this.setItemMeta(meta);
    }

    public Drop(Material mat, ChatColor color, String name)
    {
        super(mat);
        ItemMeta meta;
        if (hasItemMeta())
            meta = this.getItemMeta();
        else
            meta = Bukkit.getItemFactory().getItemMeta(mat);
        meta.setDisplayName(color + name + ChatColor.STRIKETHROUGH);
        this.setItemMeta(meta);
    }

    public Drop(Material mat, ChatColor color, String name, short durability)
    {
        super(mat);
        ItemMeta meta;
        if (hasItemMeta())
            meta = this.getItemMeta();
        else
            meta = Bukkit.getItemFactory().getItemMeta(mat);
        meta.setDisplayName(color + name + ChatColor.STRIKETHROUGH);
        this.setItemMeta(meta);
        this.setDurability(durability);
    }

    public Drop(Material mat, ChatColor color, String name, short durability,
            String... lore)
    {
        super(mat);
        ItemMeta meta;
        if (hasItemMeta())
            meta = this.getItemMeta();
        else
            meta = Bukkit.getItemFactory().getItemMeta(mat);
        meta.setDisplayName(color + name + ChatColor.STRIKETHROUGH);
        List<String> list = new ArrayList<String>();
        for (String e : lore)
        {
            list.add(e);
        }
        meta.setLore(list);
        this.setItemMeta(meta);
        this.setDurability(durability);
    }

    public Drop(Material mat, ChatColor color, String name, String... lore)
    {
        super(mat);
        ItemMeta meta;
        if (hasItemMeta())
            meta = this.getItemMeta();
        else
            meta = Bukkit.getItemFactory().getItemMeta(mat);
        meta.setDisplayName(color + name + ChatColor.STRIKETHROUGH);
        List<String> list = new ArrayList<String>();
        for (String e : lore)
        {
            list.add(e);
        }
        meta.setLore(list);
        this.setItemMeta(meta);
    }

}
