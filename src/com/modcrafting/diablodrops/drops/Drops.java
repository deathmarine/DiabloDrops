package com.modcrafting.diablodrops.drops;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class Drops
{
	Random gen = new Random();

	public Material[] diamondArmor()
	{
		return new Material[]
		{
				Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE,
				Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS
		};
	}

	public Material[] ironArmor()
	{
		return new Material[]
		{
				Material.IRON_HELMET, Material.IRON_CHESTPLATE,
				Material.IRON_LEGGINGS, Material.IRON_BOOTS
		};
	}

	public Material[] chainmailArmor()
	{
		return new Material[]
		{
				Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE,
				Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS
		};
	}

	public Material[] goldArmor()
	{
		return new Material[]
		{
				Material.GOLD_HELMET, Material.GOLD_CHESTPLATE,
				Material.GOLD_LEGGINGS, Material.GOLD_BOOTS
		};
	}

	public Material[] leatherArmor()
	{
		return new Material[]
		{
				Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE,
				Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS
		};
	}

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

	public Material weaponPicker()
	{
		switch (gen.nextInt(5))
		{
		case 0:
			return diamondWeapon(gen.nextInt(5));
		case 1:
			return ironWeapon(gen.nextInt(5));
		case 2:
			return goldWeapon(gen.nextInt(5));
		case 3:
			return woodWeapon(gen.nextInt(5));
		case 4:
			return Material.BOW;
		default:
			return null;
		}
	}

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

	public boolean isArmor(Material mat)
	{
		if (isHelmet(mat) || isBoots(mat) || isChestPlate(mat)
				|| isLeggings(mat))
		{
			return true;
		}
		return false;
	}

	public boolean isSword(Material mat)
	{
		if (mat.equals(Material.WOOD_SWORD) || mat.equals(Material.STONE_SWORD)
				|| mat.equals(Material.GOLD_SWORD)
				|| mat.equals(Material.IRON_SWORD)
				|| mat.equals(Material.DIAMOND_SWORD))
			return true;
		return false;
	}

	public boolean isAxe(Material mat)
	{
		if (mat.equals(Material.WOOD_AXE) || mat.equals(Material.STONE_AXE)
				|| mat.equals(Material.GOLD_AXE)
				|| mat.equals(Material.IRON_AXE)
				|| mat.equals(Material.DIAMOND_AXE))
			return true;
		return false;
	}

	public boolean isSpade(Material mat)
	{
		if (mat.equals(Material.WOOD_SPADE) || mat.equals(Material.STONE_SPADE)
				|| mat.equals(Material.GOLD_SPADE)
				|| mat.equals(Material.IRON_SPADE)
				|| mat.equals(Material.DIAMOND_SPADE))
			return true;
		return false;
	}

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

	public boolean isHoe(Material mat)
	{
		if (mat.equals(Material.WOOD_HOE) || mat.equals(Material.STONE_HOE)
				|| mat.equals(Material.GOLD_HOE)
				|| mat.equals(Material.IRON_HOE)
				|| mat.equals(Material.DIAMOND_HOE))
			return true;
		return false;
	}

	
	public boolean isTool(Material mat)
	{
		if (isSword(mat) || isSpade(mat) || isAxe(mat) || isPickaxe(mat)
				|| isHoe(mat)||mat.equals(Material.BOW))
			return true;
		return false;

	}
	
	public Material[] allItems()
	{
		return new Material[]{
			Material.DIAMOND_SWORD,
			Material.DIAMOND_PICKAXE,
			Material.DIAMOND_SPADE, 
			Material.DIAMOND_AXE,
			Material.DIAMOND_HOE,
			Material.IRON_SWORD,
			Material.IRON_PICKAXE,
			Material.IRON_SPADE,
			Material.IRON_AXE,
			Material.IRON_HOE,
			Material.GOLD_SWORD,
			Material.GOLD_PICKAXE,
			Material.GOLD_SPADE,
			Material.GOLD_AXE,
			Material.GOLD_HOE,
			Material.WOOD_SWORD,
			Material.WOOD_PICKAXE,
			Material.WOOD_SPADE,
			Material.WOOD_AXE,
			Material.WOOD_HOE,
			Material.DIAMOND_HELMET, 
			Material.DIAMOND_CHESTPLATE,	
			Material.DIAMOND_LEGGINGS, 
			Material.DIAMOND_BOOTS,
			Material.IRON_HELMET,
			Material.IRON_CHESTPLATE,
			Material.IRON_LEGGINGS, 
			Material.IRON_BOOTS,
			Material.CHAINMAIL_HELMET, 
			Material.CHAINMAIL_CHESTPLATE,
			Material.CHAINMAIL_LEGGINGS, 
			Material.CHAINMAIL_BOOTS,
			Material.GOLD_HELMET, 
			Material.GOLD_CHESTPLATE,
			Material.GOLD_LEGGINGS, 
			Material.GOLD_BOOTS,
			Material.LEATHER_LEGGINGS, 
			Material.LEATHER_BOOTS,
			Material.BOW
			};
	}
		
	
}
