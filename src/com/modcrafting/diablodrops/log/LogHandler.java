package com.modcrafting.diablodrops.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.bukkit.plugin.Plugin;

public class LogHandler extends Handler {
	File logFile;
	BufferedWriter bw;
	public LogHandler(Plugin plugin){
		logFile = new File(plugin.getDataFolder(),"Error.log");
		try {
			if(!logFile.exists()){
				logFile.createNewFile();
			}
			bw = new BufferedWriter(new FileWriter(logFile,true));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void close() throws SecurityException {
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void flush() {
		try {
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void publish(LogRecord arg) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
			Date now = new Date();
			now.setTime(System.currentTimeMillis());
			Level curlvl = arg.getLevel(); 
			if(!curlvl.equals(Level.INFO)){
				StringBuilder sb = new StringBuilder();
				sb.append(format.format(now));
				sb.append(" [");
				sb.append(arg.getLevel());
				sb.append("] Class:");
				sb.append(arg.getSourceClassName());
				sb.append(" Method:");
				sb.append(arg.getSourceMethodName());
				bw.write(sb.toString());
				bw.newLine();
				bw.write(arg.getMessage());
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
