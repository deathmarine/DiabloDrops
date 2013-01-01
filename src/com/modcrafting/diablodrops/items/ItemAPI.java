package com.modcrafting.diablodrops.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemAPI
{
    public Random gen = new Random();

    /**
     * Adds lore to the ItemStack
     * 
     * @param itemStack
     *            ItemStack to add lore to
     * @param lore
     *            Lore to add to ItemStack
     * @return lored ItemStack
     */
    public ItemStack addLore(final ItemStack itemStack, final String lore)
    {
        ItemStack ret = itemStack;
        List<String> loreList = getLore(ret);
        loreList.add(lore);
        return setLore(ret, loreList);
    }

    /**
     * Returns an array of Materials that the plugin can use
     * 
     * @return all items that the plugin can use
     */
    public Material[] allItems()
    {
        return new Material[] { Material.DIAMOND_SWORD,
                Material.DIAMOND_PICKAXE, Material.DIAMOND_SPADE,
                Material.DIAMOND_AXE, Material.DIAMOND_HOE,
                Material.IRON_SWORD, Material.IRON_PICKAXE,
                Material.IRON_SPADE, Material.IRON_AXE, Material.IRON_HOE,
                Material.GOLD_SWORD, Material.GOLD_PICKAXE,
                Material.GOLD_SPADE, Material.GOLD_AXE, Material.GOLD_HOE,
                Material.STONE_SWORD, Material.STONE_PICKAXE,
                Material.STONE_SPADE, Material.STONE_AXE, Material.STONE_HOE,
                Material.WOOD_SWORD, Material.WOOD_PICKAXE,
                Material.WOOD_SPADE, Material.WOOD_AXE, Material.WOOD_HOE,
                Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE,
                Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS,
                Material.IRON_HELMET, Material.IRON_CHESTPLATE,
                Material.IRON_LEGGINGS, Material.IRON_BOOTS,
                Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE,
                Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS,
                Material.GOLD_HELMET, Material.GOLD_CHESTPLATE,
                Material.GOLD_LEGGINGS, Material.GOLD_BOOTS,
                Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE,
                Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS, Material.BOW };
    }

    /**
     * Returns the helmet, chestplate, leggings, and boots of a random material
     * type
     * 
     * @return the helmet, chestplate, leggings, and boots of a random material
     */
    public Material[] armorPicker()
    {
        switch (gen.nextInt(5))
        {
            case 0:
                return diamondArmor();
            case 1:
                return ironArmor();
            case 2:
                return chainmailArmor();
            case 3:
                return goldArmor();
            case 4:
                return leatherArmor();
            default:
                return null;
        }
    }

    /**
     * Returns the helmet, chestplate, leggings, and boots
     * 
     * @return helmet, chestplate, leggings, and boots
     */
    public Material[] chainmailArmor()
    {
        return new Material[] { Material.CHAINMAIL_HELMET,
                Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS,
                Material.CHAINMAIL_BOOTS };
    }

    /**
     * Returns the helmet, chestplate, leggings, and boots
     * 
     * @return helmet, chestplate, leggings, and boots
     */
    public Material[] diamondArmor()
    {
        return new Material[] { Material.DIAMOND_HELMET,
                Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS,
                Material.DIAMOND_BOOTS };
    }

    /**
     * Returns a kind of diamond tool 1 - DIAMOND_SWORD 2 - DIAMOND_PICKAXE 3 -
     * DIAMOND_SPADE 4 - DIAMOND_AXE 5 - DIAMOND_HOE
     * 
     * @param num
     *            kind to return
     * @return kind of diamond tool
     */
    public Material diamondWeapon(final int num)
    {
        switch (num)
        {
            case 1:
                return Material.DIAMOND_SWORD;
            case 2:
                return Material.DIAMOND_PICKAXE;
            case 3:
                return Material.DIAMOND_SPADE;
            case 4:
                return Material.DIAMOND_AXE;
            case 5:
                return Material.DIAMOND_HOE;
            default:
                return null;
        }
    }

    /**
     * Returns a Material that was randomly picked
     * 
     * @return random material
     */
    public Material dropPicker()
    {
        int next = gen.nextInt(10);
        switch (next)
        {
            case 1:
                return getHelmet();
            case 2:
                return getChestPlate();
            case 3:
                return getLeggings();
            case 4:
                return getBoots();
            case 5:
                return getHoe();
            case 6:
                return getPickaxe();
            case 7:
                return getAxe();
            case 8:
                return getSpade();
            case 9:
                return Material.BOW;
            default:
                return getSword();

        }
    }

    /**
     * Gets random type of axe
     * 
     * @return random type of axe
     */
    public Material getAxe()
    {
        switch (gen.nextInt(5))
        {
            case 0:
                return Material.WOOD_AXE;
            case 1:
                return Material.STONE_AXE;
            case 2:
                return Material.GOLD_AXE;
            case 3:
                return Material.IRON_AXE;
            case 4:
                return Material.DIAMOND_AXE;
            default:
                return null;
        }
    }

    /**
     * Gets a random kind of boot
     * 
     * @return random kind of boot
     */
    public Material getBoots()
    {
        switch (gen.nextInt(5))
        {
            case 0:
                return Material.LEATHER_BOOTS;
            case 1:
                return Material.GOLD_BOOTS;
            case 2:
                return Material.CHAINMAIL_BOOTS;
            case 3:
                return Material.IRON_BOOTS;
            case 4:
                return Material.DIAMOND_BOOTS;
            default:
                return null;
        }
    }

    /**
     * Gets random kind of chestplate
     * 
     * @return random kind of chestplate
     */
    public Material getChestPlate()
    {
        switch (gen.nextInt(5))
        {
            case 0:
                return Material.LEATHER_CHESTPLATE;
            case 1:
                return Material.GOLD_CHESTPLATE;
            case 2:
                return Material.CHAINMAIL_CHESTPLATE;
            case 3:
                return Material.IRON_CHESTPLATE;
            case 4:
                return Material.DIAMOND_CHESTPLATE;
            default:
                return null;
        }
    }

    /**
     * Gets random kind of helmet
     * 
     * @return random kind of helmet
     */
    public Material getHelmet()
    {
        switch (gen.nextInt(5))
        {
            case 0:
                return Material.LEATHER_HELMET;
            case 1:
                return Material.GOLD_HELMET;
            case 2:
                return Material.CHAINMAIL_HELMET;
            case 3:
                return Material.IRON_HELMET;
            case 4:
                return Material.DIAMOND_HELMET;
            default:
                return null;
        }
    }

    /**
     * Gets random kind of hoe
     * 
     * @return random kind of hoe
     */
    public Material getHoe()
    {
        switch (gen.nextInt(5))
        {
            case 0:
                return Material.WOOD_HOE;
            case 1:
                return Material.STONE_HOE;
            case 2:
                return Material.GOLD_HOE;
            case 3:
                return Material.IRON_HOE;
            case 4:
                return Material.DIAMOND_HOE;
            default:
                return null;
        }
    }

    /**
     * Gets random kind of leggings
     * 
     * @return random kind of leggings
     */
    public Material getLeggings()
    {
        switch (gen.nextInt(5))
        {
            case 0:
                return Material.LEATHER_LEGGINGS;
            case 1:
                return Material.GOLD_LEGGINGS;
            case 2:
                return Material.CHAINMAIL_LEGGINGS;
            case 3:
                return Material.IRON_LEGGINGS;
            case 4:
                return Material.DIAMOND_LEGGINGS;
            default:
                return null;
        }
    }

    /**
     * Gets the lore of an ItemStack
     * 
     * @param itemStack
     *            ItemStack to get the lore of
     * @return the lore of an ItemStack
     */
    public List<String> getLore(final ItemStack itemStack)
    {
        ItemStack ret = itemStack;
        ItemMeta itemMeta;
        if (ret.hasItemMeta())
        {
            itemMeta = ret.getItemMeta();
        }
        else
        {
            itemMeta = Bukkit.getItemFactory().getItemMeta(ret.getType());
        }
        if (itemMeta.hasLore())
            return itemMeta.getLore();
        return new ArrayList<String>();
    }

    /**
     * Gets the name of an ItemStack
     * 
     * @param itemStack
     *            ItemStack to get the lore of
     * @return the lore of an ItemStack
     */
    public String getName(final ItemStack itemStack)
    {
        ItemStack ret = itemStack;
        ItemMeta itemMeta;
        if (ret.hasItemMeta())
        {
            itemMeta = ret.getItemMeta();
        }
        else
        {
            itemMeta = Bukkit.getItemFactory().getItemMeta(ret.getType());
        }
        if (itemMeta.hasDisplayName())
            return itemMeta.getDisplayName();
        String unfName = ret.getType().name();
        String[] split = unfName.split("_");
        String fName = new String();
        for (String s : split)
        {
            String firstLetter = s.substring(0, 1);
            String restOfWord = s.substring(1, s.length());
            String newName = firstLetter.toUpperCase()
                    + restOfWord.toLowerCase();
            fName = fName + newName + " ";
        }
        return fName;
    }

    /**
     * Gets random kind of pickaxe
     * 
     * @return random kind of pickaxe
     */
    public Material getPickaxe()
    {
        switch (gen.nextInt(5))
        {
            case 0:
                return Material.WOOD_PICKAXE;
            case 1:
                return Material.STONE_PICKAXE;
            case 2:
                return Material.GOLD_PICKAXE;
            case 3:
                return Material.IRON_PICKAXE;
            case 4:
                return Material.DIAMOND_PICKAXE;
            default:
                return null;
        }
    }

    /**
     * Gets random kind of spade
     * 
     * @return random kind of spade
     */
    public Material getSpade()
    {
        switch (gen.nextInt(5))
        {
            case 0:
                return Material.WOOD_SPADE;
            case 1:
                return Material.STONE_SPADE;
            case 2:
                return Material.GOLD_SPADE;
            case 3:
                return Material.IRON_SPADE;
            case 4:
                return Material.DIAMOND_SPADE;
            default:
                return null;
        }
    }

    /**
     * Gets random kind of sword
     * 
     * @return random kind of sword
     */
    public Material getSword()
    {
        switch (gen.nextInt(5))
        {
            case 0:
                return Material.WOOD_SWORD;
            case 1:
                return Material.STONE_SWORD;
            case 2:
                return Material.GOLD_SWORD;
            case 3:
                return Material.IRON_SWORD;
            case 4:
                return Material.DIAMOND_SWORD;
            default:
                return null;
        }
    }

    /**
     * Returns the helmet, chestplate, leggings, and boots
     * 
     * @return helmet, chestplate, leggings, and boots
     */
    public Material[] goldArmor()
    {
        return new Material[] { Material.GOLD_HELMET, Material.GOLD_CHESTPLATE,
                Material.GOLD_LEGGINGS, Material.GOLD_BOOTS };
    }

    /**
     * Returns a kind of gold tool 1 - GOLD_SWORD 2 - GOLD_PICKAXE 3 -
     * GOLD_SPADE 4 - GOLD_AXE 5 - GOLD_HOE
     * 
     * @param num
     *            kind to return
     * @return kind of gold tool
     */
    public Material goldWeapon(final int num)
    {
        switch (num)
        {
            case 1:
                return Material.GOLD_SWORD;
            case 2:
                return Material.GOLD_PICKAXE;
            case 3:
                return Material.GOLD_SPADE;
            case 4:
                return Material.GOLD_AXE;
            case 5:
                return Material.GOLD_HOE;
            default:
                return null;
        }
    }

    /**
     * Returns the helmet, chestplate, leggings, and boots
     * 
     * @return helmet, chestplate, leggings, and boots
     */
    public Material[] ironArmor()
    {
        return new Material[] { Material.IRON_HELMET, Material.IRON_CHESTPLATE,
                Material.IRON_LEGGINGS, Material.IRON_BOOTS };
    }

    /**
     * Returns a kind of iron tool 1 - IRON_SWORD 2 - IRON_PICKAXE 3 -
     * IRON_SPADE 4 - IRON_AXE 5 - IRON_HOE
     * 
     * @param num
     *            kind to return
     * @return kind of iron tool
     */
    public Material ironWeapon(final int num)
    {
        switch (num)
        {
            case 1:
                return Material.IRON_SWORD;
            case 2:
                return Material.IRON_PICKAXE;
            case 3:
                return Material.IRON_SPADE;
            case 4:
                return Material.IRON_AXE;
            case 5:
                return Material.IRON_HOE;
            default:
                return null;
        }
    }

    /**
     * Is material a piece of armor?
     * 
     * @param mat
     *            material to check
     * @return is armor or not
     */
    public boolean isArmor(final Material mat)
    {
        if (isHelmet(mat) || isBoots(mat) || isChestPlate(mat)
                || isLeggings(mat))
            return true;
        return false;
    }

    /**
     * Is material an axe?
     * 
     * @param mat
     *            material to check
     * @return is axe or not
     */
    public boolean isAxe(final Material mat)
    {
        if (mat.equals(Material.WOOD_AXE) || mat.equals(Material.STONE_AXE)
                || mat.equals(Material.GOLD_AXE)
                || mat.equals(Material.IRON_AXE)
                || mat.equals(Material.DIAMOND_AXE))
            return true;
        return false;
    }

    /**
     * Is material boots?
     * 
     * @param mat
     *            material to check
     * @return is boots or not
     */
    public boolean isBoots(final Material mat)
    {
        if (mat.equals(Material.LEATHER_BOOTS)
                || mat.equals(Material.GOLD_BOOTS)
                || mat.equals(Material.CHAINMAIL_BOOTS)
                || mat.equals(Material.IRON_BOOTS)
                || mat.equals(Material.DIAMOND_BOOTS))
            return true;
        return false;
    }

    /**
     * Is material a chestplate?
     * 
     * @param mat
     *            material to check
     * @return is chestplate or not
     */
    public boolean isChestPlate(final Material mat)
    {
        if (mat.equals(Material.LEATHER_CHESTPLATE)
                || mat.equals(Material.GOLD_CHESTPLATE)
                || mat.equals(Material.CHAINMAIL_CHESTPLATE)
                || mat.equals(Material.IRON_CHESTPLATE)
                || mat.equals(Material.DIAMOND_CHESTPLATE))
            return true;
        return false;
    }

    /**
     * Is material a helmet?
     * 
     * @param mat
     *            material to check
     * @return is helmet or not
     */
    public boolean isHelmet(final Material mat)
    {
        if (mat.equals(Material.LEATHER_HELMET)
                || mat.equals(Material.GOLD_HELMET)
                || mat.equals(Material.CHAINMAIL_HELMET)
                || mat.equals(Material.IRON_HELMET)
                || mat.equals(Material.DIAMOND_HELMET))
            return true;
        return false;
    }

    /**
     * Is material a hoe?
     * 
     * @param mat
     *            material to check
     * @return is hoe or not
     */
    public boolean isHoe(final Material mat)
    {
        if (mat.equals(Material.WOOD_HOE) || mat.equals(Material.STONE_HOE)
                || mat.equals(Material.GOLD_HOE)
                || mat.equals(Material.IRON_HOE)
                || mat.equals(Material.DIAMOND_HOE))
            return true;
        return false;
    }

    public boolean isLeather(final Material mat)
    {
        if (mat.equals(Material.LEATHER_HELMET)
                || mat.equals(Material.LEATHER_CHESTPLATE)
                || mat.equals(Material.LEATHER_LEGGINGS)
                || mat.equals(Material.LEATHER_BOOTS))
            return true;
        return false;
    }

    /**
     * Is material leggings?
     * 
     * @param mat
     *            material to check
     * @return is leggings or not
     */
    public boolean isLeggings(final Material mat)
    {
        if (mat.equals(Material.LEATHER_LEGGINGS)
                || mat.equals(Material.GOLD_LEGGINGS)
                || mat.equals(Material.CHAINMAIL_LEGGINGS)
                || mat.equals(Material.IRON_LEGGINGS)
                || mat.equals(Material.DIAMOND_LEGGINGS))
            return true;
        return false;
    }

    /**
     * Is material a pickaxe?
     * 
     * @param mat
     *            material to check
     * @return is pickaxe or not
     */
    public boolean isPickaxe(final Material mat)
    {
        if (mat.equals(Material.WOOD_PICKAXE)
                || mat.equals(Material.STONE_PICKAXE)
                || mat.equals(Material.GOLD_PICKAXE)
                || mat.equals(Material.IRON_PICKAXE)
                || mat.equals(Material.DIAMOND_PICKAXE))
            return true;
        return false;
    }

    /**
     * Is material a spade?
     * 
     * @param mat
     *            material to check
     * @return is spade or not
     */
    public boolean isSpade(final Material mat)
    {
        if (mat.equals(Material.WOOD_SPADE) || mat.equals(Material.STONE_SPADE)
                || mat.equals(Material.GOLD_SPADE)
                || mat.equals(Material.IRON_SPADE)
                || mat.equals(Material.DIAMOND_SPADE))
            return true;
        return false;
    }

    /**
     * Is material a sword?
     * 
     * @param mat
     *            material to check
     * @return is sword or not
     */
    public boolean isSword(final Material mat)
    {
        if (mat.equals(Material.WOOD_SWORD) || mat.equals(Material.STONE_SWORD)
                || mat.equals(Material.GOLD_SWORD)
                || mat.equals(Material.IRON_SWORD)
                || mat.equals(Material.DIAMOND_SWORD))
            return true;
        return false;
    }

    /**
     * Is material an tool?
     * 
     * @param mat
     *            material to check
     * @return is tool or not
     */
    public boolean isTool(final Material mat)
    {
        if (isSword(mat) || isSpade(mat) || isAxe(mat) || isPickaxe(mat)
                || isHoe(mat) || mat.equals(Material.BOW))
            return true;
        return false;
    }

    /**
     * Returns the helmet, chestplate, leggings, and boots
     * 
     * @return helmet, chestplate, leggings, and boots
     */
    public Material[] leatherArmor()
    {
        return new Material[] { Material.LEATHER_HELMET,
                Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS,
                Material.LEATHER_BOOTS };
    }

    /**
     * Sets the lore of an ItemStack
     * 
     * @param itemStack
     *            ItemStack to set lore for
     * @param lore
     *            Lore to give to the ItemStack
     * @return lored ItemStack
     */
    public ItemStack setLore(final ItemStack itemStack, final List<String> lore)
    {
        ItemStack ret = itemStack;
        ItemMeta itemMeta;
        if (ret.hasItemMeta())
        {
            itemMeta = ret.getItemMeta();
        }
        else
        {
            itemMeta = Bukkit.getItemFactory().getItemMeta(ret.getType());
        }
        itemMeta.setLore(lore);
        ret.setItemMeta(itemMeta);
        return ret;
    }

    /**
     * Sets the lore of an ItemStack
     * 
     * @param itemStack
     *            ItemStack to set lore for
     * @param lore
     *            Lore to give to the ItemStack
     * @return lored ItemStack
     */
    public ItemStack setLore(final ItemStack itemStack, final String... lore)
    {
        ItemStack ret = itemStack;
        ItemMeta itemMeta;
        if (ret.hasItemMeta())
        {
            itemMeta = ret.getItemMeta();
        }
        else
        {
            itemMeta = Bukkit.getItemFactory().getItemMeta(ret.getType());
        }
        itemMeta.setLore(Arrays.asList(lore));
        ret.setItemMeta(itemMeta);
        return ret;
    }

    /**
     * Sets the name of an ItemStack
     * 
     * @param itemStack
     *            ItemStack to set name for
     * @param name
     *            Name to give to the ItemStack
     * @return named ItemStack
     */
    public ItemStack setName(final ItemStack itemStack, final String name)
    {
        ItemStack ret = itemStack;
        ItemMeta itemMeta;
        if (ret.hasItemMeta())
        {
            itemMeta = ret.getItemMeta();
        }
        else
        {
            itemMeta = Bukkit.getItemFactory().getItemMeta(ret.getType());
        }
        itemMeta.setDisplayName(name);
        ret.setItemMeta(itemMeta);
        return ret;
    }

    /**
     * Returns a kind of stone tool 1 - STONE_SWORD 2 - STONE_PICKAXE 3 -
     * STONE_SPADE 4 - STONE_AXE 5 - STONE_HOE
     * 
     * @param num
     *            kind to return
     * @return kind of stone tool
     */
    public Material stoneWeapon(final int num)
    {
        switch (num)
        {
            case 1:
                return Material.STONE_SWORD;
            case 2:
                return Material.STONE_PICKAXE;
            case 3:
                return Material.STONE_SPADE;
            case 4:
                return Material.STONE_AXE;
            case 5:
                return Material.STONE_HOE;
            default:
                return null;
        }
    }

    /**
     * Returns a Material that was randomly picked
     * 
     * @return random material
     */
    public Material toolPicker()
    {
        int next = gen.nextInt(10);
        switch (next)
        {
            case 1:
                return getHelmet();
            case 2:
                return getChestPlate();
            case 3:
                return getLeggings();
            case 4:
                return getBoots();
            case 5:
                return getHoe();
            case 6:
                return getPickaxe();
            case 7:
                return getAxe();
            case 8:
                return getSpade();
            case 9:
                return Material.BOW;
            default:
                return getSword();

        }
    }

    /**
     * Get a random weapon
     * 
     * @return a random weapon
     */
    public Material weaponPicker()
    {
        switch (gen.nextInt(6))
        {
            case 0:
                return diamondWeapon(gen.nextInt(5));
            case 1:
                return ironWeapon(gen.nextInt(5));
            case 2:
                return goldWeapon(gen.nextInt(5));
            case 3:
                return stoneWeapon(gen.nextInt(5));
            case 4:
                return woodWeapon(gen.nextInt(5));
            case 5:
                return Material.BOW;
            default:
                return null;
        }
    }

    /**
     * Returns a kind of wood tool 1 - WOOD_SWORD 2 - WOOD_PICKAXE 3 -
     * WOOD_SPADE 4 - WOOD_AXE 5 - WOOD_HOE
     * 
     * @param num
     *            kind to return
     * @return kind of wood tool
     */
    public Material woodWeapon(final int num)
    {
        switch (num)
        {
            case 1:
                return Material.WOOD_SWORD;
            case 2:
                return Material.WOOD_PICKAXE;
            case 3:
                return Material.WOOD_SPADE;
            case 4:
                return Material.WOOD_AXE;
            case 5:
                return Material.WOOD_HOE;
            default:
                return null;
        }
    }
}
