/**
 * 
 */
package com.fsd.sba;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @author Rajiniganth Jagadeesan
 *
 */
@RunWith(SpringRunner.class)
public abstract class AbstractTest {

	private final Logger log = LoggerFactory.getLogger(AbstractTest.class);
	

	/**
	 * @param obj
	 * @return String
	 * @throws JsonProcessingException
	 */
	public String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModules(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		return objectMapper.writeValueAsString(obj);
	}

	/**
	 * @param <T>
	 * @param json
	 * @param clazz
	 * @return <T>
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModules(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		return objectMapper.readValue(json, clazz);
	}
	
	public <T> List<T> mapFromJsonList(String jsonList, Class<T []> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModules(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		T[] res = objectMapper.readValue(jsonList, clazz);
		return (List<T>) Arrays.asList(res);
	}
	
	/**
	 * Method to get json string from file
	 * @param path
	 * @return String
	 */
	public String getJsonString(String path) {
		String rtn = null;
	    try {
	    	File file = ResourceUtils.getFile(path);
	        rtn = new String(Files.readAllBytes(file.toPath()));
	    } catch (IOException e ) {
	       log.error(e.getMessage());
	    } 
	    return rtn;
	}
}
