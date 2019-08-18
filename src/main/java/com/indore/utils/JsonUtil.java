package com.indore.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.JsonFactory;

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
     *
     * @param fileName file which needs to be read into JSON.
     * @return
     */
    public String getJson(String fileName) throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream in = classLoader.getResourceAsStream(fileName);
        //File file = new File(classLoader.getResource(fileName).getFile());

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }

        return result.toString(StandardCharsets.UTF_8.name());
    }
}
