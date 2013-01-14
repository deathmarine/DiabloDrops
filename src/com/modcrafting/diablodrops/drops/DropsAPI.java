package com.modcrafting.diablodrops.drops;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.items.Drop;
import com.modcrafting.diablodrops.items.IdentifyTome;
import com.modcrafting.diablodrops.items.Socket;
import com.modcrafting.diablodrops.tier.Tier;

public class DropsAPI
{
    private final DiabloDrops plugin;

    public DropsAPI(final DiabloDrops instance)
    {
        plugin = instance;
    }

    /**
     * Is material armor or tool?
     * 
     * @param material
     * @return is armor or tool
     */
    public boolean canBeItem(final Material material)
    {
        if (plugin.getItemAPI().isArmor(material)
                || plugin.getItemAPI().isTool(material))
            return true;
        return false;
    }

    public boolean canBeTier(Material mat, Tier tier)
    {
        if (tier.getMaterials() == null || tier.getMaterials().isEmpty()
                || tier.getMaterials().contains(mat))
            return true;
        return false;
    }

    /**
     * Gets a random color.
     * 
     * @return color
     */
    public ChatColor colorPicker()
    {
        return plugin.getSettings().getSocketColors()[plugin.getSingleRandom()
                .nextInt(plugin.getSettings().getSocketColors().length)];
    }

    /**
     * Determines if a List contains a string
     * 
     * @param l
     *            List to check
     * @param s
     *            String to find
     * @return if contains
     */
    public boolean containsIgnoreCase(final List<String> l, final String s)
    {
        Iterator<String> it = l.iterator();
        while (it.hasNext())
            if (it.next().equalsIgnoreCase(s))
                return true;
        return false;
    }

    /**
     * Gets a random amount of damage for an ItemStack
     * 
     * @param material
     *            of ItemStack
     * @return durability to be set
     */
    public short damageItemStack(final Material material)
    {
        short dur = material.getMaxDurability();
        try
        {
            int newDur = plugin.getSingleRandom().nextInt(dur);
            return (short) newDur;
        }
        catch (Exception e)
        {
            dur = 0;
        }
        return dur;
    }

    /**
     * Returns a Material that was randomly picked
     * 
     * @return random material
     */
    public Material dropPicker()
    {
        int next = plugin.getSingleRandom().nextInt(10);
        switch (next)
        {
            case 1:
                return plugin.getItemAPI().getHelmet();
            case 2:
                return plugin.getItemAPI().getChestPlate();
            case 3:
                return plugin.getItemAPI().getLeggings();
            case 4:
                return plugin.getItemAPI().getBoots();
            case 5:
                return plugin.getItemAPI().getHoe();
            case 6:
                return plugin.getItemAPI().getPickaxe();
            case 7:
                return plugin.getItemAPI().getAxe();
            case 8:
                return plugin.getItemAPI().getSpade();
            case 9:
                return Material.BOW;
            default:
                return plugin.getItemAPI().getSword();

        }
    }

    /**
     * Fills a chest with items.
     * 
     * @param block
     *            Block to fill
     */
    public void fillChest(final Block block)
    {
        try
        {
            if (!(block.getState() instanceof Chest))
                return;
            Chest chestB = ((Chest) block.getState());
            Inventory chest = chestB.getBlockInventory();
            for (int i = 0; i < plugin.getSingleRandom().nextInt(
                    chest.getSize()); i++)
            {
                ItemStack cis = getItem();
                while (cis == null)
                {
                    cis = getItem();
                }
                chest.setItem(i, cis);
            }
        }
        catch (Exception e)
        {
        }
    }

    /**
     * Fills a chest with items.
     * 
     * @param block
     *            Block to fill
     * @param size
     *            Rarity of chest
     */
    public void fillChest(final Block block, final int size)
    {
        try
        {
            if (!(block.getState() instanceof Chest))
                return;
            Chest chestB = ((Chest) block.getState());
            Inventory chest = chestB.getBlockInventory();
            for (int i = 0; i < size; i++)
            {
                ItemStack cis = getItem();
                while (cis == null)
                {
                    cis = getItem();
                }
                chest.setItem(i, cis);
            }
        }
        catch (Exception e)
        {
        }
    }

    /**
     * Get a particular custom itemstack
     * 
     * @param name
     * @return particular custom itemstack
     */
    public ItemStack getCustomItem(final String name)
    {
        for (ItemStack t : plugin.custom)
        {
            ItemMeta meta = t.getItemMeta();
            if (ChatColor.stripColor(meta.getDisplayName()).equalsIgnoreCase(
                    name))
                return t;
        }
        return null;
    }

