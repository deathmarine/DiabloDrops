package me.jtjj222.BlockReplace;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.MerchantRecipeList;
import net.minecraft.server.Packet100OpenWindow;
import net.minecraft.server.Packet250CustomPayload;

import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

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
public class VillagerTradingLib {
	
	/**
	 * The id used to identify a villager trading window
	 * Used by Packet100OpenWindow
	 */
	private static int VillagerTradingInventoryTypeID = 6;
	
	/**
	 * A uniue id that is used to identify gui screens
	 * It cannot be 0; That represents the player's
	 * inventory. I chose this value randomly, and
	 * to avoid conflicts, it should be changed or 
	 * incremented every time a new window is opened
	 */
	private int lastWindowID = 10;
	

	/**
	 * Used to avoid conflicts with multiple instances
	 * of this class
	 * @param windowId - The window id to start at
	 */
	public VillagerTradingLib(int windowId) {
		this.lastWindowID = windowId;
	}
	
	public VillagerTradingLib(){}
	
	/**
	 * 
	 * @return - A unique window id
	 */
	private int getNextWindowId() {
		++lastWindowID;
		return lastWindowID;
	}
	
	/**
	 * 
	 * @param player - The player to open a trading window with
	 * @param offers - The trades that are being offered
	 * @throws NullTradeOfferException - Invalid trade offer
	 * @throws IOException 
	 */
	public void OpenTrade(Player player, VillagerTradeOffer[] offers) throws NullTradeOfferException, IOException {
		
		int windowId = getNextWindowId();
		EntityPlayer p = ((CraftPlayer)player).getHandle();
		MerchantRecipeList list = getMerchantRecipeList(offers);
		
		Packet100OpenWindow tradeWindow = new Packet100OpenWindow(windowId,VillagerTradingInventoryTypeID,"",1);
		
		p.netServerHandler.sendPacket(tradeWindow);
		p.netServerHandler.sendPacket(getVillagerOfferPacket(list,windowId));		
	}
	
	/**
	 * 
	 * @param list - A list of trade recipe's
	 * @param windowID - Id of the window that this affects
	 * @return - A packet that can be sent to the player to set the trade window recipe's
	 * @throws IOException 
	 */
	private Packet250CustomPayload getVillagerOfferPacket(MerchantRecipeList list, int windowID) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream output = new DataOutputStream(out);
		
		output.writeInt(windowID);
		//Write the offer using MerchantRecipeList's obfuscated writeRecipie method
		list.a(output);
		
		return new Packet250CustomPayload("MC|TrList",out.toByteArray());
	}
	
	/**
	 * 
	 * @param offers - An array of VillagerTradeOffers to be added to the list
	 * @return - A MerchantRecipieList that can be used by minecraft's internal functions
	 * @throws NullTradeOfferException
	 */
	private MerchantRecipeList getMerchantRecipeList(VillagerTradeOffer[] offers) throws NullTradeOfferException {
		MerchantRecipeList list = new MerchantRecipeList();
		
		for (VillagerTradeOffer offer : offers) {
			list.a(offer.getMerchantRecipie());
		}
		return list;
	}
}
