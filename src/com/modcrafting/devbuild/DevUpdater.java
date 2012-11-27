/*
 * Updater for Bukkit.
 *
 * This class provides the means to safetly and easily update a plugin, or check to see if it is updated using dev.bukkit.org
 */

package com.modcrafting.devbuild;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * @author H31IX
 * @author Modified by Deathmarine
 */

public class DevUpdater
{
	public Integer build;
	private Plugin plugin;
	private String versionTitle;
	private String versionLink;
	private long totalSize;
	private URL url; // Connecting to RSS
	private static final String DBOUrl = "https://diabloplugins.ci.cloudbees.com/rssLatest";
	private static final int BYTE_SIZE = 1024; // Used for downloading files
	private String updateFolder = YamlConfiguration.loadConfiguration(
			new File("bukkit.yml")).getString("settings.update-folder");
	private DevUpdater.UpdateResult result = DevUpdater.UpdateResult.SUCCESS; 

	/**
	 * Gives the dev the result of the update process. Can be obtained by called
	 * getResult().
	 */
	public enum UpdateResult
	{
		/**
		 * The updater found an update, and has readied it to be loaded the next
		 * time the server restarts/reloads.
		 */
		SUCCESS(1),
		/**
		 * The updater did not find an update, and nothing was downloaded.
		 */
		NO_UPDATE(2),
		/**
		 * The updater found an update, but was unable to download it.
		 */
		FAIL_DOWNLOAD(3),
		/**
		 * For some reason, the updater was unable to contact dev.bukkit.org to
		 * download the file.
		 */
		FAIL_DBO(4),
		/**
		 * When running the version check, the file on DBO did not contain the a
		 * version in the format 'vVersion' such as 'v1.0'.
		 */
		FAIL_NOVERSION(5),
		/**
		 * The slug provided by the plugin running the updater was invalid and
		 * doesn't exist on DBO.
		 */
		FAIL_BADSLUG(6),
		/**
		 * The updater found an update, but because of the UpdateType being set
		 * to NO_DOWNLOAD, it wasn't downloaded.
		 */
		UPDATE_AVAILABLE(7);

		private static final Map<Integer, DevUpdater.UpdateResult> valueList = new HashMap<Integer, DevUpdater.UpdateResult>();
		private final int value;

		private UpdateResult(int value)
		{
			this.value = value;
		}

		public int getValue()
		{
			return this.value;
		}

		public static DevUpdater.UpdateResult getResult(int value)
		{
			return valueList.get(value);
		}

		static
		{
			for (DevUpdater.UpdateResult result : DevUpdater.UpdateResult.values())
			{
				valueList.put(result.value, result);
			}
		}
	}

	/**
	 * Allows the dev to specify the type of update that will be run.
	 */
	public enum UpdateType
	{
		/**
		 * Run a version check, and then if the file is out of date, download
		 * the newest version.
		 */
		DEFAULT(1),
		/**
		 * Don't run a version check, just find the latest update and download
		 * it.
		 */
		NO_VERSION_CHECK(2),
		/**
		 * Get information about the version and the download size, but don't
		 * actually download anything.
		 */
		NO_DOWNLOAD(3);

		private static final Map<Integer, DevUpdater.UpdateType> valueList = new HashMap<Integer, DevUpdater.UpdateType>();
		private final int value;

		private UpdateType(int value)
		{
			this.value = value;
		}

		public int getValue()
		{
			return this.value;
		}

		public static DevUpdater.UpdateType getResult(int value)
		{
			return valueList.get(value);
		}

		static
		{
			for (DevUpdater.UpdateType result : DevUpdater.UpdateType.values())
			{
				valueList.put(result.value, result);
			}
		}
	}

