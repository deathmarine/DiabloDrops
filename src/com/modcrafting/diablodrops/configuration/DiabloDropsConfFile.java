package com.modcrafting.diablodrops.configuration;

public enum DiabloDropsConfFile
{
	GENERAL("plugins/DiabloDrops/General.yml"), TIERS(
			"plugins/DiabloDrops/Tiers.yml");

	public static DiabloDropsConfFile fromName(String name)
	{
		for (DiabloDropsConfFile id : DiabloDropsConfFile.values())
		{
			if (id.name().equalsIgnoreCase(name))
				return id;
		}

		return null;
	}

	private final String _path;

	private DiabloDropsConfFile(final String path)
	{
		_path = path;
	}

	public String getPath()
	{
		return _path;
	}
}
