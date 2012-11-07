package com.modcrafting.diablodrops.drops;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.tier.Legendary;
import com.modcrafting.diablodrops.tier.Lore;
import com.modcrafting.diablodrops.tier.Magical;
import com.modcrafting.diablodrops.tier.Rare;
import com.modcrafting.diablodrops.tier.Set;
import com.modcrafting.diablodrops.tier.Tome;
import com.modcrafting.diablodrops.tier.Unidentified;

public class DropsAPI
{

	private Random gen = new Random();
	private DropType[] types = DropType.values();
	private Drops drops = new Drops();
	private DiabloDrops plugin;

	public DropsAPI(DiabloDrops instance)
	{
		plugin = instance;
	}

	/**
	 * Returns a specific type of item randomly generated
	 * 
	 * @param material
	 *            Material of itemstack
	 * @return CraftItemStack
	 */
	public CraftItemStack getItem(Material material)
	{
		Material mat = Material.DIAMOND_SWORD;
		if (gen.nextBoolean())
		{
			Material[] mats = drops.armorPicker();
			if (mats != null)
				mat = mats[gen.nextInt(mats.length - 1)];
		}
		else
		{
			mat = drops.weaponPicker();
		}
		if (mat == null)
			return null;
		DropType type = types[gen.nextInt(types.length - 1)];
		if (type.equals(DropType.LEGENDARY))
		{
			int e = plugin.config.getInt("Legendary.Enchaments.Amt", 7);
			int l = plugin.config.getInt("Legendary.Enchaments.Levels", 10);
			CraftItemStack ci = new Legendary(mat, name());
			for (; e > 0; e--)
			{
				int lvl = plugin.gen.nextInt(l + 1);
				Enchantment ench = drops.enchant();
				if (lvl != 0 && ench != null)
					ci.addUnsafeEnchantment(ench, lvl);
			}
			return ci;
		}
		if (type.equals(DropType.LORE))
		{
			int e = plugin.config.getInt("Lore.Enchaments.Amt", 7);
			int l = plugin.config.getInt("Lore.Enchaments.Levels", 9);
			CraftItemStack ci = new Lore(mat, name());
			for (; e > 0; e--)
			{
				int lvl = plugin.gen.nextInt(l + 1);
				Enchantment ench = drops.enchant();
				if (lvl != 0 && ench != null)
					ci.addUnsafeEnchantment(ench, lvl);
			}
			return ci;
		}
		if (type.equals(DropType.MAGICAL))
		{
			int e = plugin.config.getInt("Magical.Enchaments.Amt", 3);
			int l = plugin.config.getInt("Magical.Enchaments.Levels", 4);
			CraftItemStack ci = new Magical(mat, name());
			for (; e > 0; e--)
			{
				int lvl = plugin.gen.nextInt(l + 1);
				Enchantment ench = drops.enchant();
				if (lvl != 0 && ench != null)
					ci.addUnsafeEnchantment(ench, lvl);
			}
			return ci;
		}
		if (type.equals(DropType.RARE))
		{
			int e = plugin.config.getInt("Rare.Enchaments.Amt", 5);
			int l = plugin.config.getInt("Rare.Enchaments.Levels", 5);
			CraftItemStack ci = new Rare(mat, name());
			for (; e > 0; e--)
			{
				int lvl = plugin.gen.nextInt(l + 1);
				Enchantment ench = drops.enchant();
				if (lvl != 0 && ench != null)
					ci.addUnsafeEnchantment(ench, lvl);
			}
			return ci;
		}
		if (type.equals(DropType.SET))
		{
			int e = plugin.config.getInt("Set.Enchaments.Amt", 7);
			int l = plugin.config.getInt("Set.Enchaments.Levels", 6);
			CraftItemStack ci = new Set(mat, name());
			for (; e > 0; e--)
			{
				int lvl = plugin.gen.nextInt(l + 1);
				Enchantment ench = drops.enchant();
				if (lvl != 0 && ench != null)
					ci.addUnsafeEnchantment(ench, lvl);
			}
			return ci;
		}
		if (type.equals(DropType.UNIDENTIFIED))
		{
			return new Unidentified(mat, name());
		}
		if (type.equals(DropType.TOME))
		{
			return new Tome();
		}
		return null;
	}

	/**
	 * Returns an specific typename of itemstack that was randomly generated
	 * 
	 * @return CraftItemStack
	 */
	public CraftItemStack getItem(String name)
	{
		return getItem(DropType.valueOf(name.toUpperCase()));
	}