    /**
     * Gets a list of safe enchantments for an item.
     * 
     * @param ci
     * @return set
     */
    public List<Enchantment> getEnchantStack(final ItemStack ci)
    {
        List<Enchantment> set = new ArrayList<Enchantment>();
        for (Enchantment e : Enchantment.values())
            if (e.canEnchantItem(ci))
            {
                set.add(e);
            }
        return set;
    }

    public ItemStack getIdItem(Material mat, final String name)
    {
        while (mat == null)
        {
            mat = dropPicker();
        }
        Material material = mat;
        ItemStack ci = null;
        Tier tier = getTier();
        while (tier == null)
        {
            tier = getTier();
        }
        if ((tier.getMaterials().size() > 0)
                && !tier.getMaterials().contains(material))
        {
            material = tier.getMaterials().get(
                    plugin.getSingleRandom()
                            .nextInt(tier.getMaterials().size()));
        }
        int e = tier.getAmount();
        int l = tier.getLevels();
        short damage = 0;
        if (plugin.getConfig().getBoolean("DropFix.Damage", true))
        {
            damage = damageItemStack(mat);
        }
        if (plugin.getConfig().getBoolean("Display.TierName", true)
                && !tier.getColor().equals(ChatColor.MAGIC))
        {
            ci = new Drop(material, tier.getColor(),
                    ChatColor.stripColor(name(mat)), damage, tier.getColor()
                            + tier.getDisplayName());
        }
        else if (plugin.getConfig().getBoolean("Display.TierName", true)
                && tier.getColor().equals(ChatColor.MAGIC))
        {
            ci = new Drop(material, tier.getColor(),
                    ChatColor.stripColor(name(mat)), damage, ChatColor.WHITE
                            + tier.getDisplayName());
        }
        else
        {
            ci = new Drop(material, tier.getColor(),
                    ChatColor.stripColor(name(mat)), damage);
        }
        if (tier.getColor().equals(ChatColor.MAGIC))
            return ci;
        List<Enchantment> eStack = Arrays.asList(Enchantment.values());
        boolean safe = plugin.getConfig().getBoolean("SafeEnchant.Enabled",
                true);
        if (safe)
        {
            eStack = getEnchantStack(ci);
        }
        for (; e > 0; e--)
        {
            int lvl = plugin.getSingleRandom().nextInt(l + 1);
            int size = eStack.size();
            if (size < 1)
            {
                continue;
            }
            Enchantment ench = eStack.get(plugin.getSingleRandom()
                    .nextInt(size));
            if ((lvl != 0) && (ench != null)
                    && !tier.getColor().equals(ChatColor.MAGIC))
                if (safe)
                {
                    if ((lvl >= ench.getStartLevel())
                            && (lvl <= ench.getMaxLevel()))
                    {
                        try
                        {
                            ci.addEnchantment(ench, lvl);
                        }
                        catch (Exception e1)
                        {
                            if (plugin.getDebug())
                            {
                                plugin.log.warning(e1.getMessage());
                            }
                            e++;
                        }
                    }
                }
                else
                {
                    ci.addUnsafeEnchantment(ench, lvl);
                }
        }
        ItemMeta tool;
        if (ci.hasItemMeta())
            tool = ci.getItemMeta();
        else
            tool = Bukkit.getItemFactory().getItemMeta(ci.getType());
        tool.setLore(tier.getLore());
        List<String> list = new ArrayList<String>();
        if (plugin.getConfig().getBoolean("SocketItem.Enabled", true)
                && (plugin.getSingleRandom().nextInt(10000) <= plugin
                        .getSettings().getSocketChance()))
        {
            int eni = plugin.getConfig().getInt("SocketItem.EnhanceBy", 1);
            int ene = plugin.getConfig().getInt("SocketItem.EnhanceMax", 10);
            int enhance = eni + plugin.getSingleRandom().nextInt(ene);
            for (int i = 0; i < enhance; i++)
            {
                list.add(colorPicker() + "(Socket)");
            }
            tool.setLore(list);
            ci.setItemMeta(tool);
            return ci;
        }
        if (plugin.getConfig().getBoolean("Lore.Enabled", true)
                && (plugin.getSingleRandom().nextInt(10000) <= plugin
                        .getSettings().getLoreChance()))
        {
            for (int i = 0; i < plugin.getConfig().getInt("Lore.EnhanceAmount",
                    2); i++)
                if (plugin.getItemAPI().isArmor(mat))
                {
                    if (plugin.ccdefenselore != null
                            && !plugin.ccdefenselore.isEmpty()
                            && plugin.ccdefenselore.get(tier.getColor()) != null
                            && plugin.getSingleRandom().nextBoolean())
                        list.add(plugin.ccdefenselore.get(tier.getColor()).get(
                                plugin.getSingleRandom().nextInt(
                                        plugin.ccdefenselore.get(
                                                tier.getColor()).size())));
                    else
                        list.add(plugin.defenselore.get(plugin
                                .getSingleRandom().nextInt(
                                        plugin.defenselore.size())));
                    tool.setLore(list);

                }
                else if (plugin.getItemAPI().isTool(mat))
                {
                    if (plugin.ccdefenselore != null
                            && !plugin.ccoffenselore.isEmpty()
                            && plugin.ccoffenselore.get(tier.getColor()) != null
                            && plugin.getSingleRandom().nextBoolean())
                        list.add(plugin.ccoffenselore.get(tier.getColor()).get(
                                plugin.getSingleRandom().nextInt(
                                        plugin.ccoffenselore.get(
                                                tier.getColor()).size())));
                    else
                        list.add(plugin.offenselore.get(plugin
                                .getSingleRandom().nextInt(
                                        plugin.offenselore.size())));
                    tool.setLore(list);
                }
        }
        ci.setItemMeta(tool);
        return ci;
    }

