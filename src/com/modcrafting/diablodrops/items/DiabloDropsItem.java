package com.modcrafting.diablodrops.items;

import org.bukkit.inventory.ItemStack;

import com.modcrafting.diablodrops.rarity.Rarity;
import com.modcrafting.diablodrops.tier.Tier;

public class DiabloDropsItem
{

    private final ItemStack drop;
    private final Tier tier;
    private final Rarity rarity;
    private final float dropChance;

    public DiabloDropsItem(Drop drop, Tier tier, Rarity rarity)
    {
        this.drop = drop;
        this.tier = tier;
        this.rarity = rarity;
        this.dropChance = (getTier().getDropChance() * getRarity()
                .getDropChance()) / 100;
    }

    /**
     * @return the dropChance
     */
    public float getDropChance()
    {
        return dropChance;
    }

    /**
     * @return the drop
     */
    public ItemStack getItemStack()
    {
        return drop;
    }

    /**
     * @return the rarity
     */
    public Rarity getRarity()
    {
        return rarity;
    }

    /**
     * @return the tier
     */
    public Tier getTier()
    {
        return tier;
    }

}
