/*
 * Updater for Bukkit.
 * 
 * This class provides the means to safetly and easily update a plugin, or check
 * to see if it is updated using dev.bukkit.org
 */

package com.modcrafting.devbuild;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author H31IX
 * @author Modified by Deathmarine
 */

public class DevUpdater {
	/**
	 * Gives the dev the result of the update process. Can be obtained by called
	 * getResult().
	 */
	public enum DevUpdateResult {
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
		 * Failed update Retry.
		 */
		FAILED(3);

		private static final Map<Integer, DevUpdater.DevUpdateResult> valueList = new HashMap<Integer, DevUpdater.DevUpdateResult>();

		public static DevUpdater.DevUpdateResult getResult(final int value) {
			return valueList.get(value);
		}

		private final int value;

		static {
			for (DevUpdater.DevUpdateResult result : DevUpdater.DevUpdateResult
					.values()) {
				valueList.put(result.value, result);
			}
		}

		private DevUpdateResult(final int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	class Handler extends DefaultHandler {
		String currentElement;

		@Override
		public void characters(final char[] chars, final int start,
				final int length) throws SAXException {
			if (currentElement.equals("title")) {
				String s = new String(chars, start, length);
				if (StringUtils.containsIgnoreCase(s, plugin.getName())
						&& !StringUtils.containsIgnoreCase(s, "bleeding")) {
					versionTitle = s;
				}
			}
		}

		@Override
		public void endElement(final String uri, final String localName,
				final String qName) throws SAXException {
			currentElement = "";
		}

		@Override
		public void startElement(final String uri, final String localName,
				final String qName, final Attributes attributes)
				throws SAXException {
			currentElement = qName;
			if (currentElement.equals("link")) {
				String link = attributes.getValue("href");
				if (StringUtils.containsIgnoreCase(link, plugin.getName())
						&& !StringUtils.containsIgnoreCase(link, "bleeding")) {
					versionLink = link;
				}
			}
		}
	}

<<<<<<< HEAD
	public Integer build;
	private final Plugin plugin;
	private String versionTitle;
	private String versionLink;
	private long totalSize;
	private URL url; // Connecting to RSS
	// "https://diabloplugins.ci.cloudbees.com/rssLatest"
	private String DBOUrl;
	private static final int BYTE_SIZE = 1024; // Used for downloading files
=======
    public Integer build;
    private final Plugin plugin;
    private String versionTitle;
    private String versionLink;
    private long totalSize;
    private URL url; // Connecting to RSS
    // "https://diabloplugins.ci.cloudbees.com/rssLatest"
    private String DBOUrl;
    private static final int BYTE_SIZE = 1024; // Used for downloading files
>>>>>>> refs/remotes/origin/master

	private final String updateFolder = YamlConfiguration.loadConfiguration(
			new File("bukkit.yml")).getString("settings.update-folder");

	private DevUpdateResult result = DevUpdateResult.NO_UPDATE;

<<<<<<< HEAD
	public DevUpdater(final Plugin plugin, final File file,
			final Integer build, String url) {
		this.build = build;
		this.plugin = plugin;
		this.DBOUrl = url;
		forceUpdate(file);
=======
    public DevUpdater(final Plugin plugin, final File file,
            final Integer build, String url)
    {
        this.build = build;
        this.plugin = plugin;
        this.DBOUrl = url;
        forceUpdate(file);

    }

	public void forceUpdate(final File file) {
		result = null;
		try {
			url = new URL(DBOUrl);
		} catch (MalformedURLException ex) {
		}
		if (url != null) {
			readFeed();
			if (versionCheck(versionTitle)) {
				String fileLink = versionLink + "artifact/dist/"
						+ plugin.getDescription().getName() + ".jar";
				String name = file.getName();
				saveFile(new File("plugins/" + updateFolder), name, fileLink);
			} else {
				result = DevUpdateResult.NO_UPDATE;
			}
		}
	}

	public int getBuild() {
		return build;
	}

	/**
	 * Get the total bytes of the file (can only be used after running a version
	 * check or a normal run).
	 */
	public long getFileSize() {
		return totalSize;
	}

	/**
	 * Get the version string latest file avaliable online.
	 */
	public String getLatestVersionString() {
		return versionTitle;
	}

	/**
	 * Get the result of the update process.
	 */
	public DevUpdater.DevUpdateResult getResult() {
		return result;
	}

	public boolean pluginFile(final String name) {
		for (File file : new File("plugins").listFiles()) {
			if (file.getName().equals(name))
				return true;
		}
		return false;
	}

	/**
	 * Open the RSS feed
	 */
	private InputStream read() {
		try {
			return url.openStream();
		} catch (IOException e) {
			return null;
		}
	}

	private void readFeed() {
		try {
			InputStream in = read();
			if (in == null)
				return;
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(in, new Handler());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Save an update from dev.bukkit.org into the server's update folder.
	 */
	private void saveFile(final File folder, final String file, final String u) {
		if (!folder.exists()) {
			folder.mkdir();
		}
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		try {
			// Download the file
			URL url = new URL(u);
			int fileLength = url.openConnection().getContentLength();
			in = new BufferedInputStream(url.openStream());
			fout = new FileOutputStream(folder.getAbsolutePath() + "/" + file);

			byte[] data = new byte[BYTE_SIZE];
			int count;
			long downloaded = 0;
			while ((count = in.read(data, 0, BYTE_SIZE)) != -1) {
				downloaded += count;
				fout.write(data, 0, count);
				int percent = (int) ((downloaded * 100) / fileLength);
				if (((percent % 10) == 0)) {
					plugin.getLogger().info(
							"Downloading update: " + percent + "% of "
									+ fileLength + " bytes.");
				}
			}
			plugin.getLogger().info("Finished updating.");
			result = DevUpdateResult.SUCCESS;
		} catch (Exception ex) {
			result = DevUpdateResult.FAILED;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (fout != null) {
					fout.close();
				}
			} catch (Exception ex) {
				result = DevUpdateResult.FAILED;
			}
		}
	}

	public void setBuild(final Integer build) {
		if (build != null) {
			this.build = build;
		}
	}

	private boolean versionCheck(final String title) {
		StringBuilder sb = new StringBuilder();
		for (char c : title.toCharArray()) {
			if (Character.isDigit(c)) {
				sb.append(c);
			}
		}
		if (sb.length() < 1)
			return false;
		String bString = sb.toString();
		int buildNum = Integer.parseInt(bString);
		if (build == null) {
			build = buildNum;
		} else {
			if (buildNum > build) {
				build = buildNum;
				return true;
			}
		}

		return false;
	}
}
