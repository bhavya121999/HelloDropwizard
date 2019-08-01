package com.javaeeee.utils;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <File Description>.
 *
 * @author Amit Khandelwal
 */
public class JsonUtil {

	public JsonUtil() {
	}

	/**
	 * Get JSON from file in a JSON tree format.
	 * @param fileName file which needs to be read into JSON.
	 * @return
	 */
	public JsonNode getJson(String fileName) {
		JsonFactory jsonFactory = new JsonFactory();
		File file = new File(getClass().getClassLoader().getResource(fileName).getFile());
		try {
			JsonParser jp = jsonFactory.createParser(file);
			jp.setCodec(new ObjectMapper());
			return jp.readValueAsTree();
		} catch (IOException e) {
			//log.error("Error reading json file {}", fileName, e);
			return null;
		}
	}
}