    /**
     * Returns an itemstack that was randomly getSingleRandom()erated
     * 
     * @return DiabloItemStack
     */
    public ItemStack getItem()
    {
        if (plugin.getSingleRandom().nextBoolean()
                && plugin.getConfig().getBoolean("IdentifyTome.Enabled", true)
                && (plugin.getSingleRandom().nextInt(10000) <= plugin
                        .getSettings().getTomeChance()))
            return new IdentifyTome();
        if (plugin.getSingleRandom().nextBoolean()
                && plugin.getConfig().getBoolean("SocketItem.Enabled", true)
                && (plugin.getSingleRandom().nextInt(10000) <= plugin
                        .getSettings().getSocketChance()))
        {
            List<String> l = plugin.getConfig().getStringList(
                    "SocketItem.Items");
            return new Socket(Material.valueOf(l.get(
                    plugin.getSingleRandom().nextInt(l.size())).toUpperCase()));
        }
        if (plugin.getSingleRandom().nextBoolean()
                && plugin.getConfig().getBoolean("Custom.Enabled", true)
                && (plugin.getSingleRandom().nextInt(10000) <= plugin
                        .getSettings().getCustomChance()))
            return plugin.custom.get(plugin.getSingleRandom().nextInt(
                    plugin.custom.size()));
        return getItem(dropPicker());
    }

