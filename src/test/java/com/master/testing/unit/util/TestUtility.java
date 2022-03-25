package com.master.testing.unit.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestUtility {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestUtility.class);

    private TestUtility() {
        throw new IllegalStateException("Test utility class!");
    }

    public static String objectToJson(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            return "";
        }
    }
}
