package com.modcrafting.diablodrops.drops;

public enum DropType {
	LEGENDARY("legendary"),
	LORE("lore"),
	MAGICAL("magical"),
	RARE("rare"),
	SET("set"),
	UNIDENTIFIED("unid");

	private String type;
	private DropType(String name){
		type=name;
	}
	public String getType(){
		return type;
	}
}
