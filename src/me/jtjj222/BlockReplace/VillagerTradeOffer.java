package me.jtjj222.BlockReplace;

import net.minecraft.server.MerchantRecipe;

import org.bukkit.inventory.ItemStack;

/**
 * 
 * This software is part of VillagerTradingLib
 * 
 * @author Justin Michaud (jtjj222)
 * Email : Jtjj222@live.ca
 * Bukkit.org username: jtjj222
 * 
 * This Library allows plugin developers to quickly and easily
 * make  custom trades with  villagers, without mingling messy
 * netMincraftServer code into their plugins.
 * 
 * VillagerTradingLib is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or 
 * any later version.
 *  
 * VillagerTradingLib is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VillagerTradingLib.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

public class VillagerTradeOffer {

	/**
	 * The ItemStack being offered
	 */
	private ItemStack Offer = null;
	
	/**
	 * The first ItemStack that the villager wants
	 * (the leftmost one)
	 */
	private ItemStack Cost1 = null;
	
	/**
	 * The second ItemStack that the villager wants
	 * (the rightmost one)
	 */
	private ItemStack Cost2 = null;
	
	
	/**
	 * 
	 * @param cost - The item that the villager wants
	 * @param offer - The item the villager is offering
	 * 
	 * Note: looses enchantments and metadata
	 */
	public VillagerTradeOffer(ItemStack cost, ItemStack offer) {
		this.Offer = offer;
		this.Cost1 = cost;
	}
	
	/**
	 * 
	 * @param cost1 - The first item the villager wants
	 * @param cost2 - The second item the villager wants
	 * @param offer - The item the villager is offering
	 * 
	 * Note: looses enchantments and metadata
	 */
	public VillagerTradeOffer(ItemStack cost1, ItemStack cost2, ItemStack offer) {
		this.Offer = offer;
		this.Cost1 = cost1;
		this.Cost2 = cost2;
	}
	
	/**
	 * 
	 * @return - A MerchantRecipie that can be used by minecraft's internal classes
	 * @throws NullTradeOfferException - Thrown when the offer isn't valid; ie: one of the ItemStack's are null
	 */
	public MerchantRecipe getMerchantRecipie() throws NullTradeOfferException{
		
		if (Cost1 == null || Offer == null) throw new NullTradeOfferException();
		
		if (Cost2 == null) return new MerchantRecipe(getNMSCost1(), getNMSOffer());
		else  return new MerchantRecipe(getNMSCost1(), getNMSCost2(), getNMSOffer());
	}
	
	/**
	 * 
	 * @return - The first items the villager wants, in
	 * a netMinecraftServer itemstack
	 */
	public net.minecraft.server.ItemStack getNMSCost1() {
		return convertItemStackToNMS(this.Cost1);
	}
	
	/**
	 * 
	 * @return - The second item the villager wants, in
	 * a netMinecraftServer itemstack
	 */
	public net.minecraft.server.ItemStack getNMSCost2() {
		return convertItemStackToNMS(this.Cost2);
	}
	
	/**
	 * 
	 * @return - The item the villager is offering, in
	 * a netMinecraftServer itemstack
	 */
	public net.minecraft.server.ItemStack getNMSOffer() {
		return convertItemStackToNMS(this.Offer);
	}
	
	/**
	 * 
	 * @param i - The bukkit itemstack to convert
	 * @return - A netMinecraftServer itemstack
	 * 
	 * Note: looses enchantments and metadata
	 */
	private net.minecraft.server.ItemStack convertItemStackToNMS(ItemStack i) {
		int amount = i.getAmount();
		int id = i.getType().getId();
		short durability = i.getDurability();
		
		return new net.minecraft.server.ItemStack(id,amount,durability);
	}
	
}
