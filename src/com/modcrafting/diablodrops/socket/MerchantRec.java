package com.modcrafting.diablodrops.socket;

import net.minecraft.server.ItemStack;
import net.minecraft.server.MerchantRecipe;

public class MerchantRec extends MerchantRecipe{
	public MerchantRec(ItemStack sell1, ItemStack sell2, ItemStack buy) {
		super(sell1, sell2, buy);
	}

}
