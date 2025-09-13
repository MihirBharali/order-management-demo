package com.order.ordermanagement.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class LogUtil {
    private static final Logger logger = LogManager.getLogger(LogUtil.class);
    private static final String SERVER_NAME = System.getenv("HOSTNAME") != null ? System.getenv("HOSTNAME") : "local-server";

    public static void info(String message, String apiName, String customerAccountId) {
        logger.info(buildLogJson(message, apiName, null, customerAccountId));
    }

    public static void info(String message, String apiName, Long responseTimeMs, String customerAccountId) {
        logger.info(buildLogJson(message, apiName, responseTimeMs, customerAccountId));
    }

    public static void info(String message, String apiName, String operationName, String customerAccountId) {
        logger.info(buildLogJson(message, apiName, null, customerAccountId, operationName));
    }

    public static void logClientResponseTime(String message, String apiName, Long responseTimeMs, String customerAccountId, String operationName) {
        logger.info(buildLogJson(message, apiName, responseTimeMs, customerAccountId, operationName));
    }

    public static void debug(String message, String apiName, String customerAccountId) {
        logger.debug(buildLogJson(message, apiName, null, customerAccountId));
    }

    public static void debug(String message, String apiName, Long responseTimeMs, String customerAccountId) {
        logger.debug(buildLogJson(message, apiName, responseTimeMs, customerAccountId));
    }

    public static void debug(String message, String apiName, String operationName, String customerAccountId) {
        logger.debug(buildLogJson(message, apiName, null, customerAccountId, operationName));
    }

    public static void debug(String message, String apiName, Long responseTimeMs, String customerAccountId, String operationName) {
        logger.debug(buildLogJson(message, apiName, responseTimeMs, customerAccountId, operationName));
    }

    public static void error(String message, String apiName, String customerAccountId) {
        logger.error(buildLogJson(message, apiName, null, customerAccountId));
    }

    public static void error(String message, String apiName, Long responseTimeMs, String customerAccountId) {
        logger.error(buildLogJson(message, apiName, responseTimeMs, customerAccountId));
    }

    public static void error(String message, String apiName, String operationName, String customerAccountId) {
        logger.error(buildLogJson(message, apiName, null, customerAccountId, operationName));
    }

    public static void error(String message, String apiName, Long responseTimeMs, String customerAccountId, String operationName) {
        logger.error(buildLogJson(message, apiName, responseTimeMs, customerAccountId, operationName));
    }

    public static void warn(String message, String apiName, String customerAccountId) {
        logger.warn(buildLogJson(message, apiName, null, customerAccountId));
    }

    public static void warn(String message, String apiName, Long responseTimeMs, String customerAccountId) {
        logger.warn(buildLogJson(message, apiName, responseTimeMs, customerAccountId));
    }

    public static void warn(String message, String apiName, String operationName, String customerAccountId) {
        logger.warn(buildLogJson(message, apiName, null, customerAccountId, operationName));
    }

    public static void warn(String message, String apiName, Long responseTimeMs, String customerAccountId, String operationName) {
        logger.warn(buildLogJson(message, apiName, responseTimeMs, customerAccountId, operationName));
    }

    private static String buildLogJson(String message, String apiName, Long responseTimeMs, String customerAccountId) {
        JSONObject json = new JSONObject();
        json.put("message", message);
        json.put("server", SERVER_NAME);
        json.put("api", apiName);
        if (responseTimeMs != null) {
            json.put("responseTimeMs", responseTimeMs);
        }
        if (customerAccountId != null) {
            json.put("customerAccountId", customerAccountId);
        }
        return json.toString();
    }

    private static String buildLogJson(String message, String apiName, Long responseTimeMs, String customerAccountId, String operationName) {
        JSONObject json = new JSONObject();
        json.put("message", message);
        json.put("server", SERVER_NAME);
        json.put("api", apiName);
        if (responseTimeMs != null) {
            json.put("responseTimeMs", responseTimeMs);
        }
        if (customerAccountId != null) {
            json.put("customerAccountId", customerAccountId);
        }
        if (operationName != null) {
            json.put("operationName", operationName);
        }
        return json.toString();
    }
}
