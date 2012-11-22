package com.modcrafting.diablodrops.drops;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class Drops
{
	Random gen = new Random();

	/**
	 * Returns an array of Materials that the plugin can use
	 * 
	 * @return all items that the plugin can use
	 */
	public Material[] allItems()
	{
		return new Material[]
		{
				Material.DIAMOND_SWORD, Material.DIAMOND_PICKAXE,
				Material.DIAMOND_SPADE, Material.DIAMOND_AXE,
				Material.DIAMOND_HOE, Material.IRON_SWORD,
				Material.IRON_PICKAXE, Material.IRON_SPADE, Material.IRON_AXE,
				Material.IRON_HOE, Material.GOLD_SWORD, Material.GOLD_PICKAXE,
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
				Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS, Material.BOW
		};
	}

	/**
	 * Returns the helmet, chestplate, leggings, and boots of a random material
	 * type
	 * 
	 * @return the helmet, chestplate, leggings, and boots of a random material
	 */
	public Material[] armorPicker()
	{
		switch (gen.nextInt(4))
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
		return new Material[]
		{
				Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE,
				Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS
		};
	}

	/**
	 * Returns the helmet, chestplate, leggings, and boots
	 * 
	 * @return helmet, chestplate, leggings, and boots
	 */
	public Material[] diamondArmor()
	{
		return new Material[]
		{
				Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE,
				Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS
		};
	}

	/**
	 * Returns a kind of diamond tool
	 * 1 - DIAMOND_SWORD
	 * 2 - DIAMOND_PICKAXE
	 * 3 - DIAMOND_SPADE
	 * 4 - DIAMOND_AXE
	 * 5 - DIAMOND_HOE
	 * 
	 * @param kind
	 *            to return
	 * @return kind of diamond tool
	 */
	public Material diamondWeapon(int num)
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
	 * Returns a random enchantment
	 * 
	 * @return random enchantment
	 */
	public Enchantment enchant()
	{
		switch (gen.nextInt(22))
		{
			case 1:
				return Enchantment.DAMAGE_ALL; // Weapons Only
			case 2:
				return Enchantment.DAMAGE_ARTHROPODS; // Weapons Only
			case 3:
				return Enchantment.DAMAGE_UNDEAD; // Weapons Only
			case 4:
				return Enchantment.DIG_SPEED; // Tools except Hoe.
			case 5:
				return Enchantment.DURABILITY; // Tools except Hoe.
			case 6:
				return Enchantment.FIRE_ASPECT; // Weapons Only
			case 7:
				return Enchantment.KNOCKBACK; // Weapons Only
			case 8:
				return Enchantment.LOOT_BONUS_BLOCKS; // Tools except Hoe.
			case 9:
				return Enchantment.LOOT_BONUS_MOBS; // Weapons Only
			case 10:
				return Enchantment.OXYGEN; // Armor Helmet Only
			case 11:
				return Enchantment.PROTECTION_ENVIRONMENTAL; // All Armor
			case 12:
				return Enchantment.PROTECTION_EXPLOSIONS; // All Armor
			case 13:
				return Enchantment.PROTECTION_FALL; // All Armor
			case 14:
				return Enchantment.PROTECTION_FIRE; // All Armor
			case 15:
				return Enchantment.PROTECTION_PROJECTILE; // All Armor
			case 16:
				return Enchantment.SILK_TOUCH; // Tools except Hoe
			case 17:
				return Enchantment.WATER_WORKER; // Armor Helmet Only
			case 18:
				return Enchantment.ARROW_DAMAGE;
			case 19:
				return Enchantment.ARROW_FIRE;
			case 20:
				return Enchantment.ARROW_KNOCKBACK;
			case 21:
				return Enchantment.ARROW_INFINITE;
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
		switch (gen.nextInt(6))
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
		switch (gen.nextInt(6))
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
		switch (gen.nextInt(6))
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
	 * Gets random kind of leggings
	 * 
	 * @return random kind of leggings
	 */
	public Material getLeggings()
	{
		switch (gen.nextInt(6))
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
	 * Gets random kind of sword
	 * 
	 * @return random kind of sword
	 */
	public Material getSword()
	{
		switch (gen.nextInt(6))
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
	 * Gets random kind of pickaxe
	 * 
	 * @return random kind of pickaxe
	 */
	public Material getPickaxe()
	{
		switch (gen.nextInt(6))
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
	 * Gets random type of axe
	 * 
	 * @return random type of axe
	 */
	public Material getAxe()
	{
		switch (gen.nextInt(6))
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
	 * Gets random kind of spade
	 * 
	 * @return random kind of spade
	 */
	public Material getSpade()
	{
		switch (gen.nextInt(6))
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
	 * Gets random kind of hoe
	 * 
	 * @return random kind of hoe
	 */
	public Material getHoe()
	{
		switch (gen.nextInt(6))
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
	 * Returns the helmet, chestplate, leggings, and boots
	 * 
	 * @return helmet, chestplate, leggings, and boots
	 */
	public Material[] goldArmor()
	{
		return new Material[]
		{
				Material.GOLD_HELMET, Material.GOLD_CHESTPLATE,
				Material.GOLD_LEGGINGS, Material.GOLD_BOOTS
		};
	}

	/**
	 * Returns a kind of gold tool
	 * 1 - GOLD_SWORD
	 * 2 - GOLD_PICKAXE
	 * 3 - GOLD_SPADE
	 * 4 - GOLD_AXE
	 * 5 - GOLD_HOE
	 * 
	 * @param kind
	 *            to return
	 * @return kind of gold tool
	 */
	public Material goldWeapon(int num)
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
		return new Material[]
		{
				Material.IRON_HELMET, Material.IRON_CHESTPLATE,
				Material.IRON_LEGGINGS, Material.IRON_BOOTS
		};
	}

	/**
	 * Returns a kind of iron tool
	 * 1 - IRON_SWORD
	 * 2 - IRON_PICKAXE
	 * 3 - IRON_SPADE
	 * 4 - IRON_AXE
	 * 5 - IRON_HOE
	 * 
	 * @param kind
	 *            to return
	 * @return kind of iron tool
	 */
	public Material ironWeapon(int num)
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
	 * @param material
	 *            to check
	 * @return is armor or not
	 */
	public boolean isArmor(Material mat)
	{
		if (isHelmet(mat) || isBoots(mat) || isChestPlate(mat)
				|| isLeggings(mat))
		{
			return true;
		}
		return false;
	}

	/**
	 * Is material an axe?
	 * 
	 * @param material
	 *            to check
	 * @return is axe or not
	 */
	public boolean isAxe(Material mat)
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
	 * @param material
	 *            to check
	 * @return is boots or not
	 */
	public boolean isBoots(Material mat)
	{
		if (mat.equals(Material.LEATHER_BOOTS)
				|| mat.equals(Material.GOLD_BOOTS)
				|| mat.equals(Material.CHAINMAIL_BOOTS)
				|| mat.equals(Material.IRON_BOOTS)
				|| mat.equals(Material.DIAMOND_BOOTS))
		{
			return true;
		}
		return false;
	}

	/**
	 * Is material a chestplate?
	 * 
	 * @param material
	 *            to check
	 * @return is chestplate or not
	 */
	public boolean isChestPlate(Material mat)
	{
		if (mat.equals(Material.LEATHER_CHESTPLATE)
				|| mat.equals(Material.GOLD_CHESTPLATE)
				|| mat.equals(Material.CHAINMAIL_CHESTPLATE)
				|| mat.equals(Material.IRON_CHESTPLATE)
				|| mat.equals(Material.DIAMOND_CHESTPLATE))
		{
			return true;
		}
		return false;
	}

	/**
	 * Is material a helmet?
	 * 
	 * @param material
	 *            to check
	 * @return is helmet or not
	 */
	public boolean isHelmet(Material mat)
	{
		if (mat.equals(Material.LEATHER_HELMET)
				|| mat.equals(Material.GOLD_HELMET)
				|| mat.equals(Material.CHAINMAIL_HELMET)
				|| mat.equals(Material.IRON_HELMET)
				|| mat.equals(Material.DIAMOND_HELMET))
		{
			return true;
		}
		return false;
	}

	/**
	 * Is material a hoe?
	 * 
	 * @param material
	 *            to check
	 * @return is hoe or not
	 */
	public boolean isHoe(Material mat)
	{
		if (mat.equals(Material.WOOD_HOE) || mat.equals(Material.STONE_HOE)
				|| mat.equals(Material.GOLD_HOE)
				|| mat.equals(Material.IRON_HOE)
				|| mat.equals(Material.DIAMOND_HOE))
			return true;
		return false;
	}

	/**
	 * Is material leggings?
	 * 
	 * @param material
	 *            to check
	 * @return is leggings or not
	 */
	public boolean isLeggings(Material mat)
	{
		if (mat.equals(Material.LEATHER_LEGGINGS)
				|| mat.equals(Material.GOLD_LEGGINGS)
				|| mat.equals(Material.CHAINMAIL_LEGGINGS)
				|| mat.equals(Material.IRON_LEGGINGS)
				|| mat.equals(Material.DIAMOND_LEGGINGS))
		{
			return true;
		}
		return false;
	}

	/**
	 * Is material a pickaxe?
	 * 
	 * @param material
	 *            to check
	 * @return is pickaxe or not
	 */
	public boolean isPickaxe(Material mat)
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
	 * @param material
	 *            to check
	 * @return is spade or not
	 */
	public boolean isSpade(Material mat)
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
	 * @param material
	 *            to check
	 * @return is sword or not
	 */
	public boolean isSword(Material mat)
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
	 * @param material
	 *            to check
	 * @return is tool or not
	 */
	public boolean isTool(Material mat)
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
		return new Material[]
		{
				Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE,
				Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS
		};
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
	 * Returns a kind of stone tool
	 * 1 - STONE_SWORD
	 * 2 - STONE_PICKAXE
	 * 3 - STONE_SPADE
	 * 4 - STONE_AXE
	 * 5 - STONE_HOE
	 * 
	 * @param kind
	 *            to return
	 * @return kind of stone tool
	 */
	public Material stoneWeapon(int num)
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
	 * Returns a kind of wood tool
	 * 1 - WOOD_SWORD
	 * 2 - WOOD_PICKAXE
	 * 3 - WOOD_SPADE
	 * 4 - WOOD_AXE
	 * 5 - WOOD_HOE
	 * 
	 * @param kind
	 *            to return
	 * @return kind of wood tool
	 */
	public Material woodWeapon(int num)
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
