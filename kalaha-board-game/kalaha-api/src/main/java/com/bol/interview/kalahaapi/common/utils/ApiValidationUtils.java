package com.bol.interview.kalahaapi.common.utils;

import com.bol.interview.kalahaapi.abstraction.service.ICacheService;
import com.bol.interview.kalahaapi.exception.KalahaGameException;

import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.apache.logging.log4j.util.Strings.isEmpty;

/**
 * An Utility class which deals with validations of the provided inputs.
 */
public class ApiValidationUtils {

    private ApiValidationUtils() {}

    public static String throwErrorMessageIfInvalidGameId(Object gameId, ICacheService cache) {
        String message = EMPTY;
        try {
            if (gameId instanceof String) {
                String id = (String) gameId;
                if (cache == null || null == cache.fetchGameById(id) || isEmpty(id)) {
                    message = "Either Game id is invalid, non-existing, null/empty or negative.";
                }
            }
        }catch(KalahaGameException kge) {
            message = "Exception occurred while fetching game from cache.Probably, cache is evicted";
        }
        return message;
    }

    public static String throwErrorMessageIfInvalidStones(Integer stonesPerPit) {
        String message = EMPTY;
        if (stonesPerPit == null || stonesPerPit <= 0 || stonesPerPit >= Integer.MAX_VALUE) {
            message = "Invalid number of stones passed. Required non-negative stones or too large value.";
        }
        return message;
    }
}