    /**
     * Gets a new tool from an unidentified tool
     * 
     * @param tool
     * @return brand new tool
     */
    public ItemStack getItem(final ItemStack oldTool)
    {
        ItemStack tool = oldTool;
        short oldDam = tool.getDurability();
        tool = new ItemStack(tool.getType());
        tool.setDurability(oldDam);
        Tier tier = getTier();
        while ((tier == null) || tier.getColor().equals(ChatColor.MAGIC))
        {
            tier = getTier();
        }
        int e = tier.getAmount();
        int l = tier.getLevels();
        List<String> list = new ArrayList<String>();
        if (plugin.getSettings().isColorBlindCompat())
        {
            list.add(getPrettyMaterialName(tool.getType()));
        }
        if (plugin.getConfig().getBoolean("Display.TierName", true))
        {
            list.add(tier.getColor() + tier.getDisplayName());
        }
        for (String s : tier.getLore())
            if (s != null)
            {
                list.add(s);
            }
        List<Enchantment> eStack = Arrays.asList(Enchantment.values());
        boolean safe = plugin.getConfig().getBoolean("SafeEnchant.Enabled",
                true);
        if (safe)
        {
            eStack = getEnchantStack(tool);
        }
        for (; e > 0; e--)
        {
            int lvl = plugin.getSingleRandom().nextInt(l + 1);
            int size = eStack.size();
            if (size < 1)
            {
                continue;
            }
            Enchantment ench = eStack.get(plugin.getSingleRandom()
                    .nextInt(size));
            if ((lvl != 0) && (ench != null)
                    && !tier.getColor().equals(ChatColor.MAGIC))
                if (safe)
                {
                    if ((lvl >= ench.getStartLevel())
                            && (lvl <= ench.getMaxLevel()))
                    {
                        try
                        {
                            tool.addEnchantment(ench, lvl);
                        }
                        catch (Exception e1)
                        {
                            if (plugin.getDebug())
                            {
                                plugin.log.warning(e1.getMessage());
                            }
                            e++;
                        }
                    }
                }
                else
                {
                    tool.addUnsafeEnchantment(ench, lvl);
                }
        }
        ItemMeta meta;
        if (tool.hasItemMeta())
            meta = tool.getItemMeta();
        else
            meta = Bukkit.getItemFactory().getItemMeta(tool.getType());
        meta.setDisplayName(tier.getColor() + name(tool.getType()));
        boolean sock = false;
        if (plugin.getConfig().getBoolean("SocketItem.Enabled", true)
                && (plugin.getSingleRandom().nextInt(10000) <= plugin
                        .getSettings().getSocketChance())
                && !tier.getColor().equals(ChatColor.MAGIC))
        {
            int eni = plugin.getConfig().getInt("SocketItem.EnhanceBy", 1);
            int ene = plugin.getConfig().getInt("SocketItem.EnhanceMax", 10);
            int enhance = eni + plugin.getSingleRandom().nextInt(ene);
            for (int i = 0; i < enhance; i++)
            {
                list.add(colorPicker() + "(Socket)");
            }
            sock = true;
        }
        if (plugin.getConfig().getBoolean("Lore.Enabled", true)
                && (plugin.getSingleRandom().nextInt(100) <= plugin
                        .getSettings().getLoreChance())
                && !tier.getColor().equals(ChatColor.MAGIC) && !sock)
        {
            for (int i = 0; i < plugin.getConfig().getInt("Lore.EnhanceAmount",
                    2); i++)
                if (plugin.getItemAPI().isArmor(tool.getType()))
                {
                    list.add(colorPicker()
                            + plugin.defenselore.get(plugin.getSingleRandom()
                                    .nextInt(plugin.defenselore.size())));

                }
                else if (plugin.getItemAPI().isTool(tool.getType()))
                {
                    list.add(colorPicker()
                            + plugin.offenselore.get(plugin.getSingleRandom()
                                    .nextInt(plugin.offenselore.size())));
                }
        }
        meta.setLore(list);
        tool.setItemMeta(meta);
        return tool;
    }

