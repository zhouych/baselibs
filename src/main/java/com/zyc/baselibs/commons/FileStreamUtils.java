package com.zyc.baselibs.commons;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.log4j.Logger;

public class FileStreamUtils {
	
	private static final Logger logger = Logger.getLogger(FileStreamUtils.class); 
	
	private static final String EX_WRITE = "[write(...)] - ";
	
	public static void write(String path, String content) {
		Writer fw = null;
		
		try {
			fw = new FileWriter(new File(path), true);
			fw.write(content == null ? "" : content);
		} catch (Exception e) {
			logger.error(EX_WRITE + e.getMessage(), e);
		} finally {
			if(fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					logger.error(EX_WRITE + e.getMessage(), e);
				}	
			}
		}
	}
}
