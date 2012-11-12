package com.modcrafting.diablodrops.socket;

public class SocketBonus
{
	private String name;
	private int amt;

	public SocketBonus(String name, int amt)
	{
		this.name = name;
		this.amt = amt;
	}

	public String getName()
	{
		return name;
	}

	public int getAmt()
	{
		return amt;
	}

}
