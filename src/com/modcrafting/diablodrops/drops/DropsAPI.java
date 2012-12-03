package com.modcrafting.diablodrops.drops;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.items.Drop;
import com.modcrafting.diablodrops.items.Socket;
import com.modcrafting.diablodrops.items.Tome;
import com.modcrafting.diablodrops.tier.Tier;
import com.modcrafting.toolapi.lib.Tool;

public class DropsAPI
{
    private final DiabloDrops plugin;

    public DropsAPI(DiabloDrops instance)
    {
        plugin = instance;
    }

    /**
     * Is material armor or tool?
     * 
     * @param material
     * @return is armor or tool
     */
    public boolean canBeItem(Material material)
    {
        if (plugin.drop.isArmor(material) || plugin.drop.isTool(material))
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
    public short damageItemStack(Material itemstack)
    {
        short dur = itemstack.getMaxDurability();
        try
        {
            int newDur = plugin.gen.nextInt(dur + 1);
            return (short) newDur;
        }
        catch (Exception e)
        {
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
        int next = plugin.gen.nextInt(10);
        switch (next)
        {
            case 1:
                return plugin.drop.getHelmet();
            case 2:
                return plugin.drop.getChestPlate();
            case 3:
                return plugin.drop.getLeggings();
            case 4:
                return plugin.drop.getBoots();
            case 5:
                return plugin.drop.getHoe();
            case 6:
                return plugin.drop.getPickaxe();
            case 7:
                return plugin.drop.getAxe();
            case 8:
                return plugin.drop.getSpade();
            case 9:
                return Material.BOW;
            default:
                return plugin.drop.getSword();

        }
    }

    /**
     * Gets a list of safe enchantments for an item.
     * 
     * @param ci
     * @return set
     */
    public List<Enchantment> getEnchantStack(CraftItemStack ci)
    {
        List<Enchantment> set = new ArrayList<Enchantment>();
        for (Enchantment e : Enchantment.values())
        {
            if (e.canEnchantItem(ci))
            {
                set.add(e);
            }
        }
        return set;
    }

    public CraftItemStack getIdItem(Material mat, String name)
    {
        while (mat == null)
            mat = dropPicker();
        Material material = mat;
        CraftItemStack ci = null;
        Tier tier = getTier();
        while (tier == null)
        {
            tier = getTier();
        }
        if (tier.getMaterials().size() > 0
                && !tier.getMaterials().contains(material))
        {
            material = tier.getMaterials().get(
                    plugin.gen.nextInt(tier.getMaterials().size()));
        }
        int e = tier.getAmount();
        int l = tier.getLevels();
        short damage = 0;
        if (plugin.config.getBoolean("DropFix.Damage", true))
            damage = damageItemStack(mat);
        if (plugin.config.getBoolean("Display.TierName", true)
                && !tier.getColor().equals(ChatColor.MAGIC))
        {
            ci = new Drop(material, tier.getColor(),
                    ChatColor.stripColor(name()), damage, tier.getColor()
                            + tier.getName());
        }
        else if (plugin.config.getBoolean("Display.TierName", true)
                && tier.getColor().equals(ChatColor.MAGIC))
        {
            ci = new Drop(material, tier.getColor(),
                    ChatColor.stripColor(name()), damage, ChatColor.WHITE
                            + tier.getName());
        }
        else
        {
            ci = new Drop(material, tier.getColor(),
                    ChatColor.stripColor(name()), damage);
        }
        if (tier.getColor().equals(ChatColor.MAGIC))
            return ci;
        List<Enchantment> eStack = Arrays.asList(Enchantment.values());
        boolean safe = plugin.config.getBoolean("SafeEnchant.Enabled", true);
        if (safe)
            eStack = getEnchantStack(ci);
        for (; e > 0; e--)
        {
            int lvl = plugin.gen.nextInt(l + 1);
            int size = eStack.size();
            if (size < 1)
                continue;
            Enchantment ench = eStack.get(plugin.gen.nextInt(size));
            if (lvl != 0 && ench != null
                    && !tier.getColor().equals(ChatColor.MAGIC))
            {
                if (safe)
                {
                    if (lvl >= ench.getStartLevel()
                            && lvl <= ench.getMaxLevel())
                    {
                        try
                        {
                            ci.addEnchantment(ench, lvl);
                        }
                        catch (Exception e1)
                        {
                            if (plugin.debug)
                                plugin.log.warning(e1.getMessage());
                            e++;
                        }
                    }
                }
                else
                {
                    ci.addUnsafeEnchantment(ench, lvl);
                }
            }
        }
        Tool tool = new Tool(ci);
        if (plugin.config.getBoolean("SocketItem.Enabled", true)
                && plugin.gen.nextInt(100) <= plugin.config.getInt(
                        "SocketItem.Chance", 5))
        {
            tool.addLore("(Socket)");
            return tool;
        }
        if (plugin.config.getBoolean("Lore.Enabled", true)
                && plugin.gen.nextInt(100) <= plugin.config.getInt(
                        "Lore.Chance", 5))
        {
            for (int i = 0; i < plugin.config.getInt("Lore.EnhanceAmount", 2); i++)
            {
                if (plugin.drop.isArmor(mat))
                {
                    tool.addLore(plugin.defenselore.get(plugin.gen
                            .nextInt(plugin.defenselore.size())));
                }
                else if (plugin.drop.isTool(mat))
                {
                    tool.addLore(plugin.offenselore.get(plugin.gen
                            .nextInt(plugin.offenselore.size())));
                }
            }
        }
        return tool;
    }

    /**
     * Returns an itemstack that was randomly generated
     * 
     * @return CraftItemStack
     */
    public CraftItemStack getItem()
    {
        if (plugin.gen.nextBoolean()
                && plugin.config.getBoolean("IdentifyTome.Enabled", true)
                && plugin.gen.nextInt(100) <= plugin.config.getInt(
                        "IdentifyTome.Chance", 3))
        {
            return new Tome();
        }
        if (plugin.gen.nextBoolean()
                && plugin.config.getBoolean("SocketItem.Enabled", true)
                && plugin.gen.nextInt(100) <= plugin.config.getInt(
                        "SocketItem.Chance", 5))
        {
            List<String> l = plugin.config.getStringList("SocketItem.Items");
            return new Socket(Material.valueOf(l.get(
                    plugin.gen.nextInt(l.size())).toUpperCase()));
        }
        if (plugin.gen.nextBoolean()
                && plugin.config.getBoolean("Custom.Enabled", true)
                && plugin.gen.nextInt(100) <= plugin.config.getInt(
                        "Custom.Chance", 1))
        {
            return plugin.custom.get(plugin.gen.nextInt(plugin.custom.size()));
        }
        return getItem(dropPicker());
    }

    /**
     * Get a particular custom itemstack
     * 
     * @param name
     * @return particular custom itemstack
     */
    public CraftItemStack getCustomItem(String name)
    {
        CraftItemStack cis = null;
        for (CraftItemStack item : plugin.custom)
        {
            if (((Tool) item).getName().equalsIgnoreCase(name))
            {
                cis = item;
            }
        }
        return cis;
    }

    /**
     * Returns a specific type of item randomly generated
     * 
     * @param mat
     *            Material of itemstack
     * @return item
     */
    public CraftItemStack getItem(Material mat)
    {
        while (mat == null)
            mat = dropPicker();
        CraftItemStack ci = null;
        Tier tier = getTier();
        while (tier == null)
        {
            tier = getTier();
        }
        if (tier.getMaterials().size() > 0
                && !tier.getMaterials().contains(mat))
            mat = tier.getMaterials().get(
                    plugin.gen.nextInt(tier.getMaterials().size()));
        int e = tier.getAmount();
        int l = tier.getLevels();
        short damage = 0;
        if (plugin.config.getBoolean("DropFix.Damage", true))
            damage = damageItemStack(mat);
        if (plugin.config.getBoolean("Display.TierName", true)
                && !tier.getColor().equals(ChatColor.MAGIC))
        {
            ci = new Drop(mat, tier.getColor(), ChatColor.stripColor(name()),
                    damage, tier.getColor() + tier.getName());
        }
        else if (plugin.config.getBoolean("Display.TierName", true)
                && tier.getColor().equals(ChatColor.MAGIC))
        {
            ci = new Drop(mat, tier.getColor(), ChatColor.stripColor(name()),
                    damage, ChatColor.WHITE + tier.getName());
        }
        else
        {
            ci = new Drop(mat, tier.getColor(), ChatColor.stripColor(name()),
                    damage);
        }
        if (tier.getColor().equals(ChatColor.MAGIC))
            return ci;
        List<Enchantment> eStack = Arrays.asList(Enchantment.values());
        boolean safe = plugin.config.getBoolean("SafeEnchant.Enabled", true);
        if (safe)
            eStack = getEnchantStack(ci);
        for (; e > 0; e--)
        {
            int lvl = plugin.gen.nextInt(l + 1);
            int size = eStack.size();
            if (size < 1)
                continue;
            Enchantment ench = eStack.get(plugin.gen.nextInt(size));
            if (lvl != 0 && ench != null
                    && !tier.getColor().equals(ChatColor.MAGIC))
            {
                if (safe)
                {
                    if (lvl >= ench.getStartLevel()
                            && lvl <= ench.getMaxLevel())
                    {
                        try
                        {
                            ci.addEnchantment(ench, lvl);
                        }
                        catch (Exception e1)
                        {
                            if (plugin.debug)
                                plugin.log.warning(e1.getMessage());
                            e++;
                        }
                    }
                }
                else
                {
                    ci.addUnsafeEnchantment(ench, lvl);
                }
            }
        }
        Tool tool = new Tool(ci);
        if (plugin.config.getBoolean("SocketItem.Enabled", true)
                && plugin.gen.nextInt(100) <= plugin.config.getInt(
                        "SocketItem.Chance", 5)
                && !tier.getColor().equals(ChatColor.MAGIC))
        {
            tool.addLore("(Socket)");
            return tool;
        }
        if (plugin.config.getBoolean("Lore.Enabled", true)
                && plugin.gen.nextInt(100) <= plugin.config.getInt(
                        "Lore.Chance", 5)
                && !tier.getColor().equals(ChatColor.MAGIC))
        {
            for (int i = 0; i < plugin.config.getInt("Lore.EnhanceAmount", 2); i++)
            {
                if (plugin.drop.isArmor(mat))
                {
                    tool.addLore(plugin.defenselore.get(plugin.gen
                            .nextInt(plugin.defenselore.size())));
                }
                else if (plugin.drop.isTool(mat))
                {
                    tool.addLore(plugin.offenselore.get(plugin.gen
                            .nextInt(plugin.offenselore.size())));
                }
            }
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
    public CraftItemStack getItem(Tier tier)
    {
        Material mat = dropPicker();
        while (mat == null)
            mat = dropPicker();
        CraftItemStack ci = null;
        if (tier.getMaterials().size() > 0
                && !tier.getMaterials().contains(mat))
        {
            mat = tier.getMaterials().get(
                    plugin.gen.nextInt(tier.getMaterials().size()));
        }
        int e = tier.getAmount();
        int l = tier.getLevels();
        short damage = 0;
        if (plugin.config.getBoolean("DropFix.Damage", true))
        {
            damage = damageItemStack(mat);
        }
        if (plugin.config.getBoolean("Display.TierName", true)
                && !tier.getColor().equals(ChatColor.MAGIC))
        {
            ci = new Drop(mat, tier.getColor(), ChatColor.stripColor(name()),
                    damage, tier.getColor() + tier.getName());
        }
        else if (plugin.config.getBoolean("Display.TierName", true)
                && tier.getColor().equals(ChatColor.MAGIC))
        {
            ci = new Drop(mat, tier.getColor(), ChatColor.stripColor(name()),
                    damage, ChatColor.WHITE + tier.getName());
        }
        else
        {
            ci = new Drop(mat, tier.getColor(), ChatColor.stripColor(name()),
                    damage);
        }
        if (tier.getColor().equals(ChatColor.MAGIC))
            return ci;
        List<Enchantment> eStack = Arrays.asList(Enchantment.values());
        boolean safe = plugin.config.getBoolean("SafeEnchant.Enabled", true);
        if (safe)
            eStack = getEnchantStack(ci);
        for (; e > 0; e--)
        {
            int lvl = plugin.gen.nextInt(l + 1);
            int size = eStack.size();
            if (size < 1)
                continue;
            Enchantment ench = eStack.get(plugin.gen.nextInt(size));
            if (lvl != 0 && ench != null
                    && !tier.getColor().equals(ChatColor.MAGIC))
            {
                if (safe)
                {
                    if (lvl >= ench.getStartLevel()
                            && lvl <= ench.getMaxLevel())
                    {
                        try
                        {
                            ci.addEnchantment(ench, lvl);
                        }
                        catch (Exception e1)
                        {
                            if (plugin.debug)
                                plugin.log.warning(e1.getMessage());
                            e++;
                        }
                    }
                }
                else
                {
                    ci.addUnsafeEnchantment(ench, lvl);
                }
            }
        }
        Tool tool = new Tool(ci);
        if (plugin.config.getBoolean("SocketItem.Enabled", true)
                && plugin.gen.nextInt(100) <= plugin.config.getInt(
                        "SocketItem.Chance", 5)
                && !tier.getColor().equals(ChatColor.MAGIC))
        {
            tool.addLore("(Socket)");
            return tool;
        }
        if (plugin.config.getBoolean("Lore.Enabled", true)
                && plugin.gen.nextInt(100) <= plugin.config.getInt(
                        "Lore.Chance", 5)
                && !tier.getColor().equals(ChatColor.MAGIC))
        {
            for (int i = 0; i < plugin.config.getInt("Lore.EnhanceAmount", 2); i++)
            {
                if (plugin.drop.isArmor(mat))
                {
                    tool.addLore(plugin.defenselore.get(plugin.gen
                            .nextInt(plugin.defenselore.size())));
                }
                else if (plugin.drop.isTool(mat))
                {
                    tool.addLore(plugin.offenselore.get(plugin.gen
                            .nextInt(plugin.offenselore.size())));
                }
            }
        }

        return tool;
    }

    /**
     * Gets a new tool from an unidentified tool
     * 
     * @param tool
     * @return brand new tool
     */
    public Tool getItem(Tool tool)
    {
        short oldDam = tool.getDurability();
        tool = new Tool(tool.getType());
        tool.setDurability(oldDam);
        Tier tier = getTier();
        while (tier == null || tier.getColor().equals(ChatColor.MAGIC))
        {
            tier = getTier();
        }
        int e = tier.getAmount();
        int l = tier.getLevels();
        tool.setName(tier.getColor() + name());
        List<Enchantment> eStack = Arrays.asList(Enchantment.values());
        boolean safe = plugin.config.getBoolean("SafeEnchant.Enabled", true);
        if (safe)
            eStack = getEnchantStack(tool);
        for (; e > 0; e--)
        {
            int lvl = plugin.gen.nextInt(l + 1);
            int size = eStack.size();
            if (size < 1)
                continue;
            Enchantment ench = eStack.get(plugin.gen.nextInt(size));
            if (lvl != 0 && ench != null
                    && !tier.getColor().equals(ChatColor.MAGIC))
            {
                if (safe)
                {
                    if (lvl >= ench.getStartLevel()
                            && lvl <= ench.getMaxLevel())
                    {
                        try
                        {
                            tool.addEnchantment(ench, lvl);
                        }
                        catch (Exception e1)
                        {
                            if (plugin.debug)
                                plugin.log.warning(e1.getMessage());
                            e++;
                        }
                    }
                }
                else
                {
                    tool.addUnsafeEnchantment(ench, lvl);
                }
            }
        }
        if (plugin.config.getBoolean("Display.TierName", true))
            tool.addLore(tier.getColor() + tier.getName());
        boolean sock = false;
        if (plugin.config.getBoolean("SocketItem.Enabled", true)
                && plugin.gen.nextInt(100) <= plugin.config.getInt(
                        "SocketItem.Chance", 5)
                && !tier.getColor().equals(ChatColor.MAGIC))
        {
            tool.addLore("(Socket)");
            sock = true;
        }
        if (plugin.config.getBoolean("Lore.Enabled", true)
                && plugin.gen.nextInt(100) <= plugin.config.getInt(
                        "Lore.Chance", 5)
                && !tier.getColor().equals(ChatColor.MAGIC) && !sock)
        {
            for (int i = 0; i < plugin.config.getInt("Lore.EnhanceAmount", 2); i++)
            {

                if (plugin.drop.isArmor(tool.getType()))
                {
                    tool.addLore(plugin.defenselore.get(plugin.gen
                            .nextInt(plugin.defenselore.size())));
                }
                else if (plugin.drop.isTool(tool.getType()))
                {
                    tool.addLore(plugin.offenselore.get(plugin.gen
                            .nextInt(plugin.offenselore.size())));
                }
            }
        }
        return tool;
    }

    /**
     * Gets a calculated tier.
     * 
     * @return tier
     */
    public Tier getTier()
    {
        for (Tier tier : plugin.tiers)
        {
            if (plugin.gen.nextInt(100) <= tier.getChance())
            {
                return tier;
            }
        }
        return null;
    }

    /**
     * Gets tier from name
     * 
     * @param name
     * @return tier
     */
    public Tier getTier(String name)
    {
        for (Tier tier : plugin.tiers)
        {
            if (tier.getName().equalsIgnoreCase(name))
                return tier;
        }
        return null;
    }

    /**
     * Is type an actual tier?
     * 
     * @param type
     * @return is tier
     */
    public boolean matchesTier(String type)
    {
        for (Tier tier : plugin.tiers)
        {
            if (tier.getName().equalsIgnoreCase(type))
                return true;
        }
        return false;
    }

    /**
     * Gets a random name from prefix.txt and suffix.txt
     * 
     * @return name
     */
    public String name()
    {
        String prefix = plugin.prefix.get(plugin.gen.nextInt(plugin.prefix
                .size()));
        String suffix = plugin.suffix.get(plugin.gen.nextInt(plugin.suffix
                .size()));
        return prefix + " " + suffix;
    }
}
