package com.modcrafting.diablodrops.configuration;

import java.util.Set;

import com.modcrafting.diablodrops.DiabloDrops;

public class ConfigHelper
{

	private DiabloDrops plugin;
	private DiabloDropsConfigs configs;

	public ConfigHelper(DiabloDrops dd, DiabloDropsConfigs ddc)
	{
		setPlugin(dd);
		setConfigs(ddc);
	}

	public String getString(DiabloDropsConfFile ddcf, String path,
			String fallback)
	{
		String s = configs.getProperty(ddcf, path);
		if (s == null)
			return fallback;
		return s;
	}

	public int getInt(DiabloDropsConfFile ddcf, String path, int fallback)
	{
		String s = configs.getProperty(ddcf, path);
		if (s == null)
			s = "0";
		int i;
		try
		{
			i = Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			i = fallback;
		}
		return i;
	}

	public double getDouble(DiabloDropsConfFile ddcf, String path,
			double fallback)
	{
		String s = configs.getProperty(ddcf, path);
		if (s == null)
			s = "0.0";
		double d;
		try
		{
			d = Double.parseDouble(s);
		}
		catch (NumberFormatException e)
		{
			d = fallback;
		}
		return d;
	}

	public boolean getBoolean(DiabloDropsConfFile ddcf, String path,
			boolean fallback)
	{
		String s = configs.getProperty(ddcf, path);
		if (s == null)
			s = String.valueOf(fallback);
		return Boolean.parseBoolean(s);
	}

	public String[] stringSetToArray(DiabloDropsConfFile ddcf, Set<String> ss,
			String[] fallback)
	{
		if (ss == null)
			return fallback;
		String[] sl = ss.toArray(new String[0]);
		if (sl != null)
			return sl;
		return fallback;
	}

	public DiabloDrops getPlugin()
	{
		return plugin;
	}

	public void setPlugin(DiabloDrops plugin)
	{
		this.plugin = plugin;
	}

	public DiabloDropsConfigs getConfigs()
	{
		return configs;
	}

	public void setConfigs(DiabloDropsConfigs configs)
	{
		this.configs = configs;
	}

}