    /**
     * Returns a specific type of item randomly getSingleRandom()erated
     * 
     * @param mat
     *            Material of itemstack
     * @return item
     */
    public ItemStack getItem(Material mat)
    {
        while (mat == null)
        {
            mat = dropPicker();
        }
        ItemStack ci = null;
        Tier tier = getTier();
        while (tier == null)
        {
            tier = getTier();
        }
        if ((tier.getMaterials().size() > 0)
                && !tier.getMaterials().contains(mat))
        {
            mat = tier.getMaterials().get(
                    plugin.getSingleRandom()
                            .nextInt(tier.getMaterials().size()));
        }
        int e = tier.getAmount();
        int l = tier.getLevels();
        short damage = 0;
        if (plugin.getConfig().getBoolean("DropFix.Damage", true))
        {
            damage = damageItemStack(mat);
        }
        List<String> startList = new ArrayList<String>();
        if (plugin.getSettings().isColorBlindCompat())
        {
            startList.add(getPrettyMaterialName(mat));
        }
        if (plugin.getConfig().getBoolean("Display.TierName", true)
                && !tier.getColor().equals(ChatColor.MAGIC))
        {
            startList.add(tier.getColor() + tier.getDisplayName());
        }
        else if (plugin.getConfig().getBoolean("Display.TierName", true)
                && tier.getColor().equals(ChatColor.MAGIC))
        {
            startList.add(ChatColor.WHITE + tier.getDisplayName());
        }
        ci = new Drop(mat, tier.getColor(), ChatColor.stripColor(name(mat)),
                damage, startList.toArray(new String[0]));
        if (tier.getColor().equals(ChatColor.MAGIC))
            return ci;
        ItemStack tool = new ItemStack(ci);
        List<Enchantment> eStack = Arrays.asList(Enchantment.values());
        List<String> list = new ArrayList<String>();
        if (plugin.getConfig().getBoolean("Display.TierName", true))
        {
            list.add(tier.getColor() + tier.getDisplayName());
        }
        for (String s : tier.getLore())
            if (s != null)
            {
                list.add(s);
            }

        boolean safe = plugin.getConfig().getBoolean("SafeEnchant.Enabled",
                true);
        if (safe)
        {
            eStack = getEnchantStack(tool);
        }
        for (; e > 0; e--)
        {
            int lvl = plugin.getSingleRandom().nextInt(l + 1);
            int size = eStack.size();
            if (size < 1)
            {
                continue;
            }
            Enchantment ench = eStack.get(plugin.getSingleRandom()
                    .nextInt(size));
            if ((lvl != 0) && (ench != null)
                    && !tier.getColor().equals(ChatColor.MAGIC))
                if (safe)
                {
                    if ((lvl >= ench.getStartLevel())
                            && (lvl <= ench.getMaxLevel()))
                    {
                        try
                        {
                            tool.addEnchantment(ench, lvl);
                        }
                        catch (Exception e1)
                        {
                            if (plugin.getDebug())
                            {
                                plugin.log.warning(e1.getMessage());
                            }
                            e++;
                        }
                    }
                }
                else
                {
                    tool.addUnsafeEnchantment(ench, lvl);
                }
        }
        ItemMeta meta;
        if (tool.hasItemMeta())
            meta = tool.getItemMeta();
        else
            meta = Bukkit.getItemFactory().getItemMeta(tool.getType());
        if (plugin.getConfig().getBoolean("SocketItem.Enabled", true)
                && (plugin.getSingleRandom().nextInt(10000) <= plugin
                        .getSettings().getSocketChance())
                && !tier.getColor().equals(ChatColor.MAGIC))
        {
            int eni = plugin.getConfig().getInt("SocketItem.EnhanceBy", 1);
            int ene = plugin.getConfig().getInt("SocketItem.EnhanceMax", 10);
            int enhance = eni + plugin.getSingleRandom().nextInt(ene);
            for (int i = 0; i < enhance; i++)
            {
                list.add(colorPicker() + "(Socket)");
            }
            meta.setLore(list);
            tool.setItemMeta(meta);
            return tool;
        }
        if (plugin.getConfig().getBoolean("Lore.Enabled", true)
                && (plugin.getSingleRandom().nextInt(10000) <= plugin
                        .getSettings().getLoreChance())
                && !tier.getColor().equals(ChatColor.MAGIC))
        {
            for (int i = 0; i < plugin.getConfig().getInt("Lore.EnhanceAmount",
                    2); i++)
                if (plugin.getItemAPI().isArmor(mat))
                {

                    list.add(colorPicker()
                            + plugin.defenselore.get(plugin.getSingleRandom()
                                    .nextInt(plugin.defenselore.size())));

                }
                else if (plugin.getItemAPI().isTool(mat))
                {
                    list.add(colorPicker()
                            + plugin.offenselore.get(plugin.getSingleRandom()
                                    .nextInt(plugin.offenselore.size())));
                }
        }
        meta.setLore(list);
        tool.setItemMeta(meta);
        if (plugin.getItemAPI().isLeather(tool.getType()))
        {
            LeatherArmorMeta lam = (LeatherArmorMeta) tool.getItemMeta();
            lam.setColor(Color.fromRGB(plugin.getSingleRandom().nextInt(255),
                    plugin.getSingleRandom().nextInt(255), plugin
                            .getSingleRandom().nextInt(255)));
            tool.setItemMeta(lam);
        }
        return tool;
    }