	/**
	 * Initialize the updater
	 * 
	 * @param plugin
	 *            The plugin that is checking for an update.
	 * @param slug
	 *            The dev.bukkit.org slug of the project
	 *            (http://dev.bukkit.org/server-mods/SLUG_IS_HERE)
	 * @param file
	 *            The file that the plugin is running from, get this by doing
	 *            this.getFile() from within your main class.
	 * @param type
	 *            Specify the type of update this will be. See
	 *            {@link UpdateType}
	 * @param announce
	 *            True if the program should announce the progress of new
	 *            updates in console
	 */
	public DevUpdater(Plugin plugin, File file,Integer build)
	{
		if(build!=null){
			this.build=build;
		}
		this.plugin = plugin;
		try
		{
			// Obtain the results of the project's file feed
			url = new URL(DBOUrl);
		}
		catch (MalformedURLException ex)
		{
			result = DevUpdater.UpdateResult.FAIL_BADSLUG; // Bad slug! Bad!
		}
		if (url != null)
		{
			// Obtain the results of the project's file feed
			readFeed();
			if (versionCheck(versionTitle))
			{
				String fileLink = versionLink+"artifact/dist/DiabloDrops.jar";
				String name = file.getName();
				saveFile(new File("plugins/" + updateFolder), name, fileLink);
			}
		}
	}

	/**
	 * Get the result of the update process.
	 */
	public DevUpdater.UpdateResult getResult()
	{
		return result;
	}

	/**
	 * Get the total bytes of the file (can only be used after running a version
	 * check or a normal run).
	 */
	public long getFileSize()
	{
		return totalSize;
	}
	public int getBuild(){
		return build;
	}
	/**
	 * Get the version string latest file avaliable online.
	 */
	public String getLatestVersionString()
	{
		return versionTitle;
	}

	/**
	 * Save an update from dev.bukkit.org into the server's update folder.
	 */
	private void saveFile(File folder, String file, String u)
	{
		if (!folder.exists())
		{
			folder.mkdir();
		}
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		try
		{
			// Download the file
			URL url = new URL(u);
			int fileLength = url.openConnection().getContentLength();
			in = new BufferedInputStream(url.openStream());
			fout = new FileOutputStream(folder.getAbsolutePath() + "/" + file);

			byte[] data = new byte[BYTE_SIZE];
			int count;
			long downloaded = 0;
			while ((count = in.read(data, 0, BYTE_SIZE)) != -1)
			{
				downloaded += count;
				fout.write(data, 0, count);
				int percent = (int) (downloaded * 100 / fileLength);
				if ((percent % 10 == 0))
				{
					plugin.getLogger().info(
							"Downloading update: " + percent + "% of "
									+ fileLength + " bytes.");
				}
			}
			// Just a quick check to make sure we didn't leave any files from
			// last time...
			for (File xFile : new File("plugins/" + updateFolder).listFiles())
			{
				if (xFile.getName().endsWith(".zip"))
				{
					xFile.delete();
				}
			}
			// Check to see if it's a zip file, if it is, unzip it.
			File dFile = new File(folder.getAbsolutePath() + "/" + file);
			if (dFile.getName().endsWith(".zip"))
			{
				// Unzip
				unzip(dFile.getCanonicalPath());
			}
			plugin.getLogger().info("Finished updating.");
		}
		catch (Exception ex)
		{
			plugin.getLogger()
					.warning(
							"The auto-updater tried to download a new update, but was unsuccessful.");
			result = DevUpdater.UpdateResult.FAIL_DOWNLOAD;
		}
		finally
		{
			try
			{
				if (in != null)
				{
					in.close();
				}
				if (fout != null)
				{
					fout.close();
				}
			}
			catch (Exception ex)
			{
			}
		}
	}

