package com.modcrafting.diablodrops.listeners;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.ItemStack;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.events.PreSocketEnhancementEvent;
import com.modcrafting.diablodrops.events.SocketEnhancementEvent;
import com.modcrafting.diablolibrary.items.DiabloItemStack;
import com.modcrafting.diablolibrary.items.DiabloSkull;

public class SocketListener implements Listener
{
    DiabloDrops plugin;

    public SocketListener(final DiabloDrops instance)
    {
        plugin = instance;
    }

    @EventHandler
    public void burnGem(final FurnaceBurnEvent event)
    {
        Furnace furn = (Furnace) event.getBlock().getState();
        ItemStack tis = furn.getInventory().getSmelting();
        if (plugin.drop.isArmor(tis.getType())
                || plugin.drop.isTool(tis.getType()))
        {
            for (String name : plugin.config.getStringList("SocketItem.Items"))
                if (event.getFuel().getType()
                        .equals(Material.matchMaterial(name)))
                {
                    DiabloItemStack fuel = new DiabloItemStack(event.getFuel());
                    if (fuel.getName() != null)
                    {
                        boolean test = false;
                        String toReplace = null;
                        for (String t : new DiabloItemStack(tis).getLoreList())
                            if (t.contains("(Socket)"))
                            {
                                test = true;
                                toReplace = t;
                            }
                        if (toReplace != null)
                            if ((fuel.getName().contains("Socket") || fuel
                                    .getType().equals(Material.SKULL_ITEM))
                                    && test)
                            {
                                ChatColor socketColor = findColor(toReplace);
                                ChatColor fuelColor = findColor(fuel.getName());
                                if (fuelColor == socketColor
                                        || socketColor == null
                                        || socketColor == ChatColor.RESET)
                                {
                                    PreSocketEnhancementEvent psee = new PreSocketEnhancementEvent(
                                            tis, event.getFuel(), furn);
                                    plugin.getServer().getPluginManager()
                                            .callEvent(psee);
                                    if (psee.isCancelled())
                                        continue;
                                    plugin.furnanceMap.put(event.getBlock(),
                                            event.getFuel());
                                    event.setBurnTime(240);
                                    event.setBurning(true);
                                    return;
                                }
                            }
                    }
                }
            event.setCancelled(true);
            event.setBurning(false);
            event.setBurnTime(120000);
            return;
        }
    }

    public ChatColor findColor(final String s)
    {
        char[] c = s.toCharArray();
        for (int i = 0; i < c.length; i++)
            if ((c[i] == new Character((char) 167)) && ((i + 1) < c.length))
                return ChatColor.getByChar(c[i + 1]);
        return null;
    }

    @EventHandler
    public void onSmeltSocket(final FurnaceSmeltEvent event)
    {
        if (!plugin.furnanceMap.containsKey(event.getBlock())
                && !plugin.drop.isTool(event.getResult().getType()))
            return;
        ItemStack is = plugin.furnanceMap.remove(event.getBlock());
        Material fuel = is.getType();
        DiabloItemStack tool = new DiabloItemStack(event.getResult().getType());
        DiabloItemStack oldtool = new DiabloItemStack(event.getSource());
        boolean namTest = false;
        for (String n : oldtool.getLoreList())
            if (StringUtils.containsIgnoreCase(n, "(Socket)"))
                namTest = true;
        if (!namTest)
        {
            event.setResult(event.getSource());
            return;
        }

        int eni = plugin.config.getInt("SocketItem.EnhanceBy", 1);
        int ene = plugin.config.getInt("SocketItem.EnhanceMax", 10);
        for (Enchantment ench : oldtool.getEnchantments().keySet())
        {
            int il = oldtool.getEnchantments().get(ench);
            if (il < ene)
                il = il + eni;
            tool.addUnsafeEnchantment(ench, il);
        }

        if (fuel.equals(Material.SKULL_ITEM))
        {

            ChatColor color = findColor(oldtool.getName());
            DiabloSkull skull = new DiabloSkull(is);
            String skullName = skull.getOwner();
            if ((skullName == null) || (skullName.trim().length() < 1))
                switch (skull.getSkullType())
                {
                    case CREEPER:
                    {
                        skullName = "Creeper";
                        break;
                    }
                    case PLAYER:
                    {
                        skullName = "Steve";
                        break;
                    }
                    case SKELETON:
                    {
                        skullName = "Skeleton";
                        break;
                    }
                    case WITHER:
                    {
                        skullName = "Wither";
                        break;
                    }
                    case ZOMBIE:
                    {
                        skullName = "Zombie";
                        break;
                    }
                }
            String old = oldtool.getName();
            if (old.contains("'"))
                old = old.split("'")[1].substring(2);
            tool.setName(color + skullName + "'s " + ChatColor.stripColor(old));
        }
        else
            tool.setName(oldtool.getName());
        if (plugin.config.getBoolean("Socket.Lore", true))
            for (int i = 0; i < plugin.config.getInt("Lore.EnhanceAmount", 2); i++)
                if (plugin.drop.isArmor(oldtool.getType()))
                    tool.addLore(plugin.defenselore.get(plugin.gen
                            .nextInt(plugin.defenselore.size())));
                else if (plugin.drop.isTool(oldtool.getType()))
                    tool.addLore(plugin.offenselore.get(plugin.gen
                            .nextInt(plugin.offenselore.size())));
        SocketEnhancementEvent see = new SocketEnhancementEvent(
                event.getSource(), is, tool, ((Furnace) event.getBlock()
                        .getState()));
        plugin.getServer().getPluginManager().callEvent(see);
        event.setResult(tool);
        return;
    }
}
