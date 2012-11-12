package com.modcrafting.diablodrops.socket;

public class SocketBonus
{
	private String name;
	private int amt;
	private String type;

	public SocketBonus(String name, int amt, String type)
	{
		this.name = name;
		this.amt = amt;
		this.type = type;
	}

	public String getName()
	{
		return name;
	}

	public int getAmt()
	{
		return amt;
	}

	public String getType()
	{
		return type;
	}

}