    /**
     * Returns a specific type of item randomly getSingleRandom()erated
     * 
     * @param mat
     *            Material of itemstack
     * @return item
     */
    public ItemStack getItem(Material passMat, Tier passTier)
    {
        Material mat = passMat;
        Tier tier = passTier;
        while (mat == null)
        {
            mat = dropPicker();
        }
        ItemStack ci = null;
        while (tier == null)
        {
            tier = getTier();
        }
        if ((tier.getMaterials().size() > 0)
                && !tier.getMaterials().contains(mat))
        {
            mat = tier.getMaterials().get(
                    plugin.getSingleRandom()
                            .nextInt(tier.getMaterials().size()));
        }
        int e = tier.getAmount();
        int l = tier.getLevels();
        short damage = 0;
        if (plugin.getConfig().getBoolean("DropFix.Damage", true))
        {
            damage = damageItemStack(mat);
        }
        List<String> startList = new ArrayList<String>();
        if (plugin.getSettings().isColorBlindCompat())
        {
            startList.add(getPrettyMaterialName(mat));
        }
        if (plugin.getConfig().getBoolean("Display.TierName", true)
                && !tier.getColor().equals(ChatColor.MAGIC))
        {
            startList.add(tier.getColor() + tier.getDisplayName());
        }
        else if (plugin.getConfig().getBoolean("Display.TierName", true)
                && tier.getColor().equals(ChatColor.MAGIC))
        {
            startList.add(ChatColor.WHITE + tier.getDisplayName());
        }
        ci = new Drop(mat, tier.getColor(), ChatColor.stripColor(name(mat)),
                damage, startList.toArray(new String[0]));
        if (tier.getColor().equals(ChatColor.MAGIC))
            return ci;
        ItemStack tool = new ItemStack(ci);
        List<Enchantment> eStack = Arrays.asList(Enchantment.values());
        List<String> list = new ArrayList<String>();
        if (plugin.getConfig().getBoolean("Display.TierName", true))
        {
            list.add(tier.getColor() + tier.getDisplayName());
        }
        for (String s : tier.getLore())
            if (s != null)
            {
                list.add(s);
            }

        boolean safe = plugin.getConfig().getBoolean("SafeEnchant.Enabled",
                true);
        if (safe)
        {
            eStack = getEnchantStack(tool);
        }
        for (; e > 0; e--)
        {
            int lvl = plugin.getSingleRandom().nextInt(l + 1);
            int size = eStack.size();
            if (size < 1)
            {
                continue;
            }
            Enchantment ench = eStack.get(plugin.getSingleRandom()
                    .nextInt(size));
            if ((lvl != 0) && (ench != null)
                    && !tier.getColor().equals(ChatColor.MAGIC))
                if (safe)
                {
                    if ((lvl >= ench.getStartLevel())
                            && (lvl <= ench.getMaxLevel()))
                    {
                        try
                        {
                            tool.addEnchantment(ench, lvl);
                        }
                        catch (Exception e1)
                        {
                            if (plugin.getDebug())
                            {
                                plugin.log.warning(e1.getMessage());
                            }
                            e++;
                        }
                    }
                }
                else
                {
                    tool.addUnsafeEnchantment(ench, lvl);
                }
        }
        ItemMeta meta;
        if (tool.hasItemMeta())
            meta = tool.getItemMeta();
        else
            meta = Bukkit.getItemFactory().getItemMeta(tool.getType());
        if (plugin.getConfig().getBoolean("SocketItem.Enabled", true)
                && (plugin.getSingleRandom().nextInt(10000) <= plugin
                        .getSettings().getSocketChance())
                && !tier.getColor().equals(ChatColor.MAGIC))
        {
            int eni = plugin.getConfig().getInt("SocketItem.EnhanceBy", 1);
            int ene = plugin.getConfig().getInt("SocketItem.EnhanceMax", 10);
            int enhance = eni + plugin.getSingleRandom().nextInt(ene);
            for (int i = 0; i < enhance; i++)
            {
                list.add(colorPicker() + "(Socket)");
            }
            meta.setLore(list);
            tool.setItemMeta(meta);
            return tool;
        }
        if (plugin.getConfig().getBoolean("Lore.Enabled", true)
                && (plugin.getSingleRandom().nextInt(10000) <= plugin
                        .getSettings().getLoreChance())
                && !tier.getColor().equals(ChatColor.MAGIC))
        {
            for (int i = 0; i < plugin.getConfig().getInt("Lore.EnhanceAmount",
                    2); i++)
                if (plugin.getItemAPI().isArmor(mat))
                {
                    list.add(colorPicker()
                            + plugin.defenselore.get(plugin.getSingleRandom()
                                    .nextInt(plugin.defenselore.size())));

                }
                else if (plugin.getItemAPI().isTool(mat))
                {
                    list.add(colorPicker()
                            + plugin.offenselore.get(plugin.getSingleRandom()
                                    .nextInt(plugin.offenselore.size())));
                }
        }
        meta.setLore(list);
        tool.setItemMeta(meta);
        if (plugin.getItemAPI().isLeather(tool.getType()))
        {
            LeatherArmorMeta lam = (LeatherArmorMeta) tool.getItemMeta();
            lam.setColor(Color.fromRGB(plugin.getSingleRandom().nextInt(255),
                    plugin.getSingleRandom().nextInt(255), plugin
                            .getSingleRandom().nextInt(255)));
            tool.setItemMeta(lam);
        }
        return tool;
    }