	/**
	 * Returns an specific type of itemstack that was randomly generated
	 * 
	 * @return CraftItemStack
	 */
	public CraftItemStack getItem(DropType type)
	{
		Material mat = Material.DIAMOND_SWORD;
		if (gen.nextBoolean())
		{
			Material[] mats = drops.armorPicker();
			if (mats != null)
				mat = mats[gen.nextInt(mats.length - 1)];
		}
		else
		{
			mat = drops.weaponPicker();
		}
		if (mat == null)
			return null;
		if (type.equals(DropType.LEGENDARY))
		{
			int e = plugin.config.getInt("Legendary.Enchaments.Amt", 7);
			int l = plugin.config.getInt("Legendary.Enchaments.Levels", 10);
			CraftItemStack ci = new Legendary(mat, name());
			for (; e > 0; e--)
			{
				int lvl = plugin.gen.nextInt(l + 1);
				Enchantment ench = drops.enchant();
				if (lvl != 0 && ench != null)
					ci.addUnsafeEnchantment(ench, lvl);
			}
			return ci;
		}
		if (type.equals(DropType.LORE))
		{
			int e = plugin.config.getInt("Lore.Enchaments.Amt", 7);
			int l = plugin.config.getInt("Lore.Enchaments.Levels", 9);
			CraftItemStack ci = new Lore(mat, name());
			for (; e > 0; e--)
			{
				int lvl = plugin.gen.nextInt(l + 1);
				Enchantment ench = drops.enchant();
				if (lvl != 0 && ench != null)
					ci.addUnsafeEnchantment(ench, lvl);
			}
			return ci;
		}
		if (type.equals(DropType.MAGICAL))
		{
			int e = plugin.config.getInt("Magical.Enchaments.Amt", 3);
			int l = plugin.config.getInt("Magical.Enchaments.Levels", 4);
			CraftItemStack ci = new Magical(mat, name());
			for (; e > 0; e--)
			{
				int lvl = plugin.gen.nextInt(l + 1);
				Enchantment ench = drops.enchant();
				if (lvl != 0 && ench != null)
					ci.addUnsafeEnchantment(ench, lvl);
			}
			return ci;
		}
		if (type.equals(DropType.RARE))
		{
			int e = plugin.config.getInt("Rare.Enchaments.Amt", 5);
			int l = plugin.config.getInt("Rare.Enchaments.Levels", 5);
			CraftItemStack ci = new Rare(mat, name());
			for (; e > 0; e--)
			{
				int lvl = plugin.gen.nextInt(l + 1);
				Enchantment ench = drops.enchant();
				if (lvl != 0 && ench != null)
					ci.addUnsafeEnchantment(ench, lvl);
			}
			return ci;
		}
		if (type.equals(DropType.SET))
		{
			int e = plugin.config.getInt("Set.Enchaments.Amt", 7);
			int l = plugin.config.getInt("Set.Enchaments.Levels", 6);
			CraftItemStack ci = new Set(mat, name());
			for (; e > 0; e--)
			{
				int lvl = plugin.gen.nextInt(l + 1);
				Enchantment ench = drops.enchant();
				if (lvl != 0 && ench != null)
					ci.addUnsafeEnchantment(ench, lvl);
			}
			return ci;
		}
		if (type.equals(DropType.UNIDENTIFIED))
		{
			return new Unidentified(mat, name());
		}
		if (type.equals(DropType.TOME))
		{
			return new Tome();
		}
		return null;
	}

	/**
	 * Returns an itemstack that was randomly generated
	 * 
	 * @return CraftItemStack
	 */
	public CraftItemStack getItem()
	{
		Material mat = Material.DIAMOND_SWORD;
		if (gen.nextBoolean())
		{
			Material[] mats = drops.armorPicker();
			if (mats != null)
				mat = mats[gen.nextInt(mats.length - 1)];
		}
		else
		{
			mat = drops.weaponPicker();
		}
		if (mat == null)
			return null;
		DropType type = types[gen.nextInt(types.length - 1)];
		return getItem(type);
	}

	public boolean canBeItem(Material material)
	{
		if (drops.isArmor(material) || drops.isTool(material))
			return true;
		return false;
	}

	public String name()
	{
		String prefix = plugin.prefix.get(plugin.gen.nextInt(plugin.prefix
				.size() - 1));
		String suffix = plugin.suffix.get(plugin.gen.nextInt(plugin.suffix
				.size() - 1));
		return prefix + " " + suffix;
	}
}
