package com.zyc.baselibs.commons;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassLoaderUtils {
	
	private static final String CLASS_SUFFIX = ".class";
	
	private static final String PACKAGE_SEPARATOR = ".";
	
	private static final String CLASS_FILE_PREFIX = File.separator + "classes"  + File.separator;
	
	private static final String PROTOCOL_FILE = "file";
	
	private static final String PROTOCOL_JAR = "jar";

	public static List<String> getClazzNames(String packageName, boolean includeChildPackage) {
		List<String> classNames = new ArrayList<String>();
		URL url;
		JarFile jarFile;

		try {
			Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(packageName.replaceAll("\\.", "/"));
			while (urls.hasMoreElements()) {
				url = (URL) urls.nextElement();
				if(url != null) {
					if(PROTOCOL_FILE.equals(url.getProtocol())) {
						collectClassNamesByFile(classNames, new File(url.getPath()), includeChildPackage);
					} else if(PROTOCOL_JAR.equals(url.getProtocol())) {
						try {
							jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
						} catch (IOException e) {
							jarFile = null;
							e.printStackTrace();
						}
						
						if(jarFile != null) {
							collectClassNamesByJar(classNames, jarFile, packageName, includeChildPackage);
						}
					}
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return classNames;
	}
	
	private static void collectClassNamesByFile(List<String> classNameContainer, File file, boolean includeChildPackage) {
		if(file.exists()) {
			if(file.isFile()) {
				if(file.getPath().endsWith(CLASS_SUFFIX)) {
					String path = file.getPath().replace(CLASS_SUFFIX, "");
					String className = path.substring(path.indexOf(CLASS_FILE_PREFIX) + CLASS_FILE_PREFIX.length()).replace(File.separator, PACKAGE_SEPARATOR);
					//内部类存在$符号，根据该符号排除内部类
					if(!className.contains("$")) {
						classNameContainer.add(className);
					}
				}
			} else {
				if(includeChildPackage) {
					File[] files = file.listFiles();
					if(files != null && files.length > 0) {
						for (File f : files) {
							collectClassNamesByFile(classNameContainer, f, includeChildPackage);
						}
					}
				}
			}
		}
	}
	
	private static void collectClassNamesByJar(List<String> classNameContainer, JarFile jarFile, String packageName, boolean includeChildPackage) {
		JarEntry jarEntry;
		String className;
		Enumeration<JarEntry> entries = jarFile.entries();
		while (entries.hasMoreElements()) {
			 jarEntry = (JarEntry) entries.nextElement();
			 if(jarEntry.getName().endsWith(CLASS_SUFFIX)) {
				 className = jarEntry.getName().replace(CLASS_SUFFIX, "").replace("/", ".");
				 //内部类存在$符号，根据该符号排除内部类  && 如果包含子包，className只需要开头与packageName匹配；如果不包含子包，className去除类名之后的值与packageName需要相等
				 if(!className.contains("$") && ((includeChildPackage && className.startsWith(packageName)) || packageName.equals(className.substring(0, className.lastIndexOf("."))))) {
					 classNameContainer.add(className);
				 }
			 }
		}
	}
}