    /**
     * Returns an specific type of ItemStack that was randomly generated
     * 
     * @param tier
     *            name
     * @return item
     */
    public ItemStack getItem(Tier tier)
    {
        Material mat = dropPicker();
        while (mat == null)
        {
            mat = dropPicker();
        }
        while (tier == null)
        {
            tier = getTier();
        }
        ItemStack ci = null;
        if ((tier.getMaterials().size() > 0)
                && !tier.getMaterials().contains(mat))
        {
            mat = tier.getMaterials().get(
                    plugin.getSingleRandom()
                            .nextInt(tier.getMaterials().size()));
        }
        int e = tier.getAmount();
        int l = tier.getLevels();
        short damage = 0;
        if (plugin.getConfig().getBoolean("DropFix.Damage", true))
        {
            damage = damageItemStack(mat);
        }
        List<String> startList = new ArrayList<String>();
        if (plugin.getSettings().isColorBlindCompat())
        {
            startList.add(getPrettyMaterialName(mat));
        }
        if (plugin.getConfig().getBoolean("Display.TierName", true)
                && !tier.getColor().equals(ChatColor.MAGIC))
        {
            startList.add(tier.getColor() + tier.getDisplayName());
        }
        else if (plugin.getConfig().getBoolean("Display.TierName", true)
                && tier.getColor().equals(ChatColor.MAGIC))
        {
            startList.add(ChatColor.WHITE + tier.getDisplayName());
        }
        ci = new Drop(mat, tier.getColor(), ChatColor.stripColor(name(mat)),
                damage, startList.toArray(new String[0]));
        if (tier.getColor().equals(ChatColor.MAGIC))
            return ci;
        ItemStack tool = new ItemStack(ci);
        List<String> list = new ArrayList<String>();
        if (plugin.getConfig().getBoolean("Display.TierName", true))
        {
            list.add(tier.getColor() + tier.getDisplayName());
        }
        for (String s : tier.getLore())
            if (s != null)
            {
                list.add(s);
            }
        List<Enchantment> eStack = Arrays.asList(Enchantment.values());
        boolean safe = plugin.getConfig().getBoolean("SafeEnchant.Enabled",
                true);
        if (safe)
        {
            eStack = getEnchantStack(ci);
        }
        for (; e > 0; e--)
        {
            int lvl = plugin.getSingleRandom().nextInt(l + 1);
            int size = eStack.size();
            if (size < 1)
            {
                continue;
            }
            Enchantment ench = eStack.get(plugin.getSingleRandom()
                    .nextInt(size));
            if ((lvl != 0) && (ench != null)
                    && !tier.getColor().equals(ChatColor.MAGIC))
                if (safe)
                {
                    if ((lvl >= ench.getStartLevel())
                            && (lvl <= ench.getMaxLevel()))
                    {
                        try
                        {
                            tool.addEnchantment(ench, lvl);
                        }
                        catch (Exception e1)
                        {
                            if (plugin.getDebug())
                            {
                                plugin.log.warning(e1.getMessage());
                            }
                            e++;
                        }
                    }
                }
                else
                {
                    tool.addUnsafeEnchantment(ench, lvl);
                }
        }
        ItemMeta meta = tool.getItemMeta();
        if (plugin.getConfig().getBoolean("SocketItem.Enabled", true)
                && (plugin.getSingleRandom().nextInt(10000) <= plugin
                        .getSettings().getSocketChance())
                && !tier.getColor().equals(ChatColor.MAGIC))
        {
            int eni = plugin.getConfig().getInt("SocketItem.EnhanceBy", 1);
            int ene = plugin.getConfig().getInt("SocketItem.EnhanceMax", 10);
            int enhance = eni + plugin.getSingleRandom().nextInt(ene);
            for (int i = 0; i < enhance; i++)
            {
                list.add(colorPicker() + "(Socket)");
            }
            meta.setLore(list);
            tool.setItemMeta(meta);
            return tool;
        }
        if (plugin.getConfig().getBoolean("Lore.Enabled", true)
                && (plugin.getSingleRandom().nextInt(10000) <= plugin
                        .getSettings().getLoreChance())
                && !tier.getColor().equals(ChatColor.MAGIC))
        {
            for (int i = 0; i < plugin.getConfig().getInt("Lore.EnhanceAmount",
                    2); i++)
                if (plugin.getItemAPI().isArmor(tool.getType()))
                {
                    list.add(plugin.defenselore.get(plugin.getSingleRandom()
                            .nextInt(plugin.defenselore.size())));

                }
                else if (plugin.getItemAPI().isTool(tool.getType()))
                {
                    list.add(plugin.offenselore.get(plugin.getSingleRandom()
                            .nextInt(plugin.offenselore.size())));
                }
        }
        meta.setLore(list);
        tool.setItemMeta(meta);
        if (plugin.getItemAPI().isLeather(tool.getType()))
        {
            LeatherArmorMeta lam = (LeatherArmorMeta) tool.getItemMeta();
            lam.setColor(Color.fromRGB(plugin.getSingleRandom().nextInt(255),
                    plugin.getSingleRandom().nextInt(255), plugin
                            .getSingleRandom().nextInt(255)));
            tool.setItemMeta(lam);
        }
        return tool;
    }

