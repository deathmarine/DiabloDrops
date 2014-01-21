package us.deathmarine.diablodrops.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import us.deathmarine.diablodrops.DiabloDrops;
import us.deathmarine.diablodrops.tier.Tier;

public class SockettedItem extends Drop {

	private static ChatColor color() {
		return DiabloDrops.getInstance().getDropAPI().colorPicker();
	}

	private static String name(Material mat) {
		return DiabloDrops.getInstance().getDropAPI().name(mat);
	}

	private static String[] sockets() {
		List<String> list = new ArrayList<String>();
		int enhance = DiabloDrops.getInstance().getSettings().getMinSockets()
				+ DiabloDrops
						.getInstance()
						.getSingleRandom()
						.nextInt(
								DiabloDrops.getInstance().getSettings()
										.getMaxSockets());
		for (int i = 0; i < enhance; i++) {
			list.add(color() + "(Socket)");
		}
		return list.toArray(new String[0]);
	}

	private Tier tier;

	public SockettedItem(final Material mat) {
		super(mat);
		this.tier = DiabloDrops.getInstance().getDropAPI().getTier();
		while (tier == null)
			tier = DiabloDrops.getInstance().getDropAPI().getTier();
		Material material = mat;
		if ((tier.getMaterials().size() > 0)
				&& !tier.getMaterials().contains(mat)) {
			material = tier.getMaterials().get(
					DiabloDrops.getInstance().getSingleRandom()
							.nextInt(tier.getMaterials().size()));
		}
		this.setType(material);
		short damage = 0;
		if (DiabloDrops.getInstance().getConfig()
				.getBoolean("DropFix.Damage", true)) {
			damage = DiabloDrops.getInstance().getDropAPI()
					.damageItemStack(mat);
		}
		this.setDurability(damage);
		DiabloDrops.getInstance().getItemAPI()
				.setName(this, tier.getColor() + name(mat));
		int e = tier.getAmount();
		int l = tier.getLevels();
		List<Enchantment> eStack = Arrays.asList(Enchantment.values());
		boolean safe = DiabloDrops.getInstance().getConfig()
				.getBoolean("SafeEnchant.Enabled", true);
		if (safe) {
			eStack = DiabloDrops.getInstance().getDropAPI()
					.getEnchantStack(this);
		}
		for (; e > 0; e--) {
			int lvl = DiabloDrops.getInstance().getSingleRandom()
					.nextInt(l + 1);
			int size = eStack.size();
			if (size < 1) {
				continue;
			}
			Enchantment ench = eStack.get(DiabloDrops.getInstance()
					.getSingleRandom().nextInt(size));
			if ((lvl != 0) && (ench != null)
					&& !tier.getColor().equals(ChatColor.MAGIC))
				if (safe) {
					if ((lvl >= ench.getStartLevel())
							&& (lvl <= ench.getMaxLevel())) {
						try {
							this.addEnchantment(ench, lvl);
						} catch (Exception e1) {
							if (DiabloDrops.getInstance().getDebug()) {
								DiabloDrops.getInstance().log.warning(e1
										.getMessage());
							}
							e++;
						}
					}
				} else {
					this.addUnsafeEnchantment(ench, lvl);
				}
		}
		if (DiabloDrops.getInstance().getSettings().isColorBlindCompat()) {
			DiabloDrops
					.getInstance()
					.getItemAPI()
					.addLore(
							this,
							DiabloDrops.getInstance().getDropAPI()
									.getPrettyMaterialName(material));
		}
		if (DiabloDrops.getInstance().getConfig()
				.getBoolean("Display.TierName", true)
				&& !tier.getColor().equals(ChatColor.MAGIC)) {
			DiabloDrops.getInstance().getItemAPI()
					.addLore(this, tier.getColor() + tier.getDisplayName());
		}
		for (String s : sockets()) {
			DiabloDrops.getInstance().getItemAPI().addLore(this, s);
		}
		if (DiabloDrops.getInstance().getItemAPI().isLeather(getType())) {
			LeatherArmorMeta lam = (LeatherArmorMeta) getItemMeta();
			lam.setColor(Color.fromRGB(DiabloDrops.getInstance()
					.getSingleRandom().nextInt(255), DiabloDrops.getInstance()
					.getSingleRandom().nextInt(255), DiabloDrops.getInstance()
					.getSingleRandom().nextInt(255)));
			setItemMeta(lam);
		}
	}

	/**
	 * @return the tier
	 */
	public Tier getTier() {
		return tier;
	}
}