	/**
	 * Part of Zip-File-Extractor, modified by H31IX for use with Bukkit
	 */
	private void unzip(String file)
	{
		try
		{
			File fSourceZip = new File(file);
			String zipPath = file.substring(0, file.length() - 4);
			ZipFile zipFile = new ZipFile(fSourceZip);
			Enumeration<? extends ZipEntry> e = zipFile.entries();
			while (e.hasMoreElements())
			{
				ZipEntry entry = (ZipEntry) e.nextElement();
				File destinationFilePath = new File(zipPath, entry.getName());
				destinationFilePath.getParentFile().mkdirs();
				if (entry.isDirectory())
				{
					continue;
				}
				BufferedInputStream bis = new BufferedInputStream(
						zipFile.getInputStream(entry));
				int b;
				byte buffer[] = new byte[BYTE_SIZE];
				FileOutputStream fos = new FileOutputStream(destinationFilePath);
				BufferedOutputStream bos = new BufferedOutputStream(fos,
						BYTE_SIZE);
				while ((b = bis.read(buffer, 0, BYTE_SIZE)) != -1)
				{
					bos.write(buffer, 0, b);
				}
				bos.flush();
				bos.close();
				bis.close();
				String name = destinationFilePath.getName();
				if (name.endsWith(".jar") && pluginFile(name))
				{
					destinationFilePath.renameTo(new File("plugins/"
							+ updateFolder + "/" + name));
				}
				entry = null;
				destinationFilePath = null;
			}
			e = null;
			zipFile.close();
			zipFile = null;
			// Move any plugin data folders that were included to the right
			// place, Bukkit won't do this for us.
			for (File dFile : new File(zipPath).listFiles())
			{
				if (dFile.isDirectory())
				{
					if (pluginFile(dFile.getName()))
					{
						File oFile = new File("plugins/" + dFile.getName()); // Get
																				// current
																				// dir
						File[] contents = oFile.listFiles(); // List of existing
																// files in the
																// current dir
						for (File cFile : dFile.listFiles()) // Loop through all
																// the files in
																// the new dir
						{
							boolean found = false;
							for (File xFile : contents) // Loop through contents
														// to see if it exists
							{
								if (xFile.getName().equals(cFile.getName()))
								{
									found = true;
									break;
								}
							}
							if (!found)
							{
								// Move the new file into the current dir
								cFile.renameTo(new File(oFile
										.getCanonicalFile()
										+ "/"
										+ cFile.getName()));
							}
							else
							{
								// This file already exists, so we don't need it
								// anymore.
								cFile.delete();
							}
						}
					}
				}
				dFile.delete();
			}
			new File(zipPath).delete();
			fSourceZip.delete();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			plugin.getLogger()
					.warning(
							"The auto-updater tried to unzip a new update file, but was unsuccessful.");
			result = DevUpdater.UpdateResult.FAIL_DOWNLOAD;
		}
		new File(file).delete();
	}

	/**
	 * Check if the name of a jar is one of the plugins currently installed,
	 * used for extracting the correct files out of a zip.
	 */
	public boolean pluginFile(String name)
	{
		for (File file : new File("plugins").listFiles())
		{
			if (file.getName().equals(name))
			{
				return true;
			}
		}
		return false;
	}


	/**
	 * Check to see if the program should continue by evaluation whether the
	 * plugin is already updated, or shouldn't be updated
	 */
	private boolean versionCheck(String title)
	{
		StringBuilder sb = new StringBuilder();
		for(char c:title.toCharArray()){
			if(Character.isDigit(c)) sb.append(c);
		}
		if(sb.length()<1) return false;
		String bString = sb.toString();
		int buildNum = Integer.parseInt(bString);
		if(build==null){
			build=buildNum;
		}else{
			if(buildNum>build) return true;
		}
		
		return false;
	}
	private void readFeed()
	{
		try
		{
			InputStream in = read();
			if (in == null)
				return;
			SAXParserFactory factory = SAXParserFactory.newInstance();
		    SAXParser saxParser = factory.newSAXParser();
		    saxParser.parse(in, new Handler());
			
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	/**
	 * Open the RSS feed
	 */
	private InputStream read()
	{
		try
		{
			return url.openStream();
		}
		catch (IOException e)
		{
			return null;
		}
	}
	
	class Handler extends DefaultHandler {
		String currentElement;
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			currentElement = qName;
			if (currentElement.equals("link")) {
				String link = attributes.getValue("href");
				if(StringUtils.containsIgnoreCase(link, "DiabloDrops")){
	 				versionLink=link;
				}
			}
		}
		@Override
	 	public void endElement(String uri, String localName, String qName) throws SAXException {
		 	currentElement = "";
	 	}
	 	@Override
	 	public void characters(char[] chars, int start, int length) throws SAXException {
	 		if (currentElement.equals("title")) {
	 			String s = new String(chars, start, length);
	 			if(StringUtils.containsIgnoreCase(s, "DiabloDrops")){
	 				versionTitle=s;
	 			}
	 		}
	 	}
	}
}
