package com.modcrafting.diablodrops.drops;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.minecraft.server.NBTTagCompound;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.tier.Drop;
import com.modcrafting.diablodrops.tier.Tier;
import com.modcrafting.diablodrops.tier.Tome;

public class DropsAPI
{

	private Random gen = new Random();
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
	public CraftItemStack getItem(Material mat)
	{
		if (mat == null)
			return null;
		CraftItemStack ci = null;
		int attempts = 0;
		while (ci != null && attempts < 10)
		{
			if (gen.nextBoolean())
			{
				if (plugin.config.getBoolean("IdentifyTome.Enabled", true)
						&& gen.nextInt(100) <= plugin.config.getInt(
								"IdentifyTome.Chance", 3))
					return new Tome();
				continue;
			}
			for (Tier tier : plugin.tiers)
			{
				if (gen.nextInt(100) <= tier.getChance())
				{
					int e = tier.getAmount();
					int l = tier.getLevels();
					ci = new Drop(mat, tier.getColor(), name());
					for (; e > 0; e--)
					{
						int lvl = plugin.gen.nextInt(l + 1);
						Enchantment ench = drops.enchant();
						if (lvl != 0 && ench != null)
							makeSafe(ench, ci, lvl);
					}
					return ci;
				}
			}
			attempts++;
		}
		return null;
	}

	/**
	 * Returns an specific typename of itemstack that was randomly generated
	 * 
	 * @return CraftItemStack
	 */
	public CraftItemStack getItem(String type)
	{
		Material mat = dropPicker();
		if (mat == null)
			return null;
		CraftItemStack ci = null;
		int attempts = 0;
		while (ci == null && attempts < 10)
		{
			for (Tier tier : plugin.tiers)
			{
				if (tier.getName().equalsIgnoreCase(type))
				{
					if (gen.nextInt(100) <= tier.getChance())
					{
						int e = tier.getAmount();
						int l = tier.getLevels();
						ci = new Drop(mat, tier.getColor(), name());
						for (; e > 0; e--)
						{
							int lvl = plugin.gen.nextInt(l + 1);
							Enchantment ench = drops.enchant();
							if (lvl != 0 && ench != null)
								makeSafe(ench, ci, lvl);
						}
						return ci;
					}
				}
			}
			attempts++;
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
		Material mat = dropPicker();
		if (mat == null)
			return null;
		return getItem(mat);
	}

	/**
	 * Returns a Material that was randomly picked
	 * 
	 * @return Material
	 */
	public Material dropPicker()
	{
		if (gen.nextBoolean())
		{
			Material[] mats = drops.armorPicker();
			if (mats != null)
				return mats[gen.nextInt(mats.length - 1)];
		}
		else
		{
			return drops.weaponPicker();
		}
		return null;
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

	public void makeSafe(Enchantment ench, CraftItemStack citem, int level)
	{
		try
		{
			citem.addEnchantment(ench, 1);
		}
		catch (IllegalArgumentException e)
		{
		}
	}

	public boolean wearingSet(Player player)
	{
		ItemStack his = player.getInventory().getHelmet();
		ItemStack cis = player.getInventory().getChestplate();
		ItemStack lis = player.getInventory().getLeggings();
		ItemStack bis = player.getInventory().getBoots();
		if (his == null || cis == null || lis == null || bis == null)
			return false;
		Set<ItemStack> sis = new HashSet<ItemStack>();
		sis.add(cis);
		sis.add(lis);
		sis.add(bis);
		String potentialSet = new String();
		CraftItemStack chis = ((CraftItemStack) his);
		net.minecraft.server.ItemStack mistack = chis.getHandle();
		NBTTagCompound tag = mistack.tag;
		if (tag == null)
		{
			tag = new NBTTagCompound();
			tag.setCompound("display", new NBTTagCompound());
			mistack.tag = tag;
		}
		String[] ss = ChatColor.stripColor(tag.getString("Name")).split(" ");
		potentialSet = ss[0];
		for (ItemStack is : sis)
		{
			CraftItemStack c = ((CraftItemStack) is);
			net.minecraft.server.ItemStack cstack = c.getHandle();
			NBTTagCompound cstacktag = cstack.tag;
			if (cstacktag == null)
			{
				cstacktag = new NBTTagCompound();
				cstacktag.setCompound("display", new NBTTagCompound());
				cstack.tag = cstacktag;
			}
			String[] splits = ChatColor.stripColor(cstacktag.getString("Name"))
					.split(" ");
			if (!splits[0].equalsIgnoreCase(potentialSet))
				return false;
		}
		return true;
	}
}
