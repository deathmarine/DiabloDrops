package com.modcrafting.diablodrops.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.items.IdentifyTome;
import com.modcrafting.diablodrops.items.Socket;
import com.modcrafting.diablodrops.items.SockettedItem;
import com.modcrafting.diablodrops.sets.ArmorSet;
import com.modcrafting.diablodrops.tier.Tier;

public class DiabloDropCommand implements CommandExecutor
{
    private final DiabloDrops plugin;

    public DiabloDropCommand(final DiabloDrops plugin)
    {
        this.plugin = plugin;
    }

    public String combineSplit(final int startIndex, final String[] string,
            final String seperator)
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

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(final CommandSender sender, final Command command,
            final String commandLabel, final String[] args)
    {
        if (!sender.hasPermission(command.getPermission()))
        {
            sender.sendMessage(ChatColor.RED + "You cannot run this command.");
            return true;
        }
        switch (args.length)
        {
            case 0:
                if (!(sender instanceof Player))
                {
                    sender.sendMessage(ChatColor.RED
                            + "You cannot run this command.");
                    return true;
                }
                ItemStack ci = plugin.getDropAPI().getItem();
                while (ci == null)
                {
                    ci = plugin.getDropAPI().getItem();
                }
                ((Player) sender).getInventory().addItem(ci);
                ((Player) sender).updateInventory();
                ((Player) sender).sendMessage(ChatColor.GREEN
                        + "You have been given a DiabloDrops item.");
                return true;
            case 1:
                if (args[0].equalsIgnoreCase("tome")
                        || args[0].equalsIgnoreCase("book"))
                {
                    if (!(sender instanceof Player))
                    {
                        sender.sendMessage(ChatColor.RED
                                + "You cannot run this command.");
                    }
                    else
                    {
                        ((Player) sender).getInventory().addItem(
                                new IdentifyTome());
                        ((Player) sender).sendMessage(ChatColor.GREEN
                                + "You have been given an Identify Tome.");
                        ((Player) sender).updateInventory();
                    }
                }
                else if (args[0].equalsIgnoreCase("socket")
                        || args[0].equalsIgnoreCase("socketitem"))
                {
                    if (!(sender instanceof Player))
                    {
                        sender.sendMessage(ChatColor.RED
                                + "You cannot run this command.");
                    }
                    else
                    {
                        List<String> l = plugin.getConfig().getStringList(
                                "SocketItem.Items");
                        ((Player) sender).getInventory().addItem(
                                new Socket(Material.valueOf(l.get(
                                        plugin.getSingleRandom().nextInt(
                                                l.size())).toUpperCase())));
                        ((Player) sender).updateInventory();
                        ((Player) sender).sendMessage(ChatColor.GREEN
                                + "You have been given a Socket Enhancement.");
                    }
                }
                else if (args[0].equalsIgnoreCase("socketted")
                        || args[0].equalsIgnoreCase("socketteditem"))
                {
                    if (!(sender instanceof Player))
                    {
                        sender.sendMessage(ChatColor.RED
                                + "You cannot run this command.");
                    }
                    else
                    {
                        ((Player) sender).getInventory().addItem(
                                new SockettedItem(plugin.getDropAPI()
                                        .dropPicker()));
                        ((Player) sender).updateInventory();
                        ((Player) sender).sendMessage(ChatColor.GREEN
                                + "You have been given a Socketted Item.");
                    }
                }
                else if (args[0].equalsIgnoreCase("custom"))
                {
                    if (!(sender instanceof Player))
                    {
                        sender.sendMessage(ChatColor.RED
                                + "You cannot run this command.");
                    }
                    else
                    {
                        if (plugin.custom.size() > 0)
                        {
                            ((Player) sender).getInventory().addItem(
                                    plugin.custom.get(plugin.getSingleRandom()
                                            .nextInt(plugin.custom.size())));
                        }
                        else
                        {
                            ItemStack pis = plugin.getDropAPI().getItem();
                            while (pis == null)
                                pis = plugin.getDropAPI().getItem();
                            ((Player) sender).getInventory().addItem(pis);
                        }
                        ((Player) sender).updateInventory();
                        ((Player) sender).sendMessage(ChatColor.GREEN
                                + "You have been given a DiabloDrops item.");
                    }
                }
                else if (args[0].equalsIgnoreCase("repair"))
                {
                    if (!(sender instanceof Player))
                    {
                        sender.sendMessage(ChatColor.RED
                                + "You are unable to run this command right now.");
                    }
                    else
                    {
                        if (plugin.getDropAPI().canBeItem(
                                ((Player) sender).getItemInHand().getType()))
                        {
                            ((Player) sender).getItemInHand().setDurability(
                                    (short) 0);
                            ((Player) sender).sendMessage(ChatColor.GREEN
                                    + "Item repaired.");
                            return true;
                        }
                    }
                    ((Player) sender).sendMessage("Unable to repair item.");
                }
                else if (args[0].equalsIgnoreCase("reload")
                        && sender.hasPermission("diablodrops.reload"))
                {
                    plugin.getServer().getPluginManager().disablePlugin(plugin);
                    plugin.getServer().getPluginManager().enablePlugin(plugin);
                    plugin.reloadConfig();
                    plugin.getLogger().info("Reloaded");
                    sender.sendMessage(ChatColor.GREEN + "DiabloDrops Reloaded");
                }
                else if (args[0].equalsIgnoreCase("debug"))
                {
                    int customsize = plugin.custom.size();
                    plugin.getLogger().info(
                            customsize + "]: Custom Items Loaded.");
                    sender.sendMessage(customsize + "]: Custom Items Loaded.");
                    int armorsets = plugin.armorSets.size();
                    plugin.getLogger().info(armorsets + "]: ArmorSets Loaded.");
                    sender.sendMessage(armorsets + "]: ArmorSets Loaded.");
                    int tier = plugin.tiers.size();
                    plugin.getLogger().info(tier + "]: Tiers Loaded.");
                    sender.sendMessage(tier + "]: Tiers Loaded.");
                    int defaultP = plugin.prefix.size();
                    plugin.getLogger().info(
                            defaultP + "]: Default Prefixes Loaded.");
                    sender.sendMessage(defaultP + "]: Default Prefixes Loaded.");
                    int defaultS = plugin.suffix.size();
                    plugin.getLogger().info(
                            defaultS + "]: Default Suffixes Loaded.");
                    sender.sendMessage(defaultS + "]: Default Suffixes Loaded.");
                    int customP = plugin.hmprefix.size();
                    plugin.getLogger().info(
                            customP + "]: Custom Prefixes Loaded.");
                    sender.sendMessage(customP + "]: Custom Prefixes Loaded.");
                    int customS = plugin.hmsuffix.size();
                    plugin.getLogger().info(
                            customS + "]: Custom Suffixes Loaded.");
                    sender.sendMessage(customS + "]: Custom Suffixes Loaded.");
                    int dlore = plugin.defenselore.size();
                    plugin.getLogger().info(dlore + "]: Defense Lore Loaded.");
                    sender.sendMessage(dlore + "]: Defense Lore Loaded.");
                    int olore = plugin.offenselore.size();
                    plugin.getLogger().info(olore + "]: Offense Lore Loaded.");
                    sender.sendMessage(olore + "]: Offense Lore Loaded.");
                    int w = plugin.worlds.size();
                    plugin.getLogger().info(w + "]: Worlds allowing Loaded.");
                    sender.sendMessage(w + "]: Worlds allowing Loaded.");
                    if (args.length > 1)
                    {
                        if (args[1].equalsIgnoreCase("detailed"))
                        {
                            StringBuilder sb = new StringBuilder();
                            sb.append("\n");
                            sb.append("-----Custom-----");
                            sb.append("\n");
                            for (ItemStack tool : plugin.custom)
                            {
                                sb.append(tool.getItemMeta().getDisplayName()
                                        + " ");
                            }
                            sb.append("\n");
                            sb.append("-----ArmorSet-----");
                            sb.append("\n");
                            for (ArmorSet a : plugin.armorSets)
                            {
                                sb.append(a.getName() + " ");
                            }
                            sb.append("\n");
                            sb.append("-----Tiers-----");
                            sb.append("\n");
                            for (Tier a : plugin.tiers)
                            {
                                sb.append(a.getName() + " ");
                            }
                            sb.append("\n");
                            sb.append("-----DefaultPrefix-----");
                            sb.append("\n");
                            for (String s : plugin.prefix)
                            {
                                sb.append(s + " ");
                            }
                            sb.append("\n");
                            sb.append("-----DefaultSuffix-----");
                            sb.append("\n");
                            for (String s : plugin.suffix)
                            {
                                sb.append(s + " ");
                            }
                            sb.append("\n");
                            sb.append("-----CustomPrefix-----");
                            sb.append("\n");
                            for (Material m : plugin.hmprefix.keySet())
                            {
                                sb.append(m.toString() + "\n");
                                for (String p : plugin.hmprefix.get(m))
                                {
                                    sb.append(p + " ");
                                }
                            }
                            sb.append("\n");
                            sb.append("-----CustomSuffix-----");
                            sb.append("\n");
                            for (Material m : plugin.hmsuffix.keySet())
                            {
                                sb.append(m.toString() + "\n");
                                for (String p : plugin.hmsuffix.get(m))
                                {
                                    sb.append(p + " ");
                                }
                            }
                            sb.append("\n");
                            sb.append("-----Defense Lore-----");
                            sb.append("\n");
                            for (String s : plugin.defenselore)
                            {
                                sb.append(s + " ");
                            }
                            sb.append("\n");
                            sb.append("-----Offense Lore-----");
                            sb.append("\n");
                            for (String s : plugin.offenselore)
                            {
                                sb.append(s + " ");
                            }
                            plugin.getLogger().info(sb.toString());
                        }
                    }
                }
                else
                {
                    sender.sendMessage(ChatColor.RED
                            + "That is not a valid command.");
                }
                return true;
            default:
                if (args[0].equalsIgnoreCase("custom"))
                {
                    String name = "";
                    Player p = null;
                    for (String s : args)
                    {
                        if (StringUtils.containsIgnoreCase(s, "p:"))
                        {
                            s = s.replace("p:", "");
                            p = Bukkit.getPlayer(s);
                            continue;
                        }
                        if (!s.equals(args[0]))
                            if (name.equals(""))
                                name = s;
                            else
                                name = name + " " + s;
                    }
                    ItemStack customItem = null;
                    if (plugin.custom.size() > 0)
                    {
                        for (ItemStack is : plugin.custom)
                        {
                            if (ChatColor.stripColor(
                                    plugin.getItemAPI().getName(is)).equals(
                                    name))
                            {
                                customItem = is;
                                break;
                            }
                        }
                        if (customItem == null)
                            customItem = plugin.custom.get(plugin
                                    .getSingleRandom().nextInt(
                                            plugin.custom.size()));
                    }
                    if (customItem != null && p != null)
                    {
                        p.getInventory().addItem(customItem);
                        p.updateInventory();
                        p.sendMessage(ChatColor.GREEN
                                + "You have been given a DiabloDrops item.");
                        sender.sendMessage(ChatColor.WHITE + p.getName()
                                + ChatColor.GREEN
                                + " has been given a DiabloDrops item.");
                    }
                    else if (customItem != null && p == null
                            && sender instanceof Player)
                    {
                        ((Player) sender).getInventory().addItem(customItem);
                        ((Player) sender).updateInventory();
                        ((Player) sender).sendMessage(ChatColor.GREEN
                                + "You have been given a DiabloDrops item.");
                    }
                    else
                    {
                        sender.sendMessage(ChatColor.RED
                                + "Either that is not a valid item or you are unable to run this command.");
                    }
                    return true;
                }
                else if (args[0].equalsIgnoreCase("give"))
                {
                    if (args.length < 2)
                    {
                        sender.sendMessage(ChatColor.RED
                                + "Not enough arguments.");
                        return true;
                    }
                    List<String> stringList = new ArrayList<String>();
                    for (String s : args)
                    {
                        if (s.equals(args[0]))
                            continue;
                        Player p = Bukkit.getPlayer(s);
                        ItemStack giveItem = plugin.getDropAPI().getItem();
                        while (giveItem == null)
                        {
                            giveItem = plugin.getDropAPI().getItem();
                        }
                        stringList.add(p.getName());
                        p.getInventory().addItem(giveItem);
                        p.sendMessage(ChatColor.GREEN
                                + "You have been given a DiabloDrops item.");
                    }
                    sender.sendMessage(ChatColor.GREEN
                            + "You gave items to "
                            + ChatColor.WHITE
                            + stringList.toString().replace("[", "")
                                    .replace("]", "") + ChatColor.GREEN + ".");
                    return true;
                }
                else if (args[0].equalsIgnoreCase("modify"))
                {
                    if (!(sender instanceof Player))
                    {
                        sender.sendMessage(ChatColor.RED
                                + "You cannot run this command.");
                    }
                    else
                    {
                        if ((args.length < 2)
                                || (((Player) sender).getItemInHand() == null)
                                || ((Player) sender).getItemInHand().getType()
                                        .equals(Material.AIR))
                            return true;
                        ItemStack tool = ((Player) sender).getItemInHand();
                        ItemMeta meta = tool.getItemMeta();
                        if (args[1].equalsIgnoreCase("lore"))
                        {
                            if (args[2].equalsIgnoreCase("clear"))
                            {
                                meta.setLore(null);
                                tool.setItemMeta(meta);
                                ((Player) sender).sendMessage(ChatColor.GREEN
                                        + "Cleared the lore for the item!");
                                return true;
                            }
                            String lore = combineSplit(2, args, " ");
                            lore = ChatColor.translateAlternateColorCodes(
                                    "&".toCharArray()[0], lore);
                            meta.setLore(Arrays.asList(lore.split(",")));
                            tool.setItemMeta(meta);
                            ((Player) sender).sendMessage(ChatColor.GREEN
                                    + "Set the lore for the item!");
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("name"))
                        {
                            if (args[2].equalsIgnoreCase("clear"))
                            {
                                tool.getItemMeta().setDisplayName(null);
                                ((Player) sender).sendMessage(ChatColor.GREEN
                                        + "Cleared the name for the item!");
                                return true;
                            }
                            String name = combineSplit(2, args, " ");
                            name = ChatColor.translateAlternateColorCodes(
                                    "&".toCharArray()[0], name);

                            meta.setDisplayName(name);
                            tool.setItemMeta(meta);
                            ((Player) sender).sendMessage(ChatColor.GREEN
                                    + "Set the name for the item!");
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("enchant"))
                        {
                            if (args.length < 4)
                            {
                                if ((args.length == 3)
                                        && args[2].equalsIgnoreCase("clear"))
                                {
                                    for (Enchantment e : Enchantment.values())
                                        tool.getItemMeta().removeEnchant(e);
                                    ((Player) sender)
                                            .sendMessage(ChatColor.GREEN
                                                    + "Cleared the enchantments for the item!");
                                    return true;
                                }
                                ((Player) sender)
                                        .sendMessage(ChatColor.RED
                                                + "Correct usage: /dd modify enchant"
                                                + " [enchantment name] [enchantment level]");
                                return true;
                            }
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
                                    if (plugin.getDebug())
                                    {
                                        plugin.log.warning(nfe.getMessage());
                                    }
                                }
                                Enchantment ech = Enchantment.getByName(args[3]
                                        .toUpperCase());
                                if (ech != null)
                                {
                                    ((Player) sender).getItemInHand()
                                            .addUnsafeEnchantment(ech, i);
                                    ((Player) sender)
                                            .sendMessage(ChatColor.GREEN
                                                    + "Added enchantment.");
                                }
                                else
                                {
                                    ((Player) sender).sendMessage(ChatColor.RED
                                            + args[3]
                                            + " :enchantment does not exist!");
                                }
                                return true;
                            }
                            if (args[2].equalsIgnoreCase("remove"))
                            {
                                ItemStack is = ((Player) sender)
                                        .getItemInHand();
                                Map<Enchantment, Integer> hm = new HashMap<Enchantment, Integer>();
                                for (Enchantment e1 : is.getEnchantments()
                                        .keySet())
                                {
                                    if (!e1.getName().equalsIgnoreCase(args[3]))
                                    {
                                        hm.put(e1, is.getEnchantmentLevel(e1));
                                    }
                                }
                                is.addUnsafeEnchantments(hm);
                                ((Player) sender).sendMessage(ChatColor.GREEN
                                        + "Removed enchantment.");
                                return true;

                            }
                        }
                    }
                    return true;
                }
                else if (args[0].equalsIgnoreCase("tier"))
                {
                    String name = "";
                    Player p = null;
                    for (String s : args)
                    {
                        if (StringUtils.containsIgnoreCase(s, "p:"))
                        {
                            s = s.replace("p:", "");
                            p = Bukkit.getPlayer(s);
                            continue;
                        }
                        if (!s.equals(args[0]))
                            if (name.equals(""))
                                name = s;
                            else
                                name = name + " " + s;
                    }
                    Tier tier = plugin.getDropAPI().getTier(name);
                    ItemStack customItem = plugin.getDropAPI().getItem(tier);
                    if (customItem != null && p != null)
                    {
                        p.getInventory().addItem(customItem);
                        p.updateInventory();
                        p.sendMessage(ChatColor.GREEN
                                + "You have been given a DiabloDrops item.");
                        sender.sendMessage(ChatColor.WHITE + p.getName()
                                + ChatColor.GREEN
                                + " has been given a DiabloDrops item.");
                    }
                    else if (customItem != null && p == null
                            && sender instanceof Player)
                    {
                        ((Player) sender).getInventory().addItem(customItem);
                        ((Player) sender).updateInventory();
                        ((Player) sender).sendMessage(ChatColor.GREEN
                                + "You have been given a DiabloDrops item.");
                    }
                    else
                    {
                        sender.sendMessage(ChatColor.RED
                                + "Either that is not a valid tier or you are unable to run this command.");
                    }
                    return true;
                }
                if (!(sender instanceof Player))
                {
                    sender.sendMessage("You cannot run this command right now.");
                    return true;
                }
                else
                {
                    ItemStack ci2 = plugin.getDropAPI().getItem();
                    while (ci2 == null)
                    {
                        ci2 = plugin.getDropAPI().getItem();
                    }
                    ((Player) sender).getInventory().addItem(ci2);
                    ((Player) sender).updateInventory();
                    ((Player) sender).sendMessage(ChatColor.GREEN
                            + "You have been given a DiabloDrops item.");
                    return true;
                }
        }
    }
}