    public String getPrettyMaterialName(Material material)
    {
        String prettyMaterialName = "";
        String matName = material.name();
        String[] split = matName.split("_");
        for (String s : split)
        {
            prettyMaterialName = prettyMaterialName
                    + (s.substring(0, 1).toUpperCase() + s.substring(1,
                            s.length()).toLowerCase()) + " ";
        }
        return prettyMaterialName;
    }

    /**
     * Gets a calculated tier.
     * 
     * @return tier
     */
    public Tier getTier()
    {
        for (Tier tier : plugin.tiers)
            if (plugin.getSingleRandom().nextInt(10000) <= tier.getChance())
                return tier;
        return null;
    }

    public Tier getTier(ItemStack item)
    {
        if (!item.hasItemMeta() || !item.getItemMeta().hasLore())
            return null;
        for (String s : item.getItemMeta().getLore())
        {
            Tier tier = getTier(s);
            if (tier != null)
                return tier;
        }
        return null;
    }

    /**
     * Gets tier from name
     * 
     * @param name
     * @return tier
     */
    public Tier getTier(final String name)
    {
        for (Tier tier : plugin.tiers)
            if (tier.getDisplayName().equalsIgnoreCase(name)
                    || tier.getName().equalsIgnoreCase(name))
                return tier;
        return null;
    }

    /**
     * Is type an actual tier?
     * 
     * @param type
     * @return is tier
     */
    public boolean matchesTier(final String type)
    {
        for (Tier tier : plugin.tiers)
            if (tier.getDisplayName().equalsIgnoreCase(type)
                    || tier.getName().equalsIgnoreCase(type))
                return true;
        return false;
    }

    /**
     * Gets a random name
     * 
     * @param material
     *            Material of item to get name for
     * @return name
     */
    public String name(final Material material)
    {
        String template = plugin.getConfig().getString(
                "Display.ItemNameFormat", "%randprefix% %randsuffix%");
        String prefix = "";
        String suffix = "";
        String matName = material.name();
        if (plugin.hmprefix.containsKey(material))
        {
            List<String> l = plugin.hmprefix.get(material);
            prefix = l.get(plugin.getSingleRandom().nextInt(l.size()));
        }
        if (plugin.hmsuffix.containsKey(material))
        {
            List<String> l = plugin.hmsuffix.get(material);
            suffix = l.get(plugin.getSingleRandom().nextInt(l.size()));
        }
        boolean t = plugin.getConfig().getBoolean("Display.ItemMaterialExtras",
                false);
        if ((prefix.length() < 1) || !t)
        {
            prefix = plugin.prefix.get(plugin.getSingleRandom().nextInt(
                    plugin.prefix.size()));
        }
        if ((suffix.length() < 1) || !t)
        {
            suffix = plugin.suffix.get(plugin.getSingleRandom().nextInt(
                    plugin.suffix.size()));
        }

        if (template == null)
            return null;
        return template.replace("%randprefix%", prefix)
                .replace("%randsuffix%", suffix).replace("%matname%", matName);
    }

    /**
     * Replace a line of lore with another line
     * 
     * @param tool
     *            Tool to replace lore on
     * @param toReplace
     *            Line of lore to be replaced
     * @param replaceWith
     *            Line replacing toReplace
     * @return Tool with new lore
     */
    public ItemStack replaceLore(final ItemStack tool, final String toReplace,
            final String replaceWith)
    {
        ItemMeta meta = tool.getItemMeta();
        List<String> loreList = meta.getLore();
        if ((loreList == null) || loreList.isEmpty())
            return tool;
        for (String s : meta.getLore())
            if (s.equals(toReplace))
            {
                loreList.remove(s);
                loreList.add(replaceWith);
            }
        meta.setLore(loreList);
        tool.setItemMeta(meta);
        return tool;
    }
}