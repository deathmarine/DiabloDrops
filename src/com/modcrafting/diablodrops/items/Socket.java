package com.modcrafting.diablodrops.items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import com.modcrafting.diablodrops.DiabloDrops;

public class Socket extends Drop
{

    private static ChatColor color()
    {
        switch (DiabloDrops.getInstance().getSingleRandom().nextInt(3))
        {
            case 1:
                return ChatColor.RED;
            case 2:
                return ChatColor.BLUE;
            default:
                return ChatColor.GREEN;
        }
    }

    public Socket(final Material mat)
    {
        super(mat, color(), "Socket Enhancement", ChatColor.GOLD
                + "Put in the bottom of a furnace", ChatColor.GOLD
                + "with another item in the top", ChatColor.GOLD
                + "to add socket enhancements.");
        int num = DiabloDrops.getInstance().getSingleRandom().nextInt(5);
        MaterialData md = getData();
        md.setData((byte) num);
        setData(md);
        ItemMeta meta;
        if (hasItemMeta())
            meta = getItemMeta();
        else
            meta = Bukkit.getItemFactory().getItemMeta(mat);
        if (mat.equals(Material.SKULL_ITEM))
        {
            SkullMeta sk = (SkullMeta) meta;
            if (num == 3)
            {
                if (Bukkit.getServer().getOfflinePlayers().length > 0)
                {
                    sk.setOwner(Bukkit.getServer().getOfflinePlayers()[DiabloDrops
                            .getInstance()
                            .getSingleRandom()
                            .nextInt(
                                    Bukkit.getServer().getOfflinePlayers().length)]
                            .getName());
                }
                else
                {
                    if (DiabloDrops.getInstance().getSingleRandom()
                            .nextBoolean())
                    {
                        sk.setOwner("deathmarin");
                    }
                    else
                    {
                        sk.setOwner("ToppleTheNun");
                    }
                }
            }
        }
        setItemMeta(meta);
    }
}
