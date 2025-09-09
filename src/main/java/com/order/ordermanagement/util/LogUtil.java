package com.order.ordermanagement.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class LogUtil {
    private static final Logger logger = LogManager.getLogger(LogUtil.class);
    private static final String SERVER_NAME = System.getenv("HOSTNAME") != null ? System.getenv("HOSTNAME") : "local-server";

    public static void info(String message, String apiName) {
        logger.info(buildLogJson(message, apiName, null));
    }

    public static void info(String message, String apiName, Long responseTimeMs) {
        logger.info(buildLogJson(message, apiName, responseTimeMs));
    }

    public static void debug(String message, String apiName) {
        logger.debug(buildLogJson(message, apiName, null));
    }

    public static void debug(String message, String apiName, Long responseTimeMs) {
        logger.debug(buildLogJson(message, apiName, responseTimeMs));
    }

    public static void error(String message, String apiName) {
        logger.error(buildLogJson(message, apiName, null));
    }

    public static void error(String message, String apiName, Long responseTimeMs) {
        logger.error(buildLogJson(message, apiName, responseTimeMs));
    }

    public static void warn(String message, String apiName) {
        logger.warn(buildLogJson(message, apiName, null));
    }

    public static void warn(String message, String apiName, Long responseTimeMs) {
        logger.warn(buildLogJson(message, apiName, responseTimeMs));
    }

    private static String buildLogJson(String message, String apiName, Long responseTimeMs) {
        JSONObject json = new JSONObject();
        json.put("message", message);
        json.put("server", SERVER_NAME);
        json.put("api", apiName);
        if (responseTimeMs != null) {
            json.put("responseTimeMs", responseTimeMs);
        }
        return json.toString();
    }
}
