package com.modcrafting.diablodrops.socket;

public class SocketBonus
{
	public static enum SocketType{
		ARMOR(0),WEAPON(1),TOOL(2),ITEM(3);
		int n;
		SocketType(int i){
			n=i;
		}
		public Integer getType(){
			return n;
		}
	}
	private String name;
	private int amt;
	private SocketType type;
	public SocketBonus(SocketType t,String name, int amt)
	{
		this.type = t;
		this.name = name;
		this.amt = amt;
	}

	public SocketType getType()
	{
		return type;
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
