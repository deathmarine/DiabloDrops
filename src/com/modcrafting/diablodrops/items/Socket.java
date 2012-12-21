package com.modcrafting.diablodrops.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import com.modcrafting.diablodrops.DiabloDrops;

public class Socket extends ItemStack
{
    public enum SkullType {
		SKELETON(0), WITHER(1), ZOMBIE(2), PLAYER(3), CREEPER(4);
		public int type;

		private SkullType(final int i) {
			type = i;
		}

		public byte getData() {
			return (byte) type;
		}

	}

    public Socket(final Material mat)
    {
        super(mat);
        ChatColor color = null;
        switch (DiabloDrops.getInstance().gen.nextInt(3))
        {
            case 1:
                color = ChatColor.RED;
                break;
            case 2:
                color = ChatColor.BLUE;
                break;
            default:
                color = ChatColor.GREEN;
        }
        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName(color + "Socket Enhancement");
        List<String> list = new ArrayList<String>();
        list.add(ChatColor.GOLD + "Put in the bottom of a furnace");
        list.add(ChatColor.GOLD + "with another item in the top");
        list.add(ChatColor.GOLD + "to add socket enhancements.");
        if (mat.equals(Material.SKULL_ITEM))
        {
            SkullMeta sk = (SkullMeta) meta;
            SkullType type = SkullType.values()[DiabloDrops.getInstance().gen
                    .nextInt(SkullType.values().length)];
            if (type.equals(SkullType.PLAYER)){
                sk.setOwner(Bukkit.getServer().getOfflinePlayers()[DiabloDrops
                        .getInstance().gen.nextInt(Bukkit.getServer()
                        .getOfflinePlayers().length)].getName());
            }
        	MaterialData md = this.getData();
        	md.setData(type.getData());
        	this.setData(md);
        }
        meta.setLore(list);
        this.setItemMeta(meta);
    }
}
