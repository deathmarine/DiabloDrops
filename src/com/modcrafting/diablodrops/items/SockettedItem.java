package com.modcrafting.diablodrops.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import com.modcrafting.diablodrops.DiabloDrops;

public class SockettedItem extends Drop
{

    private static ChatColor color()
    {
        return DiabloDrops.getInstance().getDropAPI().colorPicker();
    }

    private static String name(Material mat)
    {
        return DiabloDrops.getInstance().getDropAPI().name(mat);
    }

    private static String[] sockets()
    {
        List<String> list = new ArrayList<String>();
        int enhance = DiabloDrops.getInstance().getSettings().getMinSockets()
                + DiabloDrops
                        .getInstance()
                        .getSingleRandom()
                        .nextInt(
                                DiabloDrops.getInstance().getSettings()
                                        .getMaxSockets());
        for (int i = 0; i < enhance; i++)
        {
            list.add(color() + "(Socket)");
        }
        return list.toArray(new String[0]);
    }

    public SockettedItem(final Material mat)
    {
        super(mat, color(), name(mat), sockets());
        ItemMeta meta;
        if (hasItemMeta())
            meta = getItemMeta();
        else
            meta = Bukkit.getItemFactory().getItemMeta(mat);
        setItemMeta(meta);
    }
}