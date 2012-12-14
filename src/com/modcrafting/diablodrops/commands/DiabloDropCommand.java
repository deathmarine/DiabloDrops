package com.modcrafting.diablodrops.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.items.DiabloifyTome;
import com.modcrafting.diablodrops.items.IdentifyTome;
import com.modcrafting.diablodrops.items.Socket;
import com.modcrafting.diablodrops.sets.ArmorSet;
import com.modcrafting.diablodrops.tier.Tier;
import com.modcrafting.diablolibrary.items.DiabloItemStack;

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
                DiabloItemStack ci = plugin.dropsAPI.getItem();
                while (ci == null)
                {
                    ci = plugin.dropsAPI.getItem();
                }
                pi.addItem(ci);
                player.updateInventory();
                player.sendMessage(ChatColor.GREEN
                        + "You have been given a DiabloDrops item.");
                return true;
            default:
                if (args[0].equalsIgnoreCase("tome")
                        || args[0].equalsIgnoreCase("book"))
                {
                    if (plugin.gen.nextBoolean())
                    {
                        pi.addItem(new IdentifyTome());
                    }
                    else
                    {
                        pi.addItem(new DiabloifyTome());
                    }
                    player.sendMessage(ChatColor.GREEN
                            + "You have been given a tome.");
                    player.updateInventory();
                    return true;
                }
                if (args[0].equalsIgnoreCase("socket")
                        || args[0].equalsIgnoreCase("socketitem"))
                {
                    List<String> l = plugin.config
                            .getStringList("SocketItem.Items");
                    pi.addItem(new Socket(Material.valueOf(l.get(
                            plugin.gen.nextInt(l.size())).toUpperCase())));
                    player.updateInventory();
                    player.sendMessage(ChatColor.GREEN
                            + "You have been given a SocketItem.");
                    return true;
                }
                if (args[0].equalsIgnoreCase("custom"))
                {
                    pi.addItem(plugin.custom.get(plugin.gen
                            .nextInt(plugin.custom.size())));
                    player.updateInventory();
                    player.sendMessage(ChatColor.GREEN
                            + "You have been given a DiabloDrops item.");
                    return true;
                }
                if (args[0].equalsIgnoreCase("modify"))
                {
                    if ((args.length < 2)
                            || (player.getItemInHand() == null)
                            || player.getItemInHand().getType()
                                    .equals(Material.AIR))
                        return true;
                    DiabloItemStack tool = new DiabloItemStack(
                            player.getItemInHand());
                    if (args[1].equalsIgnoreCase("lore"))
                    {
                        if (args[2].equalsIgnoreCase("clear"))
                        {
                            tool.clearLore();
                            player.sendMessage(ChatColor.GREEN
                                    + "Cleared the lore for the item!");
                            return true;
                        }
                        String lore = combineSplit(2, args, " ");
                        lore = ChatColor.translateAlternateColorCodes(
                                "&".toCharArray()[0], lore);
                        for (String s : lore.split(","))
                        {
                            if (s.length() > 0)
                            {
                                tool.addLore(s);
                            }
                        }
                        player.sendMessage(ChatColor.GREEN
                                + "Set the lore for the item!");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("name"))
                    {
                        if (args[2].equalsIgnoreCase("clear"))
                        {
                            tool.clearName();
                            player.sendMessage(ChatColor.GREEN
                                    + "Cleared the name for the item!");
                            return true;
                        }
                        String name = combineSplit(2, args, " ");
                        name = ChatColor.translateAlternateColorCodes(
                                "&".toCharArray()[0], name);
                        new DiabloItemStack(player.getItemInHand())
                                .setName(name);
                        player.sendMessage(ChatColor.GREEN
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
                                tool.clearEnchantments();
                                player.sendMessage(ChatColor.GREEN
                                        + "Cleared the enchantments for the item!");
                                return true;
                            }
                            player.sendMessage(ChatColor.RED
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
                                if (plugin.debug)
                                {
                                    plugin.log.warning(nfe.getMessage());
                                }
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
                if (args[0].equalsIgnoreCase("debug"))
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
                            for (DiabloItemStack tool : plugin.custom)
                            {
                                sb.append(tool.getName() + " ");
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
                    return true;
                }
                if (args[0].equalsIgnoreCase("tier"))
                {
                    Tier tier = plugin.dropsAPI.getTier(args[1]);
                    DiabloItemStack ci2 = plugin.dropsAPI.getItem(tier);
                    while (ci2 == null)
                    {
                        ci2 = plugin.dropsAPI.getItem(tier);
                    }
                    pi.addItem(ci2);
                    player.updateInventory();
                    if (tier == null)
                    {
                        player.sendMessage(ChatColor.GREEN
                                + "You have been given a DiabloDrops item.");
                    }
                    else
                    {
                        player.sendMessage(ChatColor.GREEN
                                + "You have been given a " + tier.getColor()
                                + tier.getName() + ChatColor.GREEN
                                + " DiabloDrops item.");

                    }
                    return true;
                }
                DiabloItemStack ci2 = plugin.dropsAPI.getItem();
                while (ci2 == null)
                {
                    ci2 = plugin.dropsAPI.getItem();
                }
                pi.addItem(ci2);
                player.updateInventory();
                player.sendMessage(ChatColor.GREEN
                        + "You have been given a DiabloDrops item.");
                return true;
        }
    }
}
