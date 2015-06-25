package com.bike.propertiesagent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesLoad {
	static final Logger logger = LoggerFactory.getLogger(PropertiesLoad.class);

	/**
	 * 
	 * @param agentOps
	 * @param inst
	 */
	public static void premain(String agentOps, Instrumentation inst) {
		File file = new File(agentOps);
		if (!file.exists()) {
			logger.error("file {} is not exit and skip !",
					file.getAbsolutePath());
			return;
		}
		logger.info("properties file : {}", file.getAbsolutePath());
		try {
			loadPropertiesToSystem(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void loadPropertiesToSystem(File propertiesFile)
			throws IOException {

		FileInputStream fileInputStream = new FileInputStream(propertiesFile);
		Properties properties = new Properties();
		properties.load(fileInputStream);
		logger.info("-------------------------");
		Set<Entry<Object, Object>> set = properties.entrySet();
		for (Entry<Object, Object> entry : set) {
			logger.info(" {} = {}", entry.getKey(), entry.getValue());
		}
		logger.info("-------------------------");
		System.getProperties().putAll(properties);
		fileInputStream.close();
	}
}
