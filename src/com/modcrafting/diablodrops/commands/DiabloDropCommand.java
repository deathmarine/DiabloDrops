package com.modcrafting.diablodrops.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.log.Logging;
import com.modcrafting.diablodrops.socket.gem.SocketItem;
import com.modcrafting.diablodrops.tier.Tier;
import com.modcrafting.diablodrops.tier.Tome;
import com.modcrafting.toolapi.lib.Tool;

public class DiabloDropCommand implements CommandExecutor
{
    private DiabloDrops plugin;

    public DiabloDropCommand(DiabloDrops plugin)
    {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command,
            String commandLabel, String[] args)
    {
        if (!(sender instanceof Player)
                || !sender.hasPermission(command.getPermission()))
        {
            sender.sendMessage(ChatColor.RED + "You cannot run this command.");
            return true;
        }
        Player player = ((Player) sender);
        PlayerInventory pi = player.getInventory();
        switch (args.length)
        {
            case 0:
                CraftItemStack ci = plugin.dropsAPI.getItem();
                while (ci == null)
                    ci = plugin.dropsAPI.getItem();
                pi.addItem(ci);
                player.sendMessage(ChatColor.GREEN
                        + "You have been given a DiabloDrops item.");
                return true;
            default:
                if (args[0].equalsIgnoreCase("tome")
                        || args[0].equalsIgnoreCase("book"))
                {
                    pi.addItem(new Tome());
                    player.sendMessage(ChatColor.GREEN
                            + "You have been given a tome.");
                    return true;
                }
                if (args[0].equalsIgnoreCase("socket")
                        || args[0].equalsIgnoreCase("socketitem"))
                {
                    pi.addItem(new SocketItem(Material.EMERALD));
                    player.sendMessage(ChatColor.GREEN
                            + "You have been given a SocketItem.");
                    return true;
                }
                if (args[0].equalsIgnoreCase("custom"))
                {
                    pi.addItem(plugin.custom.get(plugin.gen
                            .nextInt(plugin.custom.size())));
                    player.sendMessage(ChatColor.GREEN
                            + "You have been given a DiabloDrops item.");
                    return true;
                }
                if (args[0].equalsIgnoreCase("modify"))
                {
                    if (args.length < 2)
                        return true;
                    if (args[1].equalsIgnoreCase("lore"))
                    {
                        String lore = combineSplit(2, args, " ");
                        lore = ChatColor.translateAlternateColorCodes(
                                "&".toCharArray()[0], lore);
                        new Tool(player.getItemInHand()).setLore(Arrays
                                .asList(lore.split(",")));
                        player.sendMessage(ChatColor.GREEN
                                + "Set the lore for the item!");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("name"))
                    {
                        String name = combineSplit(2, args, " ");
                        name = ChatColor.translateAlternateColorCodes(
                                "&".toCharArray()[0], name);
                        new Tool(player.getItemInHand()).setName(name);
                        player.sendMessage(ChatColor.GREEN
                                + "Set the name for the item!");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("enchant"))
                    {
                        if (args.length < 4)
                            return true;
                        if (args[2].equalsIgnoreCase("add"))
                        {
                            if (args.length < 5)
                                return true;
                            int i = 1;
                            try
                            {
                                i = Integer.parseInt(args[4]);
                            }
                            catch (NumberFormatException nfe)
                            {
                                Logging.debug("", nfe, true);
                            }
                            Enchantment ech = Enchantment.getByName(args[3]
                                    .toUpperCase());
                            if (ech != null)
                            {
                                player.getItemInHand().addUnsafeEnchantment(
                                        ech, i);
                                player.sendMessage(ChatColor.GREEN
                                        + "Added enchantment.");
                            }
                            else
                            {
                                player.sendMessage(ChatColor.RED + args[3]
                                        + " :enchantment does not exist!");
                            }
                            return true;
                        }
                        if (args[2].equalsIgnoreCase("remove"))
                        {
                            ItemStack is = player.getItemInHand();
                            Map<Enchantment, Integer> hm = new HashMap<Enchantment, Integer>();
                            for (Enchantment e1 : is.getEnchantments().keySet())
                            {
                                if (!e1.getName().equalsIgnoreCase(args[3]))
                                {
                                    hm.put(e1, is.getEnchantmentLevel(e1));
                                }
                            }
                            is.addUnsafeEnchantments(hm);
                            player.sendMessage(ChatColor.GREEN
                                    + "Removed enchantment.");
                            return true;

                        }
                    }
                }
                if (args[0].equalsIgnoreCase("reload")
                        && sender.hasPermission("diablodrops.reload"))
                {
                    plugin.getServer().getPluginManager().disablePlugin(plugin);
                    plugin.getServer().getPluginManager().enablePlugin(plugin);
                    plugin.reloadConfig();
                    plugin.getLogger().info("Reloaded");
                    sender.sendMessage(ChatColor.GREEN + "DiabloDrops Reloaded");
                    return true;
                }
                if (args[0].equalsIgnoreCase("repair"))
                {
                    if (plugin.dropsAPI.canBeItem(player.getItemInHand()
                            .getType()))
                    {
                        player.getItemInHand().setDurability((short) 0);
                        player.sendMessage(ChatColor.GREEN + "Item repaired.");
                        return true;
                    }
                    player.sendMessage("Unable to repair item.");
                    return true;
                }
                if (plugin.dropsAPI.matchesTier(args[0]))
                {
                    Tier tier = plugin.dropsAPI.getTier(args[0]);
                    CraftItemStack ci2 = plugin.dropsAPI.getItem(tier);
                    while (ci2 == null)
                        ci2 = plugin.dropsAPI.getItem(tier);
                    pi.addItem(ci2);
                    player.sendMessage(ChatColor.GREEN
                            + "You have been given a " + ChatColor.WHITE
                            + args[0] + ChatColor.GREEN + " DiabloDrops item.");
                    return true;
                }
                CraftItemStack ci2 = plugin.dropsAPI.getItem();
                while (ci2 == null)
                    ci2 = plugin.dropsAPI.getItem();
                pi.addItem(ci2);
                player.sendMessage(ChatColor.GREEN
                        + "You have been given a DiabloDrops item.");
                return true;
        }
    }

    public String combineSplit(int startIndex, String[] string, String seperator)
    {
        StringBuilder builder = new StringBuilder();
        if (string.length >= 1)
        {
            for (int i = startIndex; i < string.length; i++)
            {
                builder.append(string[i]);
                builder.append(seperator);
            }
            if (builder.length() > seperator.length())
            {
                builder.deleteCharAt(builder.length() - seperator.length()); // remove
                return builder.toString();
            }
        }
        return "";
    }
}